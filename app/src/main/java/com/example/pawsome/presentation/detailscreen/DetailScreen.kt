package com.example.pawsome.presentation.detailscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pawsome.presentation.detailscreen.components.DetailChip
import com.example.pawsome.presentation.detailscreen.components.PriceAdoptButton
import com.example.pawsome.presentation.detailscreen.components.VideoCallButton

@Composable
fun CatProfileUI() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top back navigation arrow
        IconButton(onClick = { /* TODO: Handle back navigation */ }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        // Cat image using AsyncImage
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("http://api.labelary.com/v1/printers/8dpmm/labels/4x6/0/^xa^cfa,50^fo100,100^fdHello World^fs^xz")
                .crossfade(true)
                .build(),
            contentDescription = "Cat",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        // Cat information card
        Card(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(16.dp)
                .height(600.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Slash Mau", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Row {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = "Location",
                                modifier = Modifier.height(20.dp)
                            )
                            Text("California, Walk Suite")
                        }
                    }
                    VideoCallButton {

                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    item { DetailChip(text = "Gender: Male") }
                    item { DetailChip(text = "Breed: Persian") }
                    item { DetailChip(text = "Color: Brown") }
                    item { DetailChip(text = "Animal: Cat") }
                }

                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)) {
                    item {
                        UserProfile()
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Avaiable: ")
                            Text("Feb 25, 2022")
                        }
                    }
                    item {

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        Text("Meet Slash Mau, a charismatic Persian feline with a luxurious brown coat and sparkling eyes full of mischief. Born on the sunny shores of California, Slash Mau enjoys leisurely walks and cozy naps in sun-drenched spots. He's more than just a pet; he's a companion ready to bring warmth and joy into your home. His gentle purrs and affectionate nuzzles make every moment special. Embark on a journey of endless cuddles and playful adventures with Slash Mau as your beloved new family member")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    PriceAdoptButton(price = "150$") {

                    }
                }
            }
        }
    }
}

@Composable
fun UserProfile() {
    // Profile information using AsyncImage
    Row(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4lQHH4lXf8gt-fEpRHuQPB4N8l5VgYHYezg&usqp=CAU", // Replace with URL or local drawable resource
            contentDescription = "Owner",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text("Earl Kim", fontWeight = FontWeight.Bold)
            Text("Owner")
        }
    }
}

@Preview
@Composable
fun PreviewCatProfileUI() {
    CatProfileUI()
}
