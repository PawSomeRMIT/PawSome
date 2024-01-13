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
