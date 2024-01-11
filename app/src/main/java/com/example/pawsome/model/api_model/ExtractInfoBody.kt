package com.example.pawsome.model.api_model

data class ExtractInfoBody (
    val img_front: String,
    val client_session: String,
    val type: Int,
    val validate_postcode: Boolean,
    val token: String
)
