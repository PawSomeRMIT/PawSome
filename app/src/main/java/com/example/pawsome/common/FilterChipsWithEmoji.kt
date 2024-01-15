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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawsome.R
import com.example.pawsome.model.FilterChipData

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterChipsWithEmoji(
    filterOptions: List<FilterChipData>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .padding(horizontal = 10.dp)
    ) {
        item {
            filterOptions.forEach { filter ->
                CategoryChip(
                    text = filter.category,
                    emoji = filter.emoji,
                    onChipClick = {
                        onFilterSelected(it)
                                  },
                    isSelected = selectedFilter == filter.category
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryChip(
    text: String,
    emoji: String,
    onChipClick: (String) -> Unit,
    isSelected: Boolean
) {
    Chip(
        onClick = { onChipClick(text) },
        border = BorderStroke(1.dp,  if (isSelected) Color.Yellow else Color.Gray),
        colors = ChipDefaults.chipColors(backgroundColor = if (isSelected) colorResource(id = R.color.yellow) else Color.LightGray),
        modifier = Modifier
            .padding(8.dp)
            .clip(RectangleShape)
    ) {
        Text(text = emoji, fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
        Spacer(modifier = Modifier.padding(start = 4.dp, end = 4.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.clipToBounds()
        )
    }
}

