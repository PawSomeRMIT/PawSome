package com.example.pawsome.presentation.homescreen

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawsome.data.repository.AuthRepo
import com.example.pawsome.data.repository.BackEndRepo
import com.example.pawsome.model.PetDetail
import com.example.pawsome.model.User
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.appcheck.internal.util.Logger
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.maps.android.ktx.utils.sphericalDistance
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.getstream.chat.android.client.ChatClient
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

//@HiltViewModel
class HomeScreenViewModel (
//    private val authRepo: AuthRepo,
//    private val backEndRepo: BackEndRepo,
//    private val client: ChatClient,
    val currentLocation: LatLng
) : ViewModel(

) {
    private val _isLoading = Channel<Boolean>()
    val isLoading = _isLoading.receiveAsFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    var petsList = MutableStateFlow(getPetsFromFireStore())
    val matchedPets = searchText
        .combine(petsList) {text, pets ->
            if (text.isNotBlank()) {
                pets.filter {
                    it.petName.contains(text) || it.petDescription.contains(text) || it.petBreed.contains(text)
                }
            }
            else {
                pets
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            petsList.value
        )

    fun getPetsFromFireStore(): List<PetDetail> {
        val db = FirebaseFirestore.getInstance()
        val petList = mutableListOf<PetDetail>()

        try {
            viewModelScope.launch {
                db.collection("pets").get().addOnSuccessListener { documents ->
                    viewModelScope.launch{
                        _isLoading.send(true)

                        Log.d("HOMESCREEN", currentLocation.toString())

                        for (document in documents) {
                            // Calculate distance in meter and convert to km
                            val distance = currentLocation.sphericalDistance(
                                LatLng(
                                    document.get("latitude").toString().toDouble(),
                                    document.get("longitude").toString().toDouble()
                                )
                            ) / 1000

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
                                bookingPricePerDay = document.get("bookingPricePerDay").toString().toDouble(),
                                ownerId = document.get("ownerId").toString(),
                                latitude = document.get("latitude").toString().toDouble(),
                                longitude = document.get("longitude").toString().toDouble(),
                                img = document.get("img").toString(),
                                distance = String.format("%.2f", distance).toDouble()
                            )

                            petList += pet

                            _isLoading.send(false)
                        }

                        petList.sortBy { it.distance }
                    }
                }
                    .await()
            }
        }
        catch (e: FirebaseFirestoreException) {
            Log.d("error", "getDataFromFireStore: $e")
        }

        Log.d("HOME", petList.toString())

        return petList
    }

    suspend fun getUserFromFireStore(uId: String) : User {
        val db = FirebaseFirestore.getInstance()
        var user = User()

        try {
            db.collection("user").whereEqualTo("userID", uId).get().await().map {
                val result = User(
                    userID = it.get("userID").toString(),
                    username = it.get("username").toString(),
                    email = it.get("email").toString(),
                    image = it.get("image").toString(),
                    membership = it.get("membership").toString(),
                    chatToken = it.get("chatToken").toString()
                )

                user = result
            }
        }
        catch (e: FirebaseFirestoreException) {
            Log.d("error", "getDataFromFireStore: $e")
        }

        Log.d("Fetched", user.toString())

        return user
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}