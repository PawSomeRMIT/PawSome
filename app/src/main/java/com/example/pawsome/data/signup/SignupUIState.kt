/*
    RMIT University Vietnam
    Course: COSC2657 Android Development
    Semester: 2023C
    Assessment: Assignment 2
    Author: Lai Nghiep Tri
    ID: s3799602
    Created  date: 19/12/2023
    Last modified: 20/12/2023
    Acknowledgement: Figma UI, Android Developer documentation, Firebase Documentation, etc
 */


package com.example.pawsome.data.signup

data class SignupUIState(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var username: String = "",
    var image: String = "",
    val attendActivity: Int = 0,
    val membership: String = "normal",
    val userType: String = "normal",


    var firstNameError: Boolean = false,
    var lastNameError: Boolean = false,
    var emailError: Boolean = false,
    var userTypeError: Boolean = false,
    var passwordError: Boolean = false,
    var usernameError: Boolean = false
)