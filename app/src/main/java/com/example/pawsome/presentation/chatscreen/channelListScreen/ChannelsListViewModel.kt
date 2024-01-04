package com.example.pawsome.presentation.chatscreen.channelListScreen

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawsome.data.repository.BackEndRepo
import com.example.pawsome.model.CreateChannelRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelsListViewModel @Inject constructor(
    private val client: ChatClient,
    private val backEndRepo: BackEndRepo
) : ViewModel() {
    // Observe the client connection state
    val clientState = client.clientState

    private val _isLoading = Channel<Boolean>()
    val isLoading = _isLoading.receiveAsFlow()

    suspend fun createChannel() {
        _isLoading.send(true)

        val requestBody = CreateChannelRequestBody(userId1 = "trithuc3", userId2 = "trithuc4")

        val result = backEndRepo.create_channel(requestBody)

        Log.d("Create Channel", result.response)

        _isLoading.send(false)
    }
}