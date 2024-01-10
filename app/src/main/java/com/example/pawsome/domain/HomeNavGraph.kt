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
import com.example.pawsome.model.PetDetail
import com.example.pawsome.model.User
import com.example.pawsome.presentation.chatscreen.channelListScreen.ChannelsListScreen
import com.example.pawsome.presentation.chatscreen.channelScreen.ChannelScreen
import com.example.pawsome.presentation.detailscreen.DetailScreen
import com.example.pawsome.presentation.homescreen.HomeContent
import com.example.pawsome.presentation.payment.PaymentScreen
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
        startDestination = Graph.PETSLIST
    ) {
//        composable(BottomBarScreen.Home.route) {
//            HomeContent(navHostController = navController)
//        }
        petsListNavGraph(navController = navController)

        composable(BottomBarScreen.Settings.route) {
            SettingScreen(navController = navController, rootNavController = rootNavController)
        }

        composable(BottomBarScreen.Payment.route) {
            PaymentScreen(navController = navController)
        }

        channelNavGraph(navController = navController)

    }
}

fun NavGraphBuilder.petsListNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.PETSLIST,
        startDestination = PetsListScreen.PetsList.route
    ) {
        composable(route = PetsListScreen.PetsList.route) {
            HomeContent(navHostController = navController)
        }

        composable(route = PetsListScreen.DetailScreen.route) {
            val petDetail: PetDetail? = navController.previousBackStackEntry?.savedStateHandle?.get("petDetail")

            val owner: User? = navController.previousBackStackEntry?.savedStateHandle?.get("owner")

            if (petDetail != null && owner != null) {
                DetailScreen(
                    petDetail = petDetail,
                    owner = owner,
                    onAdoptClick = { /*TODO*/ },
                    onVideoCall = { /*TODO*/ },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

sealed class PetsListScreen(val route: String) {
    object PetsList: PetsListScreen(route = "PetsListScreen")
    object DetailScreen: PetsListScreen(route = "DetailScreen")
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