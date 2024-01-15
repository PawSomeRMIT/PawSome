package com.example.pawsome.presentation.homescreen

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawsome.data.repository.AuthRepo
import com.example.pawsome.data.repository.BackEndRepo
import com.example.pawsome.model.Booking
import com.example.pawsome.model.FilterChipData
import com.example.pawsome.model.PetDetail
import com.example.pawsome.model.User
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.maps.android.ktx.utils.sphericalDistance
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

//@HiltViewModel
class HomeScreenViewModel (
//    private val authRepo: AuthRepo,
//    private val backEndRepo: BackEndRepo,
//    private val client: ChatClient,
    val currentLocation: LatLng,
    val user: User
) : ViewModel(

) {
    var filterOptions = listOf(
        FilterChipData("\uD83C\uDF08", "All"),
        FilterChipData("\uD83D\uDC31", "Cat"),
        FilterChipData("\uD83D\uDC36️", "Dog"),
        FilterChipData("\uD83D\uDC2D", "Mouse"),
        FilterChipData("\uD83D\uDC37️", "Pig"),
        FilterChipData("\uD83D\uDC32️", "Dragon"),
    )

    private val _isLoading = Channel<Boolean>()
    val isLoading = _isLoading.receiveAsFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _filterType = MutableStateFlow("All")
    val filterType = _filterType.asStateFlow()

    var petsList = MutableStateFlow(getPetsFromFireStore())
    val matchedPets = petsList.asStateFlow()
        .combine(searchText) {pets, text ->
            if (text.isNotBlank()) {
                pets.filter {
                    it.petName.lowercase().contains(text.lowercase()) || it.petDescription.contains(text) || it.petBreed.contains(text)
                }
            }
            else {
                pets
            }
        }
        .combine(filterType) {pets, type ->
            Log.d("TEST", type)

//            if (type.contains("Cat")) {
//                pets.filter {
//                    it.petAnimal.contains("Cat")
//                }
//            }
//            else if (type.contains("Dog")) {
//                pets.filter {
//                    it.petAnimal.contains("Dog")
//                }
//            }
//            else {
//                pets


            if (type == "All") {
                pets
            }
            else {
                pets.filter {
                    it.petAnimal.contains(type)
                }
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

                        Log.d("HOMESCREEN", user.toString())

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
                                bookingPricePerDay = document.get("bookingPricePerDay").toString(),
                                ownerId = document.get("ownerId").toString(),
                                latitude = document.get("latitude").toString().toDouble(),
                                longitude = document.get("longitude").toString().toDouble(),
                                img = document.get("img").toString(),
                                distance = String.format("%.2f", distance).toDouble(),
                                petAddress = document.get("petAddress").toString()
                            )

                            if (pet.ownerId != user.userID && pet.petStatus == "Available") {
                                petList += pet
                            }

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
                    chatToken = it.get("chatToken").toString(),
                    history = it.get("history") as List<Booking>
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

    fun onFilterTypeChange(filterType: String) {
        Log.d("TEST", filterType)

        _filterType.value = filterType
    }
}