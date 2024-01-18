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

package com.example.pawsome.domain

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.pawsome.common.LoadingScreen
import com.example.pawsome.domain.screens.Screen
import com.example.pawsome.presentation.authentication.login.Login
import com.example.pawsome.presentation.authentication.register.Signup
import com.example.pawsome.presentation.homescreen.HomeScreen

/**
 * Create navigation link to screens
 */
@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
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
            HomeScreen(rootNavController = navHostController)
        }

        // Loading Screen
        composable(Screen.LoadingScreen.route) {
            LoadingScreen()
        }

    }
}