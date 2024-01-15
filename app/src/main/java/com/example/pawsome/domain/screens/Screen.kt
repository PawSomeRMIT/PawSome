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

package com.example.pawsome.domain.screens

sealed class Screen(val route: String) {

//    object OnboardingScreen: Screen("onboarding_screen")

    object Register : Screen("register") {
        object Login : Screen("login")
        object Signup : Screen("signup")
    }

    object HomeScreen : Screen("PetListsScreen")
    object LoadingScreen : Screen("loading")
    object FailureScreen : Screen("failure")

    object FormScreen : Screen("form")
    object MyPetsScreen: Screen("my_pets")
}
