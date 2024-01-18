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
            (petName.isNotEmpty())
        )
    }

    fun validatePetBreed(petBreed: String) : ValidationResult {
        return ValidationResult(
            (petBreed.isNotEmpty())
        )
    }

    fun validatePetGender(petGender: String) : ValidationResult {
        return ValidationResult(
            (petGender.isNotEmpty() && (petGender.lowercase() == "male" ||
                                            petGender.lowercase() == "female" ||
                                            petGender.lowercase() == "m" ||
                                            petGender.lowercase() == "f"))
        )
    }

    fun validatePetColor(petColor: String) : ValidationResult {
        return ValidationResult(
            (petColor.isNotEmpty())
        )
    }

    fun validatePetType(petType: String) : ValidationResult {
        return ValidationResult(
            (petType.isNotEmpty() && (petType.lowercase() == "dog" ||
                                            petType.lowercase() == "cat"))
        )
    }

    fun validatePetAge(petAge: String) : ValidationResult {
        return try {ValidationResult(

            (petAge.isNotEmpty() && petAge.toInt() <= 15)
        )} catch (e:Exception){
            ValidationResult(false)
        }
    }


    fun validatePetLocation(petLocation: String) : ValidationResult {
        return ValidationResult(
            (petLocation.isNotEmpty())
        )
    }

    fun validatePetDescription(petDescription: String) : ValidationResult {
        return ValidationResult(
            (petDescription.isNotEmpty())
        )
    }

    fun validateBookingPrice(bookingPrice: String) : ValidationResult {
        return try { ValidationResult(
            (bookingPrice.isNotEmpty() && bookingPrice.toFloat() < 100)
        )} catch (e: Exception) {
            ValidationResult(false)
        }
    }
}