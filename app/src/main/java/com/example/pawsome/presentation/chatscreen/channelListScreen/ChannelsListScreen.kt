package com.example.pawsome.presentation.chatscreen.channelListScreen

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pawsome.domain.ChatScreen
import com.example.pawsome.model.User
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.models.Filters
import io.getstream.chat.android.models.InitializationState
import kotlinx.coroutines.launch

@Composable
fun ChannelsListScreen(
    navController: NavController,
    channelID: String?,
    channelsListViewModel: ChannelsListViewModel = hiltViewModel(),
) {
//    var gottenChannelID by remember {
//        mutableStateOf(channelID)
//    }

    val clientInitialisationState by channelsListViewModel.clientState.initializationState.collectAsState()

    val scope = rememberCoroutineScope()

    val state by channelsListViewModel.isLoading.collectAsState(initial = false)

//    LaunchedEffect(key1 = gottenChannelID) {
//        gottenChannelID?.let { Log.d("CHECK", it) }
//
//        if (gottenChannelID != "") {
//            navController.previousBackStackEntry?.savedStateHandle?.remove<String>("channelId")
//            navController.currentBackStackEntry?.savedStateHandle?.set("channelId", channelID)
//
//            navController.navigate(ChatScreen.Channel.route)
//
//            gottenChannelID = ""
//        }
//    }

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