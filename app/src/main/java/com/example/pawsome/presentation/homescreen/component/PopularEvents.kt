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
import com.example.pawsome.model.PetDetail

@Composable
fun PopularEvents(petDetailList: List<PetDetail>,
                  navHostController: NavHostController) {
    LazyRow {
        items(petDetailList.size) { i ->
            val currentPet = petDetailList[i]
            VerticalHomeEventCard(
                inputImageAddressURI = currentPet.img.toString(),
                inputProfileURI = "",
                admin = currentPet.ownerId,
                name = currentPet.petName
            ) {
//                navHostController.navigate(Screen.DetailScreen.route)
            }
        }
    }
}