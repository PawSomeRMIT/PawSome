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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.pawsome.common.LoadingScreen
import com.example.pawsome.domain.screens.Screen
import com.example.pawsome.presentation.authentication.Login
import com.example.pawsome.presentation.authentication.Signup
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

//        // Form Screen
//        composable(Screen.FormScreen.route) {
//            Form(navHostController = navHostController)
//        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val CHAT = "chat_graph"
    const val PETSLIST = "petList_graph"
}