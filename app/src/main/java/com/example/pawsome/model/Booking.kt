package com.example.pawsome.model

import java.sql.Timestamp

data class Booking(
    val user: User,
    val petDetail: PetDetail,
    val startDate: Timestamp,
    val totalPrice: Float,
    val endDate: Timestamp
)