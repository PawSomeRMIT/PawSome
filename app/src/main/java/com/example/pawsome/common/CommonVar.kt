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

package com.example.pawsome.common
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.pawsome.model.MarkerInfo
import com.google.android.gms.maps.model.LatLng

sealed class CommonVar(

) {
    companion object {
        val fontSizeLarge: TextUnit = 38.sp
        val fontSizeMedium: TextUnit = 28.sp
        val fontSizeSmall: TextUnit = 16.sp
        val appName: String = "Earthling"
        val author: String = "Nhat Bui"
        val studentNumber: String = "s3878174"
        val introduction: String =
            "EarthLing is an innovative mobile app that unites eco-conscious individuals worldwide. It offers a platform for sharing sustainable practices, engaging in environmental discussions, and participating in eco-friendly initiatives. Aimed at fostering global responsibility towards the environment, EarthLing empowers users to take practical steps towards combating climate change and promoting conservation, all through a user-friendly and interactive interface."
        val placeHolderImageURL: String =
            "https://source.unsplash.com/random/970x646/?volunteer%20outdoor%20activity"
        val placeHolderAvatarImageURL: String =
            "https://source.unsplash.com/random/970x646/?example%20person%20profile"
        val placeHolderStatImageURL: String =
            "https://img.freepik.com/free-vector/progress-overview-concept-illustration_114360-5262.jpg"
        val placeHolderAboutUsImage: String =
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTagisXNHdeWqXgu5FIpjLoDac-WphknlleFUIDEyMTGOc0-RD8iR7tEKPVYOM&s"
        val sampleMarkerVal = listOf(
            MarkerInfo(LatLng(51.508610, -0.163611), placeHolderImageURL),
            MarkerInfo(LatLng(51.5074, -0.1278), placeHolderImageURL)
            // Add more markers as needed
        )
    }
}