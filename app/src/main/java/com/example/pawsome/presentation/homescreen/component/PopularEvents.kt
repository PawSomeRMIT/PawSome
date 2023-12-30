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

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.pawsome.model.EventData
import com.example.pawsome.model.Screen

@Composable
fun PopularEvents(eventsList: List<EventData>,
                  navHostController: NavHostController) {
    LazyRow {
        items(eventsList.size) { i ->
            val currentEvent = eventsList[i]
            VerticalHomeEventCard(
                inputImageAddressURI = currentEvent.imageUrl,
                inputProfileURI = "",
                admin = currentEvent.organizerName,
                name = currentEvent.eventName,
                attendee = currentEvent.eventTime,
                countJoin = currentEvent.attendeeCount.toString()
            ) {
//                navHostController.navigate(Screen.DetailScreen.route)
            }
        }
    }
}