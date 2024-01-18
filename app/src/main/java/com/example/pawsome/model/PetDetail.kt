/*
    RMIT University Vietnam
    Course: COSC2657 Android Development
    Semester: 2023C
    Assessment: Assignment 3
    Author:
        Thieu Tran Tri Thuc - s3870730
        Lai Nghiep Tri - s3799602
        Bui Minh Nhat - s3878174
        Phan Bao Kim Ngan - s3914582
    Created  date: 1/1/2024
    Last modified: 19/1/2024
    Acknowledgement: Figma UI, Android Developer documentation, Firebase Documentation, etc
 */

package com.example.pawsome.model

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
