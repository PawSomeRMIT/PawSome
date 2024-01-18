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

package com.example.pawsome.domain.screens

sealed class Screen(val route: String) {

    object Register : Screen("register") {
        object Login : Screen("login")
        object Signup : Screen("signup")
    }

    object HomeScreen : Screen("PetListsScreen")
    object LoadingScreen : Screen("loading")
}
