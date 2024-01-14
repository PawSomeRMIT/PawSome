package com.example.pawsome.domain

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.pawsome.domain.screens.BottomBarScreen
import com.example.pawsome.model.PetDetail
import com.example.pawsome.model.User
import com.example.pawsome.presentation.add_form.Form
import com.example.pawsome.presentation.chatscreen.channelListScreen.ChannelsListScreen
import com.example.pawsome.presentation.chatscreen.channelScreen.ChannelScreen
import com.example.pawsome.presentation.detailscreen.DetailScreen
import com.example.pawsome.presentation.homescreen.HomeContent
import com.example.pawsome.presentation.settings.SettingScreen
import com.google.android.gms.maps.model.LatLng
import drawable.EKYCUploadScreen

@Composable
fun HomeNavGraph(
    rootNavController: NavHostController,
    homeNavController: NavHostController,
    userID: String?,
) {
    NavHost(
        navController = homeNavController,
        route = Graph.HOME,
        startDestination = Graph.PETSLIST
    ) {
        petsListNavGraph(rootNavController = rootNavController, homeNavController = homeNavController)

        composable(BottomBarScreen.Settings.route) {
            SettingScreen(navController = homeNavController, rootNavController = rootNavController)
        }

//        composable(BottomBarScreen.Payment.route) {
//            PaymentScreen(navController = navController)
//        }

        channelNavGraph(navController = homeNavController)

    }
}

fun NavGraphBuilder.petsListNavGraph(
    rootNavController:NavHostController,
    homeNavController: NavHostController
) {
    navigation(
        route = Graph.PETSLIST,
        startDestination = PetsListScreen.PetsList.route
    ) {
        composable(route = PetsListScreen.PetsList.route) {
            val location: LatLng? = rootNavController.previousBackStackEntry?.savedStateHandle?.get("location")

            val user: User? = rootNavController.previousBackStackEntry?.savedStateHandle?.get("user")

            Log.d("NAV", user.toString())

            if (location != null && user != null) {
                HomeContent(navHostController = homeNavController, location = location, user = user)
            }
        }

        composable(BottomBarScreen.FormScreen.route) {
            Form(navHostController = homeNavController)
        }

        channelNavGraph(navController = homeNavController)
        composable(route = PetsListScreen.DetailScreen.route) {
            val petDetail: PetDetail? = homeNavController.previousBackStackEntry?.savedStateHandle?.get("petDetail")

            val owner: User? = homeNavController.previousBackStackEntry?.savedStateHandle?.get("owner")

            if (petDetail != null && owner != null) {
                DetailScreen(
                    petDetail = petDetail,
                    owner = owner,
                    onVideoCall = { /*TODO*/ },
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

sealed class PetsListScreen(val route: String) {
    object PetsList: PetsListScreen(route = "PetsListScreen")
    object DetailScreen: PetsListScreen(route = "DetailScreen")

    object EKYCCheckingScreen: PetsListScreen(route = "EKYCCheckingScreen")
}

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

sealed class ChatScreen(val route: String) {
    object ChannelsList: ChatScreen(route = "ChannelsList")
    object Channel: ChatScreen(route = "Channel")
}