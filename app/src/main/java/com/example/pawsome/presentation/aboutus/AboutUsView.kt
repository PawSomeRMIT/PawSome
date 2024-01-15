//@file:OptIn(ExperimentalMaterial3Api::class)
//
//package com.example.pawsome.presentation.aboutus
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.Card
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.input.nestedscroll.nestedScroll
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//


//@Composable
//fun AboutUsView(viewModel: AboutUsName, navController: NavController = rememberNavController()) {
//    Surface(
//        modifier = Modifier
//            .fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {
//        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
//        Scaffold(
//            modifier = Modifier
//                .fillMaxSize()
//                .nestedScroll(scrollBehavior.nestedScrollConnection),
//            topBar = {
//                CenterAlignedTopAppBar(
//                    title = {
//                        Text(
//                            text = "About Us"
//                        )
//                    },
//                    navigationIcon = {
//                        IconButton(onClick = { navController.popBackStack() }) {
//                            Icon(
//                                imageVector = Icons.Default.ArrowBack,
//                                contentDescription = "Go back"
//                            )
//                        }
//                    },
//                    scrollBehavior = scrollBehavior
//                )
//            }
//        ) { values ->
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(values)
//            ) {
//                items(AboutUsName().members.size) { index ->
//                    val member = AboutUsName().members[index]
//
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                        ){
//                            Image(
//                                painter = painterResource(id = member.memberImageResId),
//                                contentDescription = null,
//                                contentScale = ContentScale.Crop,
//                                modifier = Modifier
//                                    .size(125.dp)
//                                    .clip(CircleShape)
//                            )
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(8.dp)
//                            ) {
//                                Text(
//                                    text = member.memberName,
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 18.sp,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(8.dp)
//                                )
//                                Text(
//                                    text = "Student ID: ${member.studentID}",
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(8.dp)
//                                )
//                                Text(
//                                    text = "Major: ${member.memberMajor}",
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(8.dp)
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
