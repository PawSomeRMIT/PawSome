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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
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
import com.example.pawsome.model.User

@Composable
fun Profile(
    userData: User
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            userData.image,
            contentDescription = "Description",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(20.dp))

        Column {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TitleText(value = userData.username)

                if (userData.membership == "Subscribed") {
                    Spacer(modifier = Modifier.width(10.dp))

                    Icon(
                        imageVector = Icons.Filled.VerifiedUser,
                        contentDescription = "Icon description",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Green
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = userData.history.size.toString(),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )

                    NormalText(value = "bookings")
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(
                        id = if (userData.membership.lowercase() == "gold") R.drawable.gold_tier_icon else R.drawable.normal_tier_icon
                    ),
                    contentDescription = "Tier icon",
                    modifier = Modifier.size(30.dp)
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = userData.membership,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    )
            }
        }
    }
}