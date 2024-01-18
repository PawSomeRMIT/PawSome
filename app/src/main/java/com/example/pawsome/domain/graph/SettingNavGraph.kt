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

package com.example.pawsome.domain.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.pawsome.domain.Graph
import com.example.pawsome.domain.screens.SettingScreen
import com.example.pawsome.model.PetDetail
import com.example.pawsome.model.User
import com.example.pawsome.presentation.form.FormScreen
import com.example.pawsome.presentation.history.HistoryScreen
import com.example.pawsome.presentation.settings.SettingScreen
import com.example.pawsome.presentation.settings.components.MyPetListScreen

fun NavGraphBuilder.settingNavGraph(
    rootNavController: NavHostController,
    homeNavController: NavHostController
) {
    navigation(
        route = Graph.SETTING,
        startDestination = SettingScreen.SettingMenu.route
    ) {
        composable(route = SettingScreen.SettingMenu.route) {
            SettingScreen(navController = homeNavController, rootNavController = rootNavController)
        }

        composable(route = SettingScreen.BookingHistory.route) {
            HistoryScreen(navController = homeNavController)

        }

        composable(route = SettingScreen.MyPetList.route) {
            val user: User? = homeNavController.previousBackStackEntry?.savedStateHandle?.get("user")

            if (user != null) {
                MyPetListScreen(navHostController = homeNavController, user = user)
            }
        }

        composable(route = SettingScreen.EditPet.route) {
            val petDetail: PetDetail? = homeNavController.previousBackStackEntry?.savedStateHandle?.get("petDetail")

            if (petDetail != null) {
                FormScreen(
                    navHostController = homeNavController,
                    petDetail = petDetail,
                    onBackClick = {
                        homeNavController.popBackStack()
                    }
                )
            }
        }
    }
}