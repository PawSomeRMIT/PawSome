package com.example.pawsome.model.api_model

data class CheckLivenessObject(
    val face_swapping: Boolean,
    val face_swapping_prob: Double,
    val fake_liveness: Boolean,
    val fake_liveness_prob: Double,
    val fake_print_photo: Boolean,
    val fake_print_photo_prob: Double,
    val liveness: String,
    val liveness_msg: String
)