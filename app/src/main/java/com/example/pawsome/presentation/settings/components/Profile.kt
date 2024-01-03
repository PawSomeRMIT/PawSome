package com.example.pawsome.presentation.settings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pawsome.R
import com.example.pawsome.common.NormalText
import com.example.pawsome.common.TitleText

@Composable
fun Profile(
    inputImageUrl: String?,
    name: String,
    tier: String
) {
    Row(
        modifier = Modifier
            .background(Color(248, 248, 248, 255))
            .padding(6.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            if (!inputImageUrl.isNullOrEmpty()) inputImageUrl else R.drawable.default_avatar_profile_icon,
            contentDescription = "Description",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(155.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(20.dp))

        Column {
            TitleText(value = name)
            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "3",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    NormalText(value = "bookings")
                }

                Spacer(modifier = Modifier.width(20.dp))


                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "3",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    NormalText(value = "forms created")
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row {
                Image(
                    painter = painterResource(
                        id = if (tier.lowercase() == "gold") R.drawable.gold_tier_icon else R.drawable.normal_tier_icon
                    ),
                    contentDescription = "Tier icon")
                Spacer(modifier = Modifier.width(5.dp))
                Text(tier)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfile() {
    Profile(inputImageUrl = "https://i.postimg.cc/7hjg2wZm/836.jpg",
        name = "John Doe",
        tier = "Gold")
}