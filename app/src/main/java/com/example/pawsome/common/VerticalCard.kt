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

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pawsome.R

@Composable
@Preview
fun VerticalCard() {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()

        ,
        elevation = CardDefaults.cardElevation(defaultElevation =10.dp)
    ) {

        Column(modifier = Modifier.padding(8.dp)) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(
                    "data:image/jpeg;base64,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"
                )
                    .crossfade(true)
                    .build(),
//                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = "Image description",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Dance party at the top of the town - 2022",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn, // Built-in icon from the material icons library
                    contentDescription = "Location Icon",
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "New York",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(Color.Green),
                    modifier = Modifier
                        .border(1.dp, Color.Green, RoundedCornerShape(50))
                        .height(30.dp)
                ) {
                    Text(
                        text = "Completed",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("View Detail")
                }
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(Color.Magenta),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Write a Review", color = Color.White)
                }
            }
        }
    }
}

