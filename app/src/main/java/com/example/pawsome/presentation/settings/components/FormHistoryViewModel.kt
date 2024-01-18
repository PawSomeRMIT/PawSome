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

package com.example.pawsome.presentation.settings.components

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawsome.model.PetDetail
import com.example.pawsome.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FormHistoryViewModel(
    val user: User
): ViewModel() {
    private val _isLoading = Channel<Boolean>()
    val isLoading = _isLoading.receiveAsFlow()

    var petsList = MutableStateFlow(getFormsFromFireStore()).asStateFlow()

    fun getFormsFromFireStore(): List<PetDetail> {
        val db = FirebaseFirestore.getInstance()
        val petList = mutableListOf<PetDetail>()

        try {
            viewModelScope.launch {
                db.collection("pets").get().addOnSuccessListener { documents ->
                    viewModelScope.launch{
                        _isLoading.send(true)

                        Log.d("CURRENT_USER", user.toString())

                        for (document in documents) {
                            val pet = PetDetail(
                                id = document.id,
                                petName = document.get("petName").toString(),
                                petAge = document.get("petName").toString(),
                                petBreed = document.get("petBreed").toString(),
                                petAnimal = document.get("petAnimal").toString(),
                                petColor = document.get("petColor").toString(),
                                petDescription = document.get("petDescription").toString(),
                                petGender = document.get("petGender").toString(),
                                petStatus = document.get("petStatus").toString(),
                                bookingPricePerDay = document.get("bookingPricePerDay").toString(),
                                ownerId = document.get("ownerId").toString(),
                                latitude = document.get("latitude").toString().toDouble(),
                                longitude = document.get("longitude").toString().toDouble(),
                                img = document.get("img").toString(),
                                distance = 0.0,
                                petAddress = document.get("petAddress").toString()
                            )

                            if (pet.ownerId == user.userID) petList.add(pet)

                            _isLoading.send(false)
                        }
                    }
                }
                    .await()
            }
        }
        catch (e: FirebaseFirestoreException) {
            Log.d("error", "getDataFromFireStore: $e")
        }

        Log.d("PET_LIST", petList.toString())

        return petList
    }
}