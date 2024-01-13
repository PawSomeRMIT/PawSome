package com.example.pawsome.model

import com.google.firebase.Timestamp

data class Booking(
    /*val user: User,
    val petDetail: PetDetail,
    val startDate: Timestamp,
    val totalPrice: Float,
    val endDate: Timestamp*/

    val customerCardIdName: String,
    val customerCardIdNumber: String,
    val customerId: String,
    val petId: String,
    val startDate: Timestamp,
    val endDate: Timestamp,
    val totalPrice: Float,
    val ownerId: String
)
