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
        if (address == null) {
            return null
        }
        val location: Address = address[0]
        location.getLatitude()
        location.getLongitude()
        p1 = GeoPoint(
            location.getLatitude(),
            location.getLongitude()
        )
        Log.d("Latlong", "${p1.latitude} ${p1.longitude}")
        return p1
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}