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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
@Preview
fun Avatar(imageUrl: String = "https://st3.depositphotos.com/13159112/17145/v/1600/depositphotos_171453724-stock-illustration-default-avatar-profile-icon-grey.jpg",
           size: Dp = 36.dp,
           modifier: Modifier = Modifier
               .size(size)
               .clip(CircleShape)
               .background(Color.Gray)) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Avatar",
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}


