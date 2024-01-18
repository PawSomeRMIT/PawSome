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

package com.example.pawsome.data.repository

import com.example.pawsome.api.BackEndApi
import com.example.pawsome.model.api_model.CreateChannelRequestBody
import com.example.pawsome.model.api_model.RequestBody
import com.example.pawsome.model.api_model.Response
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
}