package com.jesse.contractai.data.network.model


data class ContractJson(
    val auth_provider_x509_cert_url: String,
    val auth_uri: String,
    val client_email: String,
    val client_id: String,
    val client_x509_cert_url: String,
    val private_key: String,
    val private_key_id: String,
    val project_id: String,
    val token_uri: String,
    val type: String,
    val universe_domain: String
)