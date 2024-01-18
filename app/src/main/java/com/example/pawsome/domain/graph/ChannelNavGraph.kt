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
import com.example.pawsome.domain.screens.ChatScreen
import com.example.pawsome.presentation.chatscreen.channelListScreen.ChannelsListScreen
import com.example.pawsome.presentation.chatscreen.channelScreen.ChannelScreen

fun NavGraphBuilder.channelNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.CHAT,
        startDestination = ChatScreen.ChannelsList.route
    ) {
        composable(route = ChatScreen.ChannelsList.route) {
            val channelId: String? = navController.previousBackStackEntry?.savedStateHandle?.get("channelId")

            Log.d("CHECK", channelId.toString())

            ChannelsListScreen(navController = navController, channelID = channelId)
        }

        composable(route = ChatScreen.Channel.route) {
            val channelId: String? = navController.previousBackStackEntry?.savedStateHandle?.get("channelId")

            if (channelId != null) {
                ChannelScreen(navController, channelId)
            }
        }
    }
}