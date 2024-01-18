package com.example.pawsome.presentation.homescreen.component

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pawsome.R
import com.example.pawsome.domain.Graph
import com.example.pawsome.domain.screens.BottomBarScreen

@Composable
fun CenterActionButton(
    navController: NavController
) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.ChannelsList,
        BottomBarScreen.AboutUs,
        BottomBarScreen.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route}

    if (bottomBarDestination) {
        FloatingActionButton(
            backgroundColor = colorResource(id = R.color.yellow),
            onClick = {
                navController.navigate(BottomBarScreen.FormScreen.route) {
                    popUpTo(BottomBarScreen.FormScreen.route) {
                        inclusive = true
                    }
                }
            }
        ) {
            Icon(
                Icons.Outlined.Add,
                contentDescription = "Add new pet detail",
                tint = Color.White
            )
        }
    }
}