package com.example.pawsome.data.form

sealed class FormUIEvent {
    data class PetNameChanged(val petName: String): FormUIEvent()
    data class PetBreedChanged(val petBreed: String): FormUIEvent()
    data class PetGenderChanged(val petGender: String): FormUIEvent()
    data class PetColorChanged(val petColor: String): FormUIEvent()
    data class PetAgeChanged(val petAge: String): FormUIEvent()
    data class PetTypeChanged(val petType: String): FormUIEvent()
    data class PetDescriptionChanged(val petDescription: String): FormUIEvent()
    data class PetLocationChanged(val petLocation: String): FormUIEvent()
    data class BookingPriceChanged(val bookingPrice: String): FormUIEvent()

    object SaveButtonClicked: FormUIEvent()
}