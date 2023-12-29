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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pawsome.common.Avatar
import com.example.pawsome.common.CommonVar


@Composable
fun VerticalHomeEventCard(
    inputImageAddressURI: String,
    inputProfileURI: String,
    admin: String,
    name: String,
    attendee: String,
    countJoin: String,
    actionClickHandler: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(17.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(17.dp)
    ) {

        Column(modifier = Modifier.padding(9.dp)) {
            Row(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(7.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                var url = if (inputImageAddressURI != "") inputImageAddressURI else CommonVar.placeHolderImageURL // Use 'imageUrl' parameter
                AsyncImage(
                    model = url,
                    contentDescription = "imagePlaceHolder",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Avatar(imageUrl = inputProfileURI) // Use 'profileImageUrl' parameter
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    admin,
                    fontWeight = FontWeight.Bold
                ) // Use 'organizerName' parameter
            }
            Column(modifier = Modifier.padding(3.dp)) {
                Text(
                    name, // Use 'eventName' parameter
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    attendee, // Use 'eventTime' parameter
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            inputImage(inputProfileURI, countJoin, actionClickHandler)
        }
    }
}

@Composable
private fun inputImage(
    inputProfileURI: String,
    countJoin: String,
    actionClickHandler: () -> Unit
) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Avatar(imageUrl = inputProfileURI) // Use 'profileImageUrl' parameter again for attendee avatars
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            countJoin, color = Color.White, modifier = Modifier
                .clip(CircleShape)
                .background(Color.LightGray)
                .padding(4.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = actionClickHandler, // Use 'onActionClick' parameter
            //TODO: Could change color right here
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
        ) {
            Text("Join Now", color = Color.White) // Use 'actionText' parameter
        }
    }
}


@Preview(showBackground = true)
@Composable
fun VerticalEventCardPreview() {
    VerticalHomeEventCard(
        inputImageAddressURI = "",
        inputProfileURI = "",
        admin = "Altaniagsdgasto Salami",
        name = "Cleaningu",
        attendee = "FRI 27 May, 10:00",
        countJoin = "+15"
    ) { /* Handle action click */ }
}