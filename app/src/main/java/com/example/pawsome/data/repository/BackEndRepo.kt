package com.example.pawsome.data.repository


import com.example.pawsome.api.BackEndApi
import com.example.pawsome.model.Response
import com.example.pawsome.model.api_model.CheckLivenessBody
import com.example.pawsome.model.api_model.CheckLivenessResponse
import com.example.pawsome.model.api_model.CreateChannelRequestBody
import com.example.pawsome.model.api_model.RequestBody
import com.example.pawsome.model.api_model.StripeResponse
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

    suspend fun call_payment_sheet(price: Double): StripeResponse {
        return backEndApi.call_payment_sheet(price = price.toString())
    }

    suspend fun check_liveness(requestBody: CheckLivenessBody): CheckLivenessResponse {
        return backEndApi.check_liveness(
            requestBody = requestBody
        )
    }
}