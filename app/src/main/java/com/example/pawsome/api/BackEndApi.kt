package com.example.pawsome.api

import com.example.pawsome.model.CreateChannelRequestBody
import com.example.pawsome.model.RequestBody
import com.example.pawsome.model.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BackEndApi {
    @POST("create_user")
    suspend fun create_user(
        @Body requestBody: RequestBody
    ): Response

    @POST("create_channel")
    suspend fun create_channel(
        @Body requestBody: CreateChannelRequestBody
    ): Response
}