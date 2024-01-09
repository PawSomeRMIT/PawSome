package com.example.pawsome.model.api_model

data class UploadImageObject(
    val description: String,
    val fileName: String,
    val fileType: String,
    val hash: String,
    val storageType: String,
    val title: String,
    val tokenId: String,
    val uploadedDate: String
)