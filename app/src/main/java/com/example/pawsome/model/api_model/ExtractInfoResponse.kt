package com.example.pawsome.model.api_model

data class ExtractInfoResponse(
    val message: String,
    val `object`: ExtractInfoObject,
    val server_version: String
)