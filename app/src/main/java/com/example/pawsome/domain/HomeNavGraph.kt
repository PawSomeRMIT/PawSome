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
import com.example.pawsome.domain.graph.channelNavGraph
import com.example.pawsome.domain.graph.petsListNavGraph
import com.example.pawsome.domain.graph.settingNavGraph
import com.example.pawsome.domain.screens.BottomBarScreen
import com.example.pawsome.presentation.aboutus.AboutUsScreen
import com.example.pawsome.presentation.form.FormScreen

@Composable
fun HomeNavGraph(
    rootNavController: NavHostController,
    homeNavController: NavHostController,
) {
    NavHost(
        navController = homeNavController,
        route = Graph.HOME,
        startDestination = Graph.PETSLIST
    ) {
        petsListNavGraph(rootNavController = rootNavController, homeNavController = homeNavController)

        composable(BottomBarScreen.AboutUs.route) {
            AboutUsScreen(
                onClickBack = {
                    homeNavController.popBackStack()
                }
            )
        }

        composable(BottomBarScreen.FormScreen.route) {
            FormScreen(
                navHostController = homeNavController,
                onBackClick = {
                    homeNavController.popBackStack()
                }
            )
        }

        channelNavGraph(navController = homeNavController)

        settingNavGraph(rootNavController = rootNavController, homeNavController = homeNavController)
    }
}

