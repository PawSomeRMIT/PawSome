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

package com.example.pawsome.presentation.detailscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pawsome.data.repository.BackEndRepo
import com.example.pawsome.model.api_model.CreateChannelRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val backEndRepo: BackEndRepo
) : ViewModel() {
    private val _isLoading = Channel<Boolean>()
    val isLoading = _isLoading.receiveAsFlow()

    suspend fun createChannel(userID1: String, userID2: String): String {
        _isLoading.send(true)

        val requestBody = CreateChannelRequestBody(userId1 = userID1, userId2 = userID2)

        val result = backEndRepo.create_channel(requestBody)

        Log.d("Create Channel", result.toString())

        _isLoading.send(false)

        return result.response
    }
}