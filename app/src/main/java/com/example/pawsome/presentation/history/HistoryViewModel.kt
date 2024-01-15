package com.example.pawsome.presentation.history

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawsome.model.Booking
import com.example.pawsome.model.PetDetail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(

) : ViewModel() {
    private val _bookings = MutableLiveData<List<Booking>>()
    val bookings: LiveData<List<Booking>> get() = _bookings

    private val _petDetail = MutableLiveData<PetDetail>()
    val petDetail: LiveData<PetDetail> get() = _petDetail

    private val db = FirebaseFirestore.getInstance()

    fun fetchBookings() {
        viewModelScope.launch {
            try {
                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

                Log.d("HISTORY", userId)

                val querySnapshot = db.collection("user")
                    .document(userId)
                    .get()
                    .await()

                val bookingList = mutableListOf<Booking>()

                val data = querySnapshot.data

                data?.let {
                    val bookings = data["history"] as List<*>

                    for (item in bookings) {
                        val bookingMap = item as Map<*, *>

                        val customerCardIdName = bookingMap["customerCardIdName"].toString()
                        val customerCardIdNumber = bookingMap["customerCardIdNumber"].toString()
                        val customerId = bookingMap["customerId"].toString()
                        val petId = bookingMap["petId"].toString()
                        val totalPrice = bookingMap["totalPrice"].toString().toDouble()

                        val petSnapshot = db.collection("pets")
                            .document(petId)
                            .get()
                            .await()

                        val pet = PetDetail(
                            id = petSnapshot.id,
                            petName = petSnapshot.get("petName").toString(),
                            petAge = petSnapshot.get("petName").toString(),
                            petBreed = petSnapshot.get("petBreed").toString(),
                            petAnimal = petSnapshot.get("petAnimal").toString(),
                            petColor = petSnapshot.get("petColor").toString(),
                            petDescription = petSnapshot.get("petDescription").toString(),
                            petGender = petSnapshot.get("petGender").toString(),
                            petStatus = petSnapshot.get("petStatus").toString(),
                            bookingPricePerDay = petSnapshot.get("bookingPricePerDay").toString(),
                            ownerId = petSnapshot.get("ownerId").toString(),
                            latitude = petSnapshot.get("latitude").toString().toDouble(),
                            longitude = petSnapshot.get("longitude").toString().toDouble(),
                            img = petSnapshot.get("img").toString(),
                            distance = 0.0,
                            petAddress = petSnapshot.get("petAddress").toString()
                        )

                        val booking = Booking(
                            customerId = customerId,
                            petId = petId,
                            totalPrice = totalPrice,
                            customerCardIdNumber = customerCardIdNumber,
                            customerCardIdName = customerCardIdName,
                            petDetail = pet
                        )

                        Log.d("HISTORY", booking.toString())

                        bookingList.add(booking)
                    }
                }
                _bookings.postValue(bookingList)
            } catch (e: Exception) {
                Log.e("HistoryViewModel", "Error getting history", e)
            }
        }
    }
}
