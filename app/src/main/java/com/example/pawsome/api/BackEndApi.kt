package com.example.pawsome.api

import com.example.pawsome.model.api_model.CreateChannelRequestBody
import com.example.pawsome.model.api_model.RequestBody
import com.example.pawsome.model.Response
import com.example.pawsome.model.api_model.CheckLivenessBody
import com.example.pawsome.model.api_model.CheckLivenessResponse
import com.example.pawsome.model.api_model.StripeResponse
import retrofit2.http.Body
import retrofit2.http.Headers
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

    @Headers(
        "Content-Type: application/json"
    )
    @POST("payment-sheet")
    suspend fun call_payment_sheet(
        @Body price: String
    ): StripeResponse

    @Headers(
        "Content-Type: application/json",
        "Authorization: " + ApiConstants.EKYC_ACCESS_TOKEN,
        "Token-id: " + ApiConstants.EKYC_TOKEN_ID,
        "Token-key: " + ApiConstants.EKYC_TOKEN_KEY,
        "mac-address: TEST1"
    )
    @POST("ai/v1/card/liveness")
    suspend fun check_liveness(
        @Body requestBody: CheckLivenessBody
    ): CheckLivenessResponse
}