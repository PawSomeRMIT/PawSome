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
}