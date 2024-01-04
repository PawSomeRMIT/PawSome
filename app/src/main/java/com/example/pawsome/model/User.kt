/*
    RMIT University Vietnam
    Course: COSC2657 Android Development
    Semester: 2023C
    Assessment: Assignment 2
    Author: Lai Nghiep Tri
    ID: s3799602
    Created  date: 19/12/2023
    Last modified: 20/12/2023
    Acknowledgement: Figma UI, Android Developer documentation, Firebase Documentation, etc
 */

package com.example.pawsome.model

import android.os.Parcelable

data class User (
    var userID: String = "",
    val username: String = "",
    val email: String = "",
    val image: String = "",
    val membership: String = "normal",
    val chatToken: String = ""
)