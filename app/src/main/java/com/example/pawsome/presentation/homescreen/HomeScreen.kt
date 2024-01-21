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


package com.example.pawsome.presentation.homescreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pawsome.R
import com.example.pawsome.common.FilterChipsWithEmoji
import com.example.pawsome.domain.HomeNavGraph
import com.example.pawsome.domain.screens.PetsListScreen
import com.example.pawsome.model.User
import com.example.pawsome.presentation.homescreen.component.BottomBar
import com.example.pawsome.presentation.homescreen.component.CenterActionButton
import com.example.pawsome.presentation.homescreen.component.HorizontalHomeEventCard
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    rootNavController: NavHostController,
) {
    val homeNavController = rememberNavController()

    // Get current user session
    val auth = FirebaseAuth.getInstance()
    val userID = auth.currentUser?.uid

    Scaffold(
        bottomBar = {
            BottomBar(navController = homeNavController)
                    },
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            CenterActionButton(navController = homeNavController)
                               },
        floatingActionButtonPosition = androidx.compose.material.FabPosition.Center,
    ) {
        HomeNavGraph(
            rootNavController = rootNavController,
            homeNavController = homeNavController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeContent(
    navHostController: NavHostController,
    location: LatLng,
    user: User
) {
    val context = LocalContext.current

    val homeScreenViewModel: HomeScreenViewModel = viewModel(factory = viewModelFactory {
        HomeScreenViewModel(currentLocation = location, user = user)
    })

    val scope = rememberCoroutineScope()

    val searchText by homeScreenViewModel.searchText.collectAsState()

    val displayPetsList by homeScreenViewModel.matchedPets.collectAsState()

    // Check data loading
    val loadingState by homeScreenViewModel.isLoading.collectAsState(initial = false)

    val filterSelected by homeScreenViewModel.filterType.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(top = 10.dp)
        ) {
            TextField(
                value = searchText,
                onValueChange = homeScreenViewModel::onSearchTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = {
                    Text(text = "Search")
                },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = colorResource(id = R.color.light_gray),
                    unfocusedIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search, "",
                        tint = Color.Black
                    )
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onDone = {

                    },
                )
            )
        }

        FilterChipsWithEmoji(
            filterOptions = homeScreenViewModel.filterOptions,
            selectedFilter = filterSelected
        ) {
//            filterSelected.category = it
            homeScreenViewModel.onFilterTypeChange(filterType = it)
        }


        if (loadingState) {
            androidx.compose.material.Text(text = "Loading data...")
        }
        else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 20.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    userScrollEnabled = true
                ) {
                    items(displayPetsList) { pet ->
                        //Display to the card
                        HorizontalHomeEventCard(
                            petDetail = pet,
                            onEventClick = {
                                scope.launch {
                                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                        "petDetail",
                                        pet
                                    )

                                    val owner = homeScreenViewModel.getUserFromFireStore(uId = pet.ownerId)

                                    Log.d("Before nav", owner.toString())

                                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                        "owner",
                                        owner
                                    )

                                    navHostController.navigate(PetsListScreen.DetailScreen.route)
                                }
                            }
                        )

                        if (pet == displayPetsList[displayPetsList.size-1]) {
                            Spacer(modifier = Modifier.fillMaxWidth().height(75.dp))
                        }
                    }
                }
            }
        }

    }
}

fun Date.toFormattedString(): String {
    val simpleDateFormat = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}

inline fun viewModelFactory(crossinline f: () -> HomeScreenViewModel) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = f() as T
    }

