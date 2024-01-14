package com.example.pawsome.presentation.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawsome.model.Booking
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date

class HistoryViewModel : ViewModel() {
    private val _bookings = MutableLiveData<List<Booking>>()
    val bookings: LiveData<List<Booking>> get() = _bookings

    private val db = FirebaseFirestore.getInstance()

    fun fetchBookings() {
        viewModelScope.launch {
            try {
                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                val querySnapshot = db.collection("history")
                    .whereEqualTo("customerId", userId)
                    .get()
                    .await()

                val bookingList = mutableListOf<Booking>()

                for (document in querySnapshot.documents) {
                    val customerCardIdName = document.getString("customerCardIdName") ?: ""
                    val customerCardIdNumber = document.getString("customerCardIdNumber") ?: ""
                    val customerId = document.getString("customerId") ?: ""
                    val petId = document.getString("petId") ?: ""
                    val totalPrice = document.getDouble("totalPrice") ?: 0.0
                    val ownerId = document.getString("ownerId") ?: ""

                    val startDateTimestamp = document.getTimestamp("startDate")
                    val endDateTimestamp = document.getTimestamp("endDate")

                    val startDate = startDateTimestamp ?: Timestamp(Date())
                    val endDate = endDateTimestamp ?: Timestamp(Date())

                    val booking = Booking(
                        customerId = customerId,
                        petId = petId,
                        totalPrice = totalPrice,
                        customerCardIdNumber = customerCardIdNumber,
                        customerCardIdName = customerCardIdName
                    )

//                    val booking = Booking(
//                        customerCardIdName,
//                        customerCardIdNumber,
////                        customerId,
//                        petId,
////                        startDate,
////                        endDate,
//                        totalPrice.toDouble(),
////                        ownerId
//                    )
                    bookingList.add(booking)
                }

                _bookings.postValue(bookingList)
            } catch (e: Exception) {
                Log.e("HistoryViewModel", "Error getting history", e)
            }
        }
    }
}
