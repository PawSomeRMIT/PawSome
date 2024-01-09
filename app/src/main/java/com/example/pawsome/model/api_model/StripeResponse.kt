package com.example.pawsome.model.api_model

data class StripeResponse (
    val customer: String,
    val ephemeralKey: String,
    val paymentIntent: String,
    val publishableKey: String
)