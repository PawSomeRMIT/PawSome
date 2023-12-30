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

package com.example.pawsome.presentation.searchscreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pawsome.data.DataViewModel
import com.example.pawsome.data.login.LoginViewModel
import com.example.pawsome.data.login.LoginViewModelFactory
import com.example.pawsome.model.EventData
import com.example.pawsome.model.Screen
import com.example.pawsome.model.User
import com.example.pawsome.model.nearMe
import com.example.pawsome.model.popularEvents
import com.example.pawsome.model.sampleEvents
import com.example.pawsome.common.CommonVar
import com.example.pawsome.common.NormalText
import com.example.pawsome.presentation.homescreen.component.HorizontalHomeEventCard

import com.example.pawsome.presentation.homescreen.component.PopularEvents
import com.example.pawsome.presentation.homescreen.component.HeaderSeparator
import com.example.pawsome.presentation.searchscreen.component.MyLocationButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

@Composable
fun SearchResultsScreen(
    eventDetails: List<EventData> = sampleEvents,
    navHostController: NavHostController) {
    LazyColumn {
        item {
            MyLocationButton()
        }
        //Render a lít of items
        items(eventDetails.size) { items ->
            //Get the element of the event details
            val res = eventDetails[items]
            //Display to the card
            HorizontalHomeEventCard(
                inputImageUrl = res.imageUrl,
                nameEvent = res.eventName,
                location = res.location,
                date = res.eventTime,
                admin = res.organizerName
            ) {
//                navHostController.navigate(Screen.DetailScreen.route)
            }
        }
    }
}


@Composable
fun SearchBar(searchText: TextFieldValue, onSearchTextChanged: (TextFieldValue) -> Unit) {

    val auth = FirebaseAuth.getInstance()
    val userID = auth.currentUser?.uid
    var userData by remember(userID) {
        mutableStateOf(User())
    }

    LaunchedEffect(userID) {
        if (userID != null) {
            val userRef = Firebase.firestore.collection("user").document(userID)
            val snapshot = userRef.get().await()

            snapshot?.let {
                snapshot.toObject<User>()?.let {
                    userData = it
                }
            }
        }
    }

    Column(modifier = Modifier.padding(top = 20.dp)) {

        Row(modifier = Modifier.padding(start = 20.dp)) {
            NormalText(value = "👋🏻 Hi ${userData.firstname} ${userData.lastname}",)
        }

        TextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            leadingIcon = { Icon(Icons.Default.Search, "Search Icon") },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("Search here...") }
        )
    }
}


@Composable
fun SearchResults(
    searchText: String = "AD",
    selectedFilter: String = "",
    navHostController: NavHostController,
    dataViewModel: DataViewModel = viewModel()
) {

    // Event list for storing fetching data
    val eventsList = dataViewModel.eventsList.value

    Log.d("EventList", "${eventsList.size}")

    // Coroutine scope for asynchronous operations
    val coroutineScope = rememberCoroutineScope()

    // Check data loading
    val isLoading = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        //TODO: Need a function to fetch the data and state to manage that
        if (searchText.isNotEmpty()) {
            // Display filtered results based on searchText and selectedFilter
            Column {
                //Have a if to check if filter is selected
                //Have a list if filter not found
                Text(
                    text = "Search result for ${searchText} + filter ${selectedFilter}",
                    fontWeight = FontWeight.Bold,
                    fontSize = CommonVar.fontSizeSmall
                )
                SearchResultsScreen(navHostController = navHostController)
            }
            // Here you would query your data source based on the search text and selected filter
            // and display the results.
        } else {
            if (isLoading.value) {
                Text(text = "Loading data...")
            } else {
                LazyColumn {
                    // Section for "Near Me" events
                    item {
                        HeaderSeparator(title = "Near Me")
                    }
                    items(nearMe.size) { index ->
                        val event = nearMe[index]
                        HorizontalHomeEventCard(
                            inputImageUrl = event.imageUrl,
                            nameEvent = event.eventName,
                            location = event.location,
                            date = event.eventTime,
                            admin = event.organizerName
                        ) {
//                            navHostController.navigate(Screen.DetailScreen.route)
                        }
                    }


                    // Section for "Popular" events with a horizontal scroll
                    item {
                        HeaderSeparator(title = "Popular")
                        PopularEvents(popularEvents, navHostController)
                    }

                    // Section for general events
                    item {
                        HeaderSeparator(title = "Events")
                    }
                    items(eventsList.size) { index ->
                        val event = eventsList[index]
                        HorizontalHomeEventCard(
                            inputImageUrl = event.imageUrl,
                            nameEvent = event.eventName,
                            location = event.location,
                            date = event.eventTime,
                            admin = event.organizerName
                        ) {
//                            navHostController.navigate(Screen.DetailScreen.route)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.padding(100.dp))
                    }
                }
            }
        }
    }
}
