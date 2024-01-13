package com.example.pawsome.model

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable
import java.sql.Timestamp
import java.util.Random
import java.util.UUID.randomUUID

data class PetDetail(
    val petName: String,
    val petGender: String,
    val petBreed: String,
    val petAnimal:String,
    val petColor:String,
    val petStatus: String,
    val petAge: String,
    val petDescription: String,
    val bookingPricePerDay: Double,
//    val startDate: Timestamp,
//    val endDate: Timestamp,
    val id: String,
    val latitude: Double,
    val img: String,
    val longitude: Double,
    val ownerId: String,
    val distance: Double
//    val bookingList: List<Booking> = emptyList()
) : Serializable