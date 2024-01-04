package com.example.pawsome.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.pawsome.domain.screens.BottomBarScreen
import com.example.pawsome.model.EventData
import com.example.pawsome.presentation.chatscreen.channelListScreen.ChannelsListScreen
import com.example.pawsome.presentation.chatscreen.channelScreen.ChannelScreen
import com.example.pawsome.presentation.homescreen.HomeContent
import com.example.pawsome.presentation.settings.SettingScreen

@Composable
fun HomeNavGraph(
    rootNavController: NavHostController,
    navController: NavHostController,
    eventsList: List<EventData>,
    userID: String?,
    upcomingEvent: MutableState<List<EventData>>
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(BottomBarScreen.Home.route) {
            HomeContent(navHostController = navController)
        }

        composable(BottomBarScreen.Settings.route) {
            SettingScreen(navController = navController, rootNavController = rootNavController)
        }

//        composable(BottomBarScreen.ChannelsList.route) {
//            ChannelsListScreen(navController = navController)
//        }
        channelNavGraph(navController = navController)

    }
}

fun NavGraphBuilder.channelNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.CHAT,
        startDestination = ChatScreen.ChannelsList.route
    ) {
        composable(route = ChatScreen.ChannelsList.route) {
            ChannelsListScreen(navController = navController)
        }

        composable(route = ChatScreen.Channel.route) {
            val channelId: String? = navController.previousBackStackEntry?.savedStateHandle?.get("channelId")

            if (channelId != null) {
                ChannelScreen(navController, channelId)
            }
        }
    }
}

sealed class ChatScreen(val route: String) {
    object ChannelsList: ChatScreen(route = "ChannelsList")
    object Channel: ChatScreen(route = "Channel")
}