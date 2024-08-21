package com.jesse.contractai.domain.use_case

import com.jesse.contractai.data.repository.contract.ContractRepository
import com.jesse.contractai.di.ContractAIDispatchers
import com.jesse.contractai.di.Dispatcher
import com.jesse.contractai.ui.model.ContractResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 *
 * The purpose of this use case is to get all contract results (review contracts and generated contracts),
 * merging them as one and then sending it down to the UI.
 * */
class ContractResultsUseCase @Inject constructor(
    @Dispatcher(ContractAIDispatchers.DEFAULT) private val defaultDispatcher: CoroutineDispatcher,
    private val contractRepository: ContractRepository
) {

    operator fun invoke(): Flow<List<ContractResult>>{
        return contractRepository.getAllReviewedContracts().combine(contractRepository.getAllGeneratedContracts()){
            reviewedList, generatedList ->
            // Convert both lists to ContractResult and combine them
            val contractResult = (reviewedList.map { reviewed ->
                ContractResult.ReviewedContractResult(title = reviewed.title, timeStamp = reviewed.timeStamp)
            } + generatedList.map { generated ->
                ContractResult.GeneratedContractResult(title = generated.title, timeStamp = generated.timeStamp)
            }).sortedByDescending { it.timeStamp }

            contractResult
        }.flowOn(defaultDispatcher)
    }
}