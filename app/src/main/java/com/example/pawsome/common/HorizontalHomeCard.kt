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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun HorizontalHomeCard(
    imageUrl: String,
    eventTitle: String,
    eventLocation: String,
    onEventClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp, // Add elevation here
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .clickable(onClick = onEventClick)
        ) {
            AsyncImage(
                if (imageUrl != "") imageUrl else CommonVar.placeHolderImageURL,
                contentDescription = "Description",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = eventTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(16.dp)) // Control the space between title and the location text
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Description",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = eventLocation,
                            fontSize = 16.sp
                        )
                    }
                    Button(
                        onClick = onEventClick,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 8.dp),
                        shape = RoundedCornerShape(50) // Circular edges
                    ) {
                        Text(text = "Join Now", textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

//Preview and stuff
@Preview(showBackground = true)
@Composable
fun HorizontalCardPreview() {
    HorizontalHomeCard(
        imageUrl = "https://via.placeholder.com/150",
        eventTitle = "Dance party at the top of the town - 2022",
        eventLocation = "New York",
        onEventClick = { /* TODO: Insert click handling logic here */ }
    )
}