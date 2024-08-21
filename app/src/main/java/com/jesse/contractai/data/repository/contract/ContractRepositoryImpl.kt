package com.jesse.contractai.data.repository.contract

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jesse.contractai.data.exceptions.AuthenticationException
import com.jesse.contractai.data.exceptions.NoResponseException
import com.jesse.contractai.data.local.data_source.ContractLocalDataSource
import com.jesse.contractai.data.local.database.model.ConversationItemEntity
import com.jesse.contractai.data.network.data_source.ContractNetworkDataSource
import com.jesse.contractai.data.network.model.GenerateContractResponse
import com.jesse.contractai.data.network.model.ReviewContractResponse
import com.jesse.contractai.data.network.service.user_auth_service.UserAuthenticationService
import com.jesse.contractai.di.ContractAIDispatchers
import com.jesse.contractai.di.Dispatcher
import com.jesse.contractai.ui.model.GeneratedContract
import com.jesse.contractai.ui.model.ReviewedContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContractRepositoryImpl @Inject constructor(
    @Dispatcher(ContractAIDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(ContractAIDispatchers.DEFAULT) private val defaultDispatcher: CoroutineDispatcher,
    private val contractNetworkDataSource: ContractNetworkDataSource,
    private val contractLocalDataSource: ContractLocalDataSource,
    private val userAuthenticationService: UserAuthenticationService
): ContractRepository {

    override suspend fun getMoreDetailsOnContract(contractType: String, contractNature: String): Result<List<String>> {
        return withContext(ioDispatcher){
            try {
                contractNetworkDataSource.getMoreContextOnContract(contractType, contractNature)?.let {
                    text ->
                    val questions = Gson().fromJson<List<String>>(text, object: TypeToken<List<String>>() {}.type)
                    Result.success(questions)
                } ?: Result.failure(NoResponseException())
            } catch (e: Exception){
                Result.failure(e)
            }
        }
    }

    override suspend fun generateContract(
        contractType: String,
        questionsAndAnswers: List<Pair<String, String>>
    ): Result<GeneratedContract> {
        return withContext(ioDispatcher){
            try {
                // Collect the latest user ID
                val user = userAuthenticationService.user.firstOrNull() ?: return@withContext Result.failure(AuthenticationException.NoUserException())

                contractNetworkDataSource.generateContract(contractType, questionsAndAnswers)?.let {
                    text ->
                    // Generate the contract
                    val generateContractResponse = Gson().fromJson(text, GenerateContractResponse::class.java)
                    val timeStamp = System.currentTimeMillis()
                    // Insert the generated contract into local storage
                    contractLocalDataSource.insertGeneratedContract(generateContractResponse.toGeneratedContractEntity(timeStamp, userId = user.id))
                    // Get generated contract from local storage (making local storage the source of truth) and then return
                    // it wrapped in a Result class
                    Result.success(contractLocalDataSource.getGeneratedContract(timeStamp).toGeneratedContract())
                } ?: Result.failure(NoResponseException())
            } catch (e: Exception){
                Result.failure(e)
            }
        }
    }

    override suspend fun getGeneratedContract(timeStamp: Long): Result<GeneratedContract> {
        return try {
            Result.success(contractLocalDataSource.getGeneratedContract(timeStamp).toGeneratedContract())
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override fun getAllGeneratedContracts(): Flow<List<GeneratedContract>> {
        return userAuthenticationService.user.flatMapLatest {
            user ->
            contractLocalDataSource.getAllGeneratedContract(user?.id ?: "").map {
                    generatedContractEntities ->
                generatedContractEntities.map {
                    it.toGeneratedContract()
                }
            }.flowOn(defaultDispatcher)
        }
    }

    override fun getAllReviewedContracts(): Flow<List<ReviewedContract>> {
        return userAuthenticationService.user.flatMapLatest {
                user ->
            contractLocalDataSource.getAllReviewedContracts(user?.id ?: "").map {
                    reviewedContractEntities ->
                reviewedContractEntities.map {
                    it.toReviewedContract()
                }
            }.flowOn(defaultDispatcher)
        }
    }

    override suspend fun getReviewContract(timeStamp: Long): Result<ReviewedContract> {
        return withContext(ioDispatcher){
            try {
                Result.success(contractLocalDataSource.getReviewedContract(timeStamp).toReviewedContract())
            } catch (e: Exception){
                Result.failure(e)
            }
        }
    }

    override suspend fun regenerateAnotherContract(
        complaints: String,
        generatedContract: GeneratedContract
    ): Result<GeneratedContract> {
        return withContext(ioDispatcher){
            try {
                // Collect the latest user ID
                val user = userAuthenticationService.user.firstOrNull() ?: return@withContext Result.failure(AuthenticationException.NoUserException())

                contractNetworkDataSource.regenerateAnotherContract(complaints, generatedContract.content)?.let {
                        text ->
                    // Generate the contract
                    val generateContractResponse = Gson().fromJson(text, GenerateContractResponse::class.java)
                    val timeStamp = System.currentTimeMillis()
                    // Delete generated contract from storage
                    contractLocalDataSource.deleteGeneratedContract(generatedContract.timeStamp)
                    // Insert the newly generated contract into local storage
                    contractLocalDataSource.insertGeneratedContract(generateContractResponse.toGeneratedContractEntity(timeStamp, userId = user.id))
                    // Get generated contract from local storage (making local storage the source of truth) and then return
                    // it wrapped in a Result class
                    Result.success(contractLocalDataSource.getGeneratedContract(timeStamp).toGeneratedContract())
                } ?: Result.failure(NoResponseException())
            } catch (e: Exception){
                Result.failure(e)
            }
        }
    }

    override suspend fun reviewContract(contractContent: String): Result<ReviewedContract> {
        return withContext(ioDispatcher){
            try {
                // Collect the latest user ID
                val user = userAuthenticationService.user.firstOrNull() ?: return@withContext Result.failure(AuthenticationException.NoUserException())

                contractNetworkDataSource.reviewContract(contractContent)?.let {
                        text ->
                    // Review the contract
                    val reviewContractResponse = Gson().fromJson(text, ReviewContractResponse::class.java)
                    val timeStamp = System.currentTimeMillis()
                    // Insert the reviewed contract into local storage
                    contractLocalDataSource.insertReviewedContract(reviewContractResponse.toReviewedContractEntity(timeStamp, userId = user.id))
                    // Insert the content of the reviewed contract as a review conversation item
                    contractLocalDataSource.insertConversationItem(
                        ConversationItemEntity(
                            timeStamp = timeStamp,
                            reviewedContractTimeStamp = timeStamp,
                            senderType = 1,
                            content = reviewContractResponse.content,
                            conversationMessageStatus = 2
                        )
                    )
                    // Get reviewed contract from local storage (making local storage the source of truth) and then return
                    // it wrapped in a Result class
                    val reviewedContract = contractLocalDataSource.getReviewedContract(timeStamp).toReviewedContract()
                    Result.success(reviewedContract)
                } ?: Result.failure(NoResponseException())
            } catch (e: Exception){
                Result.failure(e)
            }
        }
    }
}