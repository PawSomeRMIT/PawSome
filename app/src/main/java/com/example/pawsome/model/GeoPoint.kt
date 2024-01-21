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

package com.example.pawsome.model

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.google.firebase.firestore.GeoPoint
import java.io.IOException

fun getLocationFromAddress(strAddress: String?, context: Context): GeoPoint? {
    val coder = Geocoder(context)
    val address: List<Address>?
    var p1: GeoPoint?
    try {
        address = coder.getFromLocationName(strAddress!!, 5)
        Log.d("ADDRESS", "${address?.size ?: 0}")

        if (address == null) return null
        if (address.isEmpty()) return null

        address.let {
            val location: Address = address[0]
            p1 = GeoPoint(
                location.latitude,
                location.longitude
            )
            Log.d("Latlong", "${p1!!.latitude} ${p1!!.longitude}")
            return p1
        }

        return null
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}