package com.example.pawsome.model.api_model

data class CheckLivenessResponse(
    val challengeCode: String,
    val dataBase64: String,
    val dataSign: String,
    val imgs: Imgs,
    val logID: String,
    val message: String,
    val `object`: CheckLivenessObject,
    val server_version: String,
    val statusCode: Int
)