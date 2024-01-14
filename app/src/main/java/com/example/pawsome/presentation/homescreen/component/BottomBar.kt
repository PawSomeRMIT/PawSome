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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Map
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pawsome.R
import com.example.pawsome.data.form.DataViewModel
import com.example.pawsome.domain.Graph
import com.example.pawsome.domain.screens.BottomBarScreen

@Composable
fun BottomBar(navController: NavController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.ChannelsList,
        BottomBarScreen.Payment,
        BottomBarScreen.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route}

    var currentIcon by remember { mutableIntStateOf(0) }

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
//                    navController.navigate(Graph.PETSLIST)

                    navController.navigate(Graph.PETSLIST) {
                        popUpTo(0) {
                        }
                    }

                    currentIcon = 0
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
//                    navController.navigate(BottomBarScreen.Payment.route)

                    navController.navigate(BottomBarScreen.Payment.route) {
                        popUpTo(BottomBarScreen.Payment.route) {
                            inclusive = true
                        }
                    }

                    currentIcon = 1
                }
            ) {
                Icon(
                    Icons.Outlined.Map,
                    contentDescription = "Maps",
                    tint = colorResource(
                        id = if (currentDestination?.route == BottomBarScreen.Payment.route) R.color.yellow
                        else R.color.gray
                    ),
                    modifier = Modifier.size(35.dp)
                    )
            }

            Spacer(Modifier.weight(1f, true))
            Box(Modifier.size(48.dp)) // Placeholder for the FAB
            Spacer(Modifier.weight(1f, true))

            IconButton(
                onClick = {
//                    navController.navigate(Graph.CHAT)

                    navController.navigate(Graph.CHAT) {
                        popUpTo(0) {
                        }
                    }

                    currentIcon = 2
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

            IconButton(
                onClick = {
//                    navController.navigate(BottomBarScreen.Settings.route)

                    navController.navigate(BottomBarScreen.Settings.route) {
                        popUpTo(0) {
                        }
                    }

                    currentIcon = 3
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

@Composable
fun CenterActionButton(navController: NavController,
                       dataViewModel: DataViewModel = viewModel()) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.ChannelsList,
        BottomBarScreen.Payment,
        BottomBarScreen.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route}

    if (bottomBarDestination) {
        FloatingActionButton(
            backgroundColor = colorResource(id = R.color.yellow),
            onClick = {
                navController.navigate(BottomBarScreen.FormScreen.route)
            }
        ) {
            Icon(
                Icons.Outlined.Add,
                contentDescription = "Add new pet detail",
                tint = Color.White
            )
        }
    }


//    FloatingActionButton(
//        onClick = {
//            navController.navigate(BottomBarScreen.FormScreen.route)
//        }
//    ) {
//        Icon(Icons.Outlined.Add, contentDescription = "Add new pet detail")
//    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { /* ... */ }
        composable("profile") { /* ... */ }
        composable("favorites") { /* ... */ }
        composable("settings") { /* ... */ }
        composable("refresh") { /* ... */ }
    }
}
