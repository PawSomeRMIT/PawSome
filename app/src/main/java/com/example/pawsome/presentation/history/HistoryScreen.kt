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

@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pawsome.presentation.history

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.ContactSupport
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pawsome.R
import com.example.pawsome.common.ColorButton
import com.example.pawsome.domain.Graph
import com.example.pawsome.model.Booking
import com.example.pawsome.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavHostController,
    historyViewModel: HistoryViewModel = hiltViewModel(),
) {
    val bookings by historyViewModel.bookings.observeAsState(initial = emptyList())

    LaunchedEffect(key1 = Unit) {
        historyViewModel.fetchBookings()
    }

    val scope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // Check data loading
    val loadingState by historyViewModel.isLoading.collectAsState(initial = false)

    if (loadingState) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = colorResource(id = R.color.yellow)
            )
        }
    } else {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }, modifier = Modifier.padding(top = 10.dp, start = 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }

                    Text(
                        text = "Booking History",
                        modifier = Modifier.padding(top = 10.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Left
                    )
                    Spacer(modifier = Modifier.width(400.dp))
                }
            }
        ) { paddingValues ->
            if (bookings.isEmpty()) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "You have no completed booking yet 😄",
                        fontSize = 22.sp,
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {


                    items(bookings.size) { index ->
                        val booking = bookings[index]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp, horizontal = 15.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(5.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 10.dp, horizontal = 20.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Customer Verified Name: ${booking.customerCardIdName}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )
                                Text(
                                    text = "Customer Identify Card: ${booking.customerCardIdNumber}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )

                                Text(
                                    text = "Pet Name: ${booking.petDetail!!.petName}",
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )

                                Text(
                                    text = booking.petDetail.petBreed + " " + booking.petDetail.petAnimal,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )
                                Text(
                                    text = "Total: ${booking.totalPrice} $",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    ColorButton(
                                        text = "Contact Owner",
                                        color = colorResource(id = R.color.yellow),
                                        icon = Icons.Outlined.ContactSupport,
                                        modifier = Modifier.padding(
                                            vertical = 8.dp,
                                            horizontal = 20.dp
                                        ),
                                        onClick = {
                                            scope.launch {
                                                val db = FirebaseFirestore.getInstance()
                                                var owner = User()
                                                var user = User()

                                                val userId =
                                                    FirebaseAuth.getInstance().currentUser?.uid
                                                        ?: ""

                                                Log.d("HISTORYSCREEN", booking.petDetail.ownerId)

                                                var querySnapshot = db.collection("user")
                                                    .document(userId)
                                                    .get()
                                                    .await()

                                                querySnapshot?.let {
                                                    val result = User(
                                                        userID = it.get("userID").toString(),
                                                        username = it.get("username").toString(),
                                                        email = it.get("email").toString(),
                                                        image = it.get("image").toString(),
                                                        membership = it.get("membership")
                                                            .toString(),
                                                        chatToken = it.get("chatToken").toString(),
                                                        history = it.get("history") as List<Booking>
                                                    )

                                                    user = result
                                                }

                                                Log.d("HISTORYSCREEN", user.toString())

                                                querySnapshot = db.collection("user")
                                                    .document(booking.petDetail.ownerId)
                                                    .get()
                                                    .await()

                                                querySnapshot?.let {
                                                    val result = User(
                                                        userID = it.get("userID").toString(),
                                                        username = it.get("username").toString(),
                                                        email = it.get("email").toString(),
                                                        image = it.get("image").toString(),
                                                        membership = it.get("membership")
                                                            .toString(),
                                                        chatToken = it.get("chatToken").toString(),
                                                        history = it.get("history") as List<Booking>
                                                    )

                                                    owner = result
                                                }

                                                Log.d("HISTORYSCREEN", owner.toString())

                                                val channelID = historyViewModel.createChannel(
                                                    owner.email.split("@")[0],
                                                    user.email.split("@")[0]
                                                )

                                                Log.d("HISTORYSCREEN", channelID)

                                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                                    "channelId",
                                                    channelID
                                                )

                                                navController.navigate(Graph.CHAT)
                                            }
                                        }

                                    )
                                }
                            }
                        }
                    }


                }
            }
        }
    }

}
