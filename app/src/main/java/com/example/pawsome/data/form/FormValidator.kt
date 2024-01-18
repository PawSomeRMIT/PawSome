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

data class ValidationResult(
    val status: Boolean = false
)
object FormValidator {
    fun validatePetName(petName: String) : ValidationResult {
        return ValidationResult(
            (!petName.isNullOrEmpty())
        )
    }

    fun validatePetBreed(petBreed: String) : ValidationResult {
        return ValidationResult(
            (!petBreed.isNullOrEmpty())
        )
    }

    fun validatePetGender(petGender: String) : ValidationResult {
        return ValidationResult(
            (!petGender.isNullOrEmpty() && (petGender.lowercase() == "male" ||
                                            petGender.lowercase() == "female" ||
                                            petGender.lowercase() == "m" ||
                                            petGender.lowercase() == "f"))
        )
    }

    fun validatePetColor(petColor: String) : ValidationResult {
        return ValidationResult(
            (!petColor.isNullOrEmpty())
        )
    }

    fun validatePetType(petType: String) : ValidationResult {
        return ValidationResult(
            (!petType.isNullOrEmpty() && (petType.lowercase() == "dog" ||
                                            petType.lowercase() == "cat"))
        )
    }

    fun validatePetAge(petAge: String) : ValidationResult {
        return ValidationResult(
            (!petAge.isNullOrEmpty() && petAge.toInt() <= 15)
        )
    }


    fun validatePetLocation(petLocation: String) : ValidationResult {
        return ValidationResult(
            (!petLocation.isNullOrEmpty())
        )
    }

    fun validatePetDescription(petDescription: String) : ValidationResult {
        return ValidationResult(
            (!petDescription.isNullOrEmpty())
        )
    }

    fun validateBookingPrice(bookingPrice: String) : ValidationResult {
        return ValidationResult(
            (!bookingPrice.isNullOrEmpty() && bookingPrice.toFloat() < 100)
        )
    }
}