package com.example.pawsome.domain.screens

sealed class BottomBarScreen (
    val route: String
) {
    object Home : BottomBarScreen(
        route = "PetsListScreen"
    )

    object ChannelsList : BottomBarScreen(
        route = "ChannelsList"
    )

    object Settings : BottomBarScreen(
        route = "SettingMenu"
    )

    object BookingHistory: BottomBarScreen(
        route = "Booking History"
    )

    object FormScreen: BottomBarScreen(
        route = "form"
    )
}