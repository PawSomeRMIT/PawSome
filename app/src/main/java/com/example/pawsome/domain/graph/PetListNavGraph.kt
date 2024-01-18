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

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.pawsome.domain.Graph
import com.example.pawsome.domain.screens.PetsListScreen
import com.example.pawsome.model.PetDetail
import com.example.pawsome.model.User
import com.example.pawsome.presentation.detailscreen.DetailScreen
import com.example.pawsome.presentation.homescreen.HomeContent
import com.example.pawsome.presentation.kycscreen.EKYCUploadScreen
import com.google.android.gms.maps.model.LatLng

fun NavGraphBuilder.petsListNavGraph(
    rootNavController: NavHostController,
    homeNavController: NavHostController
) {
    navigation(
        route = Graph.PETSLIST,
        startDestination = PetsListScreen.PetsList.route
    ) {
        composable(route = PetsListScreen.PetsList.route) {
            val location: LatLng? =
                rootNavController.previousBackStackEntry?.savedStateHandle?.get("location")

            val user: User? =
                rootNavController.previousBackStackEntry?.savedStateHandle?.get("user")

            Log.d("NAV", user.toString())

            if (location != null && user != null) {
                HomeContent(navHostController = homeNavController, location = location, user = user)
            }
        }

        channelNavGraph(navController = homeNavController)

        composable(route = PetsListScreen.DetailScreen.route) {
            val petDetail: PetDetail? = homeNavController.previousBackStackEntry?.savedStateHandle?.get("petDetail")

            val owner: User? = homeNavController.previousBackStackEntry?.savedStateHandle?.get("owner")

            if (petDetail != null && owner != null) {
                DetailScreen(
                    petDetail = petDetail,
                    owner = owner,
                    onBackClick = {
                        homeNavController.popBackStack()
                    },
                    navHostController = homeNavController
                )
            }
        }

        composable(route = PetsListScreen.EKYCCheckingScreen.route) {
            val petDetail: PetDetail? = homeNavController.previousBackStackEntry?.savedStateHandle?.get("petDetail")

            if (petDetail != null) {
                EKYCUploadScreen(
                    navHostController = homeNavController,
                    petDetail = petDetail
                )
            }
        }
    }
}