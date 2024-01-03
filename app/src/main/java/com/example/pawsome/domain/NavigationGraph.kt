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


package com.example.pawsome.domain

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.pawsome.common.FailureScreen
import com.example.pawsome.common.LoadingScreen
import com.example.pawsome.model.Screen
import com.example.pawsome.presentation.authentication.Login
import com.example.pawsome.presentation.authentication.Signup
import com.example.pawsome.presentation.homescreen.HomeScreen

/**
 * Create navigation link to screens
 */
@Composable
fun NavigationGraph() {

    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = Screen.Register.route
    ) {
        navigation(
            route = Screen.Register.route,
            startDestination = Screen.Register.Login.route
        ) {

            // Login
            composable(Screen.Register.Login.route) {
                Login(navHostController = navHostController)
            }

            // Signup
            composable(Screen.Register.Signup.route) {
                Signup(navHostController = navHostController)
            }
        }

        // Home Screen
        composable(Screen.HomeScreen.route) {
            HomeScreen()
        }

        // Loading Screen
        composable(Screen.LoadingScreen.route) {
            LoadingScreen()
        }

        // Failure Screen
        composable(Screen.FailureScreen.route) {
            FailureScreen()
        }

        composable(
            route = "${Screen.FailureScreen.route}/{errorType}/{errorDesc}",
            arguments = listOf(
                navArgument("errorType") { type = NavType.StringType },
                navArgument("errorDesc") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            FailureScreen(
                errorType = backStackEntry.arguments?.getString("errorType") ?: "",
                errorDesc = backStackEntry.arguments?.getString("errorDesc") ?: ""
            )
        }
    }
}