package com.example.pawsome.model

import java.sql.Timestamp

data class PetDetail(
    val petName: String,
    val petGender: String,
    val petBreed: String,
    val petAnimal:String,
    val petColor:String,
    val petStatus: String,
    val petAge: String,
    val petDescription: String,
    val bookingPricePerDay: Float,
    val startDate: Timestamp,
    val endDate: Timestamp,
    val id: String,
    val img: String,
    val location: String,
    val ownerId: String,
    val bookingList: List<Book> = emptyList()
)