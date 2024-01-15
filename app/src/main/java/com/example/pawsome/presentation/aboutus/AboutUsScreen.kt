package com.example.pawsome.presentation.aboutus

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawsome.R
import com.example.pawsome.model.Member

@Composable
@Preview(showBackground = true)
fun AboutUsScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { }, modifier = Modifier.padding(top = 10.dp, start = 0.dp)) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go back"
                )
            }
            Image(
                modifier = Modifier
                    .width(250.dp)
                    .padding(top = 10.dp),
                painter = painterResource(id = R.drawable.pawsome),
                contentDescription = "Pawsome"
            )
            Spacer(modifier = Modifier.width(200.dp))
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize().padding(5.dp)
        ) {
            item {
                Text(text = "About PawSome 📚", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Text(
                    modifier = Modifier.padding(20.dp),
                    style = MaterialTheme.typography.body1,
                    text = "PawSome is an innovative pet booking app designed for animal lovers seeking the joy of pet companionship without long-term commitments. It offers a user-friendly platform where users can easily browse and book time with a variety of pets, catering to their preferences and schedules. With PawSome, you can experience the delightful moments of pet ownership, whether it's a cozy cuddle session, a playful afternoon, or a peaceful walk in the park"
                )
                Spacer(modifier = Modifier.height(16.dp))

            }
            item {
                Text(text = "Technologies 📱", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(AboutUsData().technology.size){ index ->
                val technology: Member = AboutUsData().technology[index]
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        technology.memberName,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = technology.memberImageResId),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
            item {
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "PawSome Team 👫", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(15.dp))
            }
            items(AboutUsData().members.size) { index ->
                val member = AboutUsData().members[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = member.memberImageResId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(125.dp)
                                .padding(10.dp)
                                .clip(CircleShape)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = member.memberName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            androidx.compose.material3.Text(
                                text = "Student ID: ${member.studentID}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            androidx.compose.material3.Text(
                                text = "Major: ${member.memberMajor}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Made With ❤️ From PawSome Team",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }

    }
}