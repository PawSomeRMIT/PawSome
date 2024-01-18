/*
    RMIT University Vietnam
    Course: COSC2657 Android Development
    Semester: 2023C
    Assessment: Assignment 3
    Author:
        Thieu Tran Tri Thuc - s3870730
        Lai Nghiep Tri - s3799602
        Bui Minh Nhat - s3878174
        Phan Bao Kim Ngan - s3914582
    Created  date: 1/1/2024
    Last modified: 19/1/2024
    Acknowledgement: Figma UI, Android Developer documentation, Firebase Documentation, etc
 */

package com.example.pawsome.presentation.chatscreen.channelListScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pawsome.domain.screens.ChatScreen
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.models.InitializationState

@Composable
fun ChannelsListScreen(
    navController: NavController,
    channelID: String?,
    channelsListViewModel: ChannelsListViewModel = hiltViewModel(),
) {

    val clientInitialisationState by channelsListViewModel.clientState.initializationState.collectAsState()

    if (channelID != null) {
        navController.previousBackStackEntry?.savedStateHandle?.remove<String>("channelId")
        navController.currentBackStackEntry?.savedStateHandle?.set("channelId", channelID)

        navController.navigate(ChatScreen.Channel.route)
    }

    ChatTheme {
        when (clientInitialisationState) {
            InitializationState.COMPLETE -> {
                ChannelsScreen(
                    title = "Channel List",
                    isShowingSearch = true,
                    isShowingHeader = false,
                    onItemClick = {channel ->
                        navController.currentBackStackEntry?.savedStateHandle?.set("channelId", channel.cid)

                        navController.navigate(ChatScreen.Channel.route)
                    },

                )
            }

            InitializationState.INITIALIZING -> {
                Text(text = "Initializing...")
            }

            InitializationState.NOT_INITIALIZED -> {
                Text(text = "Not Initialized...")
            }
        }
    }


}