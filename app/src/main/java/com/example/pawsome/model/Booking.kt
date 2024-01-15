package com.example.pawsome.model

data class Booking(
    val customerId: String,
    val petId: String,
    val totalPrice: Double,
    val customerCardIdNumber: String,
    val customerCardIdName: String
)