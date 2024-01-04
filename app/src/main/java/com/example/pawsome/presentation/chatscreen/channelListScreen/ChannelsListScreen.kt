package com.example.pawsome.presentation.chatscreen.channelListScreen

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
//    user: User?,
    channelsListViewModel: ChannelsListViewModel = hiltViewModel(),
) {
    val clientInitialisationState by channelsListViewModel.clientState.initializationState.collectAsState()

    val scope = rememberCoroutineScope()

    val state by channelsListViewModel.isLoading.collectAsState(initial = false)

    ChatTheme {
        when (clientInitialisationState) {
            InitializationState.COMPLETE -> {
                ChannelsScreen(
                    title = "Channel List",
                    isShowingSearch = true,
                    onItemClick = {channel ->
                        navController.currentBackStackEntry?.savedStateHandle?.set("channelId", channel.cid)

                        navController.navigate(ChatScreen.Channel.route)
                    },
                    onBackPressed = {
//                        navController.popBackStack()
                    },
                    onHeaderActionClick = {
                        scope.launch {
                            Log.d("Test chat", "On clicked")

                            channelsListViewModel.createChannel()
                        }
                    }
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