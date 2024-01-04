package com.example.pawsome.data.repository


import com.example.pawsome.api.BackEndApi
import com.example.pawsome.model.CreateChannelRequestBody
import com.example.pawsome.model.RequestBody
import com.example.pawsome.model.Response
import javax.inject.Inject

class BackEndRepo @Inject constructor(
    private val backEndApi: BackEndApi
) {
    suspend fun create_user(requestBody: RequestBody): Response {
        return backEndApi.create_user(requestBody)
    }

    suspend fun create_channel(requestBody: CreateChannelRequestBody): Response {
        return backEndApi.create_channel(requestBody)
    }

}