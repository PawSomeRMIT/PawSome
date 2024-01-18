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

import com.example.pawsome.api.EKYCApi
import com.example.pawsome.model.api_model.CheckLivenessBody
import com.example.pawsome.model.api_model.CheckLivenessResponse
import com.example.pawsome.model.api_model.ExtractInfoBody
import com.example.pawsome.model.api_model.ExtractInfoResponse
import com.example.pawsome.model.api_model.UploadImgResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class EKYCApiRepo @Inject constructor(
    private val ekycApi: EKYCApi
) {
    suspend fun upload_image(file: File, title: String, description: String): UploadImgResponse {
        return ekycApi.upload_image(
            file = MultipartBody.Part.createFormData(
                "file",
                filename = file.name,
                file.asRequestBody()
            ),
            title = title,
            description = description
        )
    }

    suspend fun check_liveness(requestBody: CheckLivenessBody): CheckLivenessResponse {
        return ekycApi.check_liveness(
            requestBody = requestBody
        )
    }

    suspend fun extract_front_info(requestBody: ExtractInfoBody): ExtractInfoResponse {
        return ekycApi.extract_front_info(
            requestBody = requestBody
        )
    }
}