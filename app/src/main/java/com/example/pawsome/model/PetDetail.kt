package com.example.pawsome.model

import java.sql.Timestamp

data class PetDetail(
    val petName: String,

    val petGender: String,
    val petAge: String,
    val bookingPricePerDay: Float,
    val startDate: Timestamp,
    val endDate: Timestamp,
    val id: String,
    val ownerId: String
)