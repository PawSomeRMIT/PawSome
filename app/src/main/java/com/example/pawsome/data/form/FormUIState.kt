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

package com.example.pawsome.data.form

data class FormUIState(
    var petName: String = "",
    var petBreed: String = "",
    var petGender: String = "",
    var petColor: String = "",
    var petAge: String = "",
    var petType: String = "",
    var petDescription: String = "",
    var petlocation: String = "",
    var bookingPrice: String = "",

    var petNameError: Boolean = false,
    var petBreedError: Boolean = false,
    var petGenderError: Boolean = false,
    var petColorError: Boolean = false,
    var petAgeError: Boolean = false,
    var petTypeError: Boolean = false,
    var petDescriptionError: Boolean = false,
    var petlocationError: Boolean = false,
    var bookingPriceError: Boolean = false,
)
