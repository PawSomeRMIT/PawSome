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


package com.example.pawsome.data.form

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pawsome.model.PetDetail
import com.example.pawsome.model.User
import com.example.pawsome.model.getLocationFromAddress
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class DataViewModel: ViewModel() {
    // Temp fetching data
    private val tempFetchData = mutableStateOf(listOf<PetDetail>())

    // List for storing fetch data
    val petDetailList: State<List<PetDetail>> = tempFetchData

    init {
        getAllPets { dataList ->
            tempFetchData.value = dataList
            Log.d("Firestore", "GET FORM DATA SUCCESS: ${petDetailList.value.size}")
        }
    }

    private val tag = DataViewModel::class.simpleName

    var formUIState = mutableStateOf(FormUIState())
    var allFormValidatePassed = mutableStateOf(false)

    suspend fun onEvent(e: FormUIEvent) {
        when (e) {
            is FormUIEvent.PetNameChanged -> {
                formUIState.value = formUIState.value.copy(
                    petName = e.petName
                )
                printState()
            }

            is FormUIEvent.PetAgeChanged -> {
                formUIState.value = formUIState.value.copy(
                    petAge = e.petAge
                )
                printState()
            }

            is FormUIEvent.PetBreedChanged -> {
                formUIState.value = formUIState.value.copy(
                    petBreed = e.petBreed
                )
                printState()
            }

            is FormUIEvent.PetGenderChanged -> {
                formUIState.value = formUIState.value.copy(
                    petGender = e.petGender
                )
                printState()
            }

            is FormUIEvent.PetLocationChanged -> {
                formUIState.value = formUIState.value.copy(
                    petlocation = e.petLocation
                )
                printState()
            }

            is FormUIEvent.PetColorChanged -> {
                formUIState.value = formUIState.value.copy(
                    petColor = e.petColor
                )
                printState()
            }

            is FormUIEvent.PetTypeChanged -> {
                formUIState.value = formUIState.value.copy(
                    petType = e.petType
                )
                printState()
            }

            is FormUIEvent.PetDescriptionChanged -> {
                formUIState.value = formUIState.value.copy(
                    petDescription = e.petDescription
                )
                printState()
            }

            is FormUIEvent.BookingPriceChanged -> {
                formUIState.value = formUIState.value.copy(
                    bookingPrice = e.bookingPrice
                )
                printState()
            }

//            is FormUIEvent.SaveButtonClicked -> {
//                saveForm()
//            }

            else -> {}
        }

        vailidateForm()
    }


    private fun printState() {
        Log.d(tag, formUIState.value.toString())
    }

    suspend fun saveForm(petDetail: PetDetail, context: Context) {
        // Create a storage reference
        val storage = FirebaseStorage.getInstance().reference.child("pets/${UUID.randomUUID()}.jpg")

        // Create a FireStore reference
        val db = FirebaseFirestore.getInstance()

        val addressLatLong = getLocationFromAddress(petDetail.petAddress, context)

        petDetail.latitude = addressLatLong?.latitude ?: 10.729250
        petDetail.longitude = addressLatLong?.longitude ?: 106.695520

        Log.d("LatLong", "${petDetail.latitude} ${petDetail.longitude}")

        petDetail.img?.let {
            val uploadURI = uploadToStorage(it, context= context)

            petDetail.img = uploadURI

            storage.putFile(it)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        storage.downloadUrl.addOnSuccessListener { uri ->
                            db.collection("pets")
                                .add(petDetail)
                                .addOnSuccessListener {
                                    Log.d("Firestore", "Inside_OnSuccessListener")
                                    Log.d("Firestore", "Save form successfully")
                                }
                                .addOnFailureListener{
                                    Log.d("Firestore", "Inside_OnFailureListener")
                                    Log.d("Firestore", "Exception= ${it.message}")
                                    Log.d("Firestore", "Exception= ${it.localizedMessage}")
                                }
                        }
                    } else {
                        Log.d("Storage", "Upload failed: ${task.exception?.message}")
                    }
                }
        }
    }


    fun getUserDetails(): User? {
        val auth = FirebaseAuth.getInstance()
        val userID = auth.currentUser?.uid

        val db = Firebase.firestore

        val tag = "Firestore"
        var userData: User? = null

        if (userID != null) {
            val userDocument = db.collection("user").document(userID)
            userDocument
                .get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        userData = it.toObject(User::class.java)
                    } else
                        userData = null
                }
                .addOnFailureListener {
                    Log.e(tag, "Failed to retrieve user.")
                }

            return userData
        }

        return null
    }


