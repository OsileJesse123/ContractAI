package com.jesse.contractai.domain.use_case

import com.jesse.contractai.data.repository.contract.ContractRepository
import com.jesse.contractai.di.ContractAIDispatchers
import com.jesse.contractai.di.Dispatcher
import com.jesse.contractai.ui.model.ContractResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
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
       return contractRepository.getAllGeneratedContracts().map {
           generatedContracts ->
           generatedContracts.map {
               ContractResult.GeneratedContractResult(title = it.title, timeStamp = it.timeStamp)
           }.sortedByDescending { it.timeStamp }
       }.flowOn(defaultDispatcher)
    }
}