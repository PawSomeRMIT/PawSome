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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pawsome.model.PetDetail

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HorizontalHomeEventCard(
    petDetail: PetDetail,
    editable: Boolean? = false,
    modifier: Modifier = Modifier,
    onEventClick: () -> Unit = {},
    onEditButtonClick:() -> Unit = {},
    onDeleteButtonClick:() -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp),
        onClick = onEventClick
    ) {
        Row(
            modifier = Modifier
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = petDetail.petName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.LocationOn,
                        contentDescription = "Location",
                        tint = Color.Black
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = petDetail.distance.toString() + " km",
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            AsyncImage(
                petDetail.img,
                contentDescription = "Description",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            if (editable != null) {
                if (editable) {
                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        IconButton(
                            onClick = onDeleteButtonClick,
                        ) {
                            Icon(imageVector = Icons.Filled.RemoveCircle,
                                contentDescription = "Remove icon",
                                tint = Color.Black)
                        }

                        IconButton(
                            onClick = onEditButtonClick,
                        ) {
                            Icon(imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit icon",
                                tint = Color.Black)
                        }
                    }


                }
            }
        }
    }
}
