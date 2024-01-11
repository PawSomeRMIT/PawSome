package com.example.pawsome.api

import com.example.pawsome.model.api_model.CheckLivenessBody
import com.example.pawsome.model.api_model.CheckLivenessResponse
import com.example.pawsome.model.api_model.ExtractInfoBody
import com.example.pawsome.model.api_model.ExtractInfoResponse
import com.example.pawsome.model.api_model.UploadImgResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface EKYCApi {
    @Multipart
    @Headers(
        "Authorization: " + ApiConstants.EKYC_ACCESS_TOKEN,
        "Token-id: " + ApiConstants.EKYC_TOKEN_ID,
        "Token-key: " + ApiConstants.EKYC_TOKEN_KEY,
    )
    @POST("file-service/v1/addFile")
    suspend fun upload_image(
        @Part file: MultipartBody.Part,
        @Part("title") title: String,
        @Part("description") description: String
    ): UploadImgResponse

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

    @Headers(
        "Content-Type: application/json",
        "Authorization: " + ApiConstants.EKYC_ACCESS_TOKEN,
        "Token-id: " + ApiConstants.EKYC_TOKEN_ID,
        "Token-key: " + ApiConstants.EKYC_TOKEN_KEY,
        "mac-address: TEST1"
    )
    @POST("ai/v1/ocr/id/front")
    suspend fun extract_front_info(
        @Body requestBody: ExtractInfoBody
    ): ExtractInfoResponse

}