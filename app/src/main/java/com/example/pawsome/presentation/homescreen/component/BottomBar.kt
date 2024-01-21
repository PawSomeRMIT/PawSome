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

package com.example.pawsome.presentation.homescreen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pawsome.R
import com.example.pawsome.domain.Graph
import com.example.pawsome.domain.screens.BottomBarScreen

@Composable
fun BottomBar(navController: NavController) {
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
        BottomAppBar(
            cutoutShape = MaterialTheme.shapes.small.copy(
                androidx.compose.foundation.shape.CornerSize(
                    percent = 50
                )
            ),
            contentPadding = PaddingValues(horizontal = 15.dp, vertical = 5.dp),
            backgroundColor = Color.White
        ) {
            IconButton(
                onClick = {
                    navController.navigate(Graph.PETSLIST) {
                        popUpTo(Graph.PETSLIST) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = colorResource(
                        id = if (currentDestination?.route == BottomBarScreen.Home.route) R.color.yellow
                        else R.color.gray
                    ),
                    modifier = Modifier.size(35.dp)
                )

            }

            Spacer(Modifier.weight(1f, true))

            IconButton(
                onClick = {
                    navController.navigate(Graph.CHAT) {
                        popUpTo(Graph.CHAT) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Icon(
                    Icons.Filled.ChatBubble,
                    contentDescription = "Chat List",
                    tint = colorResource(
                        id = if (currentDestination?.route == BottomBarScreen.ChannelsList.route) R.color.yellow
                        else R.color.gray
                    ),
                    modifier = Modifier.size(35.dp)
                )

            }

            Spacer(Modifier.weight(1f, true))

            Spacer(Modifier.weight(1f, true))
            Box(Modifier.size(48.dp)) // Placeholder for the FAB
            Spacer(Modifier.weight(1f, true))

            IconButton(
                onClick = {
                    navController.navigate(BottomBarScreen.AboutUs.route) {
                        popUpTo(BottomBarScreen.AboutUs.route) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = "About us",
                    tint = colorResource(
                        id = if (currentDestination?.route == BottomBarScreen.AboutUs.route) R.color.yellow
                        else R.color.gray
                    ),
                    modifier = Modifier.size(35.dp)
                )
            }

            Spacer(Modifier.weight(1f, true))

            IconButton(
                onClick = {
                    navController.navigate(Graph.SETTING) {
                        popUpTo(Graph.SETTING) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = colorResource(
                        id = if (currentDestination?.route == BottomBarScreen.Settings.route) R.color.yellow
                            else R.color.gray
                    ),
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    }
}


