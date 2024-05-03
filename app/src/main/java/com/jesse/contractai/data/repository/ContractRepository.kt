package com.jesse.contractai.data.repository

import com.jesse.contractai.data.model.ContractJson
import com.jesse.contractai.data.model.TokenResponse

interface ContractRepository {

    suspend fun getMoreDetailsOnContract(contractType: String, contractNature: String): Result<List<String>>
    //suspend fun generateContract(answers: List<String>):
}