//    fun updateUserDetails() {
//        val auth = FirebaseAuth.getInstance()
//        val userID = auth.currentUser?.uid
//
//        val db = Firebase.firestore
//
//        val tag = "Firestore"
//
//        if (userID != null) {
//            val userDocument = db.collection("user").document(userID)
//            userDocument
//                .get()
//                .addOnSuccessListener {
//                    if (it.exists()) {
//
//                    } else {
//                        Log.e(tag, "No user found")
//                    }
//                }
//                .addOnFailureListener {
//                    Log.e(tag, "Failed to retrieve user $userID")
//                }
//        }
//    }


    fun getAllPets(
        onResult: (List<PetDetail>) -> Unit
    ) {
        // Create a FireStore reference
        val db = FirebaseFirestore.getInstance()
        val tag = "Firestore"

        db.collection("forms").get()
            .addOnSuccessListener {snapshot ->
                val petList = mutableListOf<PetDetail>()

                for (document in snapshot) {
                    val pet = document.toObject(PetDetail::class.java)
                    petList.add(pet)
                }

                onResult(petList)
                Log.e(tag, "Get all event data success")
            }
            .addOnFailureListener {e ->
                onResult(emptyList())
                Log.e(tag, "Failed to get event data: ${e.message}")
            }
    }

    suspend fun uploadToStorage(uri: Uri, context: Context): Uri? {
        val storage = Firebase.storage

        // Create a storage reference from app
        var storageRef = storage.reference

        val unique_image_name = "${UUID.randomUUID()}"
        var spaceRef: StorageReference
        spaceRef = storageRef.child("$unique_image_name.jpeg")

        val byteArray: ByteArray? = context.contentResolver
            .openInputStream(uri)
            ?.use { it.readBytes() }

        var uploadedURI: Uri? = Uri.EMPTY

        byteArray?.let{
            var uploadTask = spaceRef.putBytes(byteArray)
            uploadTask
                .addOnFailureListener {
                    Toast.makeText(
                        context,
                        "upload failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(
                        context,
                        "upload successed",
                        Toast.LENGTH_SHORT
                    ).show()
                }.await()
        }

        spaceRef.downloadUrl
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Failed to get url",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnSuccessListener {
                uploadedURI = it
                println("1: " + uploadedURI.toString())
            }
            .await()
        println("23: " + uploadedURI.toString())
        return uploadedURI
    }

    private fun vailidateForm() {

        val petNameResult = FormValidator.validatePetName(
            petName = formUIState.value.petName
        )


        val petAgeResult = FormValidator.validatePetAge(
            petAge = formUIState.value.petAge
        )


        val petBreedResult = FormValidator.validatePetBreed(
            petBreed = formUIState.value.petBreed
        )

        val petGenderResult = FormValidator.validatePetGender(
            petGender = formUIState.value.petGender
        )


        val petColorResult = FormValidator.validatePetColor(
            petColor = formUIState.value.petColor
        )


        val petTypeResult = FormValidator.validatePetType(
            petType = formUIState.value.petType
        )


        val petLocationResult = FormValidator.validatePetLocation(
            petLocation = formUIState.value.petlocation
        )


        val petDescriptionResult = FormValidator.validatePetDescription(
            petDescription = formUIState.value.petDescription
        )


        val bookingPriceResult = FormValidator.validateBookingPrice(
            bookingPrice = formUIState.value.bookingPrice
        )

        Log.d(tag, "Validate form result")
        Log.d(tag, "petNameResult= $petNameResult")
        Log.d(tag, "petAgeResult= $petAgeResult")
        Log.d(tag, "petBreedResult= $petBreedResult")
        Log.d(tag, "petGenderResult= $petGenderResult")
        Log.d(tag, "petColorResult= $petColorResult")
        Log.d(tag, "petTypeResult= $petTypeResult")
        Log.d(tag, "petLocationResult= $petLocationResult")
        Log.d(tag, "petDescriptionResult= $petDescriptionResult")
        Log.d(tag, "bookingPriceResult= $bookingPriceResult")

        formUIState.value = formUIState.value.copy(
            petNameError = petNameResult.status,
            petAgeError = petAgeResult.status,
            petBreedError = petBreedResult.status,
            petGenderError = petGenderResult.status,
            petColorError = petColorResult.status,
            petTypeError = petTypeResult.status,
            petlocationError = petLocationResult.status,
            petDescriptionError = petDescriptionResult.status,
            bookingPriceError = bookingPriceResult.status,
        )

        allFormValidatePassed.value =
            petNameResult.status && petAgeResult.status &&
            petBreedResult.status && petColorResult.status &&
            petLocationResult.status && petDescriptionResult.status &&
            petGenderResult.status && petTypeResult.status &&
            bookingPriceResult.status
    }
}