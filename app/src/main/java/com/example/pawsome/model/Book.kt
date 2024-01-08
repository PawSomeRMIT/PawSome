package com.example.pawsome.model

import java.sql.Timestamp

data class Book(
    val user: User,
    val petDetail: PetDetail,
    val startDate: Timestamp,
    val endDate: Timestamp
)