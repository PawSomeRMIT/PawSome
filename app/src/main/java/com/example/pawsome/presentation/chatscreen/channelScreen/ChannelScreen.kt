package com.example.pawsome.presentation.chatscreen.channelScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.getstream.chat.android.compose.ui.messages.MessagesScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory

@Composable
fun ChannelScreen(
    navController: NavController,
    channelId: String,
    viewModel: ChannelScreenViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val factory by lazy {
        MessagesViewModelFactory(
            context = context,
            channelId = channelId
        )
    }

    ChatTheme {
        MessagesScreen(
            viewModelFactory = MessagesViewModelFactory(
                context = context,
                channelId = channelId,
                messageLimit = 30
            ),
            onBackPressed = {
                navController.popBackStack()
            },
        )
    }
}