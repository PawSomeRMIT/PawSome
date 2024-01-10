package com.example.pawsome.presentation.detailscreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawsome.data.repository.BackEndRepo
import com.example.pawsome.model.User
import com.example.pawsome.model.api_model.CreateChannelRequestBody
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val client: ChatClient,
    private val backEndRepo: BackEndRepo
) : ViewModel() {

    private val _isLoading = Channel<Boolean>()
    val isLoading = _isLoading.receiveAsFlow()
}