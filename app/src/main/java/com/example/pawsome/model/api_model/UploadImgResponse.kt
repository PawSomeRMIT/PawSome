package com.example.pawsome.model.api_model

import com.example.pawsome.model.api_model.UploadImageObject

data class UploadImgResponse(
    val message: String,
    val `object`: UploadImageObject
)