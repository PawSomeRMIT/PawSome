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


package com.example.pawsome.presentation.homescreen

import android.annotation.SuppressLint
import android.app.appsearch.SearchResults
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pawsome.data.DataViewModel
import com.example.pawsome.model.EventData
import com.example.pawsome.model.FilterChipData
import com.example.pawsome.common.CenterActionButton
import com.example.pawsome.common.FilterChipsWithEmoji
import com.example.pawsome.common.NavBar
import com.example.pawsome.presentation.searchscreen.SearchBar
import com.example.pawsome.presentation.searchscreen.SearchResults
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(dataViewModel: DataViewModel = viewModel()) {
    val navController = rememberNavController()

    // Get current user session
    val auth = FirebaseAuth.getInstance()
    val userID = auth.currentUser?.uid

    // Event list for storing fetching data
    val eventsList = dataViewModel.eventsList.value

    // Get upcoming event
    var upcomingEvent = remember {
        mutableStateOf(eventsList.filter {
            it.eventTime > Date().toFormattedString()
        })
    }

    Scaffold(
        bottomBar = { NavBar(navController = navController) },
        isFloatingActionButtonDocked = true,
        floatingActionButton = { CenterActionButton(navController = navController) },
        floatingActionButtonPosition = FabPosition.Center,
        content = navHost(navController, eventsList, userID, upcomingEvent),

        )
}

@Composable
private fun navHost(
    navController: NavHostController,
    eventsList: List<EventData>,
    userID: String?,
    upcomingEvent: MutableState<List<EventData>>
): @Composable (PaddingValues) -> Unit =
    {
        NavHost(navController, startDestination = "home") {
            composable(com.example.pawsome.model.Screen.HomeScreen.route) {
                HomeContent(navHostController = navController)
            }
//            composable(com.example.pawsome.model.Screen.AboutUsView.route) {
//                AboutUsView()
//            }
//
//            composable(com.example.pawsome.model.Screen.AddSiteScreen.route) {
//                AddSiteScreen(navHostController = navController)
//            }
//
//            composable(com.example.pawsome.model.Screen.DetailScreen.route) {
//                DetailScreen(navHostController = navController)
//            }
//
//            composable(com.example.pawsome.model.Screen.MapScreen.route) {
//                Log.e("Map", "${eventsList.size}")
//                MapScreen(inputEventData = eventsList)
//            }
//
//            composable(com.example.pawsome.model.Screen.Setting.route) {
//                Setting(navController = navController)
//            }
        }
    }

//TODO: Configuring routing strategy
@Composable
fun HomeContent(
    navHostController: NavHostController
) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var filterOptions = listOf(
        FilterChipData("🌊", "Beach"),
        FilterChipData("🏕️", "Forest"),
        FilterChipData("👨‍👩‍👧‍👦", "Popular"),
        FilterChipData("🏕️", "City"),
        FilterChipData("⭐️", "Trending"),
        FilterChipData("🥬", "Community"),
        FilterChipData("🏫", "School")
    )
    var filterSelected by remember { mutableStateOf(filterOptions[0]) }

    Column {
        SearchBar(searchText, onSearchTextChanged = { searchText = it })
        FilterChipsWithEmoji(
            filterOptions = filterOptions,
            selectedFilter = filterSelected.category
        ) { filterSelected.category = it }
        SearchResults(searchText.text, filterSelected.category,
            navHostController = navHostController)
    }
}

fun Date.toFormattedString(): String {
    val simpleDateFormat = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}


enum class Screen(val route: String, val icon: ImageVector, val title: String) {
    Home("home", Icons.Default.Home, "Home"),
    Profile("profile", Icons.Default.Person, "Profile"),
    Settings("settings", Icons.Default.Settings, "Settings")
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}

