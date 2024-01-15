package com.example.pawsome.model

import android.net.Uri
import java.io.Serializable

data class PetDetail(
    var petName: String,
    var petGender: String,
    var petBreed: String,
    var petAnimal:String,
    var petColor:String,
    var petStatus: String,
    var petAge: String,
    var petDescription: String,
    var petAddress: String,
    var bookingPricePerDay: String,
    var id: String? = "",
    var latitude: Double = 10.729250,
    var img: String,
    var longitude: Double = 106.695520,
    var ownerId: String,
    var distance: Double
) : Serializable
