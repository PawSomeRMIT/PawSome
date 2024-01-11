package com.example.pawsome.model

import java.sql.Timestamp

data class Booking(
    val customerId: String,
    val petId: String,
    val totalPrice: Double,
    val customerCardIdNumber: String,
    val customerCardIdName: String
    )