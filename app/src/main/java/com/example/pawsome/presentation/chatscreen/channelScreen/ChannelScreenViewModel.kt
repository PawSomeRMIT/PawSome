package com.example.pawsome.presentation.chatscreen.channelScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import javax.inject.Inject

@HiltViewModel
class ChannelScreenViewModel @Inject constructor(
    private val client: ChatClient
) : ViewModel() {
    // Observe the client connection state
    val clientState = client.clientState
}