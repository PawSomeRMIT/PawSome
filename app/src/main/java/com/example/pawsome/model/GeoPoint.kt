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
    var p1: GeoPoint? = null
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