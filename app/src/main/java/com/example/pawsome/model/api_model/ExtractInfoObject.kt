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

package com.example.pawsome.model.api_model

data class ExtractInfoObject(
    val birth_day: String,
    val birth_day_label: String,
    val birth_day_prob: Double,
    val card_type: String,
    val citizen_id: String,
    val citizen_id_prob: Double,
    val expire_warning: String,
    val gender: String,
    val gender_prob: Int,
    val id: String,
    val id_fake_prob: Double,
    val id_fake_warning: String,
    val id_probs: String,
    val issue_date: String,
    val issue_date_prob: Double,
    val issue_place: String,
    val issue_place_prob: Double,
    val msg: String,
    val name: String,
    val name_label: String,
    val name_prob: Double,
    val nation_policy: String,
    val nation_slogan: String,
    val nationality: String,
    val nationality_prob: Double,
    val origin_location: String,
    val origin_location_label: String,
    val origin_location_prob: Double,
    val post_code: List<PostCode>,
    val recent_location: String,
    val recent_location_label: String,
    val recent_location_prob: Double,
    val tampering: Tampering,
    val type_id: Int,
    val valid_date: String,
    val valid_date_prob: Double
)