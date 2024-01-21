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

package com.example.pawsome.data.form

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.pawsome.R
import com.example.pawsome.domain.screens.BottomBarScreen
import com.example.pawsome.model.PetDetail
import com.example.pawsome.model.getLocationFromAddress
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class DataViewModel : ViewModel() {
    // Temp fetching data
    private val tempFetchData = mutableStateOf(listOf<PetDetail>())

    // List for storing fetch data
    val petDetailList: State<List<PetDetail>> = tempFetchData

    // Check loading state
    private val _isLoading = Channel<Boolean>()
    val isLoading = _isLoading.receiveAsFlow()

    init {
        getAllPets { dataList ->
            tempFetchData.value = dataList
            Log.d("Firestore", "GET FORM DATA SUCCESS: ${petDetailList.value.size}")
        }
    }

    private val tag = DataViewModel::class.simpleName

    var formUIState = mutableStateOf(FormUIState())
    var allFormValidatePassed = mutableStateOf(false)

    fun onEvent(e: FormUIEvent) {
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
        }

        vailidateForm()
    }


    private fun printState() {
        Log.d(tag, formUIState.value.toString())
    }

    suspend fun saveForm(
        petDetail: PetDetail,
        context: Context,
        navHostController: NavHostController
    ) {
        // Create a storage reference
        val storage = FirebaseStorage.getInstance().reference.child("pets/${UUID.randomUUID()}.jpg")

        // Create a FireStore reference
        val db = FirebaseFirestore.getInstance()

        // Create document reference
        val documentRef = db.collection("pets").document()

        // Get document ID and store it manually along with object
        val documentID = documentRef.id
        Log.d("Firestore", "Document ID: ${documentID}")
        petDetail.id = documentID

        // Convert address to latitude and longitude
        // Default location (RMIT University Vietnam)
        val addressLatLong = getLocationFromAddress(petDetail.petAddress, context)

        if (addressLatLong != null) {
            petDetail.latitude = addressLatLong?.latitude ?: 10.729250
            petDetail.longitude = addressLatLong?.longitude ?: 106.695520
            Log.d("LatLong", "${petDetail.latitude} ${petDetail.longitude}")

            petDetail.img?.let {

                // Store object image to Storage and get its public url
                val uploadURI = uploadToStorage(Uri.parse(it), context = context)
                petDetail.img = uploadURI.toString()

                storage.putFile(Uri.parse(it))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            storage.downloadUrl.addOnSuccessListener { uri ->
                                db.collection("pets")
                                    .add(petDetail)
                                    .addOnSuccessListener {

                                        // Back to home screen
                                        navHostController.navigate(BottomBarScreen.Home.route) {
                                            popUpTo(BottomBarScreen.Home.route) {
                                                inclusive = true
                                            }
                                        }

                                        Log.d("Firestore", "Inside_OnSuccessListener")
                                        Log.d(
                                            "Firestore",
                                            "Form ${petDetail.id} is saved successfully"
                                        )
                                    }
                                    .addOnFailureListener {
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

        } else {
            Toast.makeText(context, "Invalid address. Try again!", Toast.LENGTH_SHORT).show()
        }
    }

    fun updatePetDetails(
        formID: String?,
        petDetail: PetDetail,
        context: Context,
        navHostController: NavHostController
    ) {
        val tag = "Firestore"
        val db = Firebase.firestore

        // Convert address to latitude and longitude
        // Default location (RMIT University Vietnam)
        val addressLatLong = getLocationFromAddress(petDetail.petAddress, context)

        if (addressLatLong != null) {
            petDetail.latitude = addressLatLong?.latitude ?: 10.729250
            petDetail.longitude = addressLatLong?.longitude ?: 106.69552
            Log.d("LatLong", "${petDetail.latitude} ${petDetail.longitude}")

            db.collection("pets").document(formID.toString())
                .set(petDetail)
                .addOnSuccessListener {
                    Log.d(tag, "Form $formID is updated")

                    // Back to home screen
                    navHostController.navigate(BottomBarScreen.Home.route) {
                        popUpTo(BottomBarScreen.Home.route) {
                            inclusive = true
                        }
                    }

                    Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "Invalid address. Try again!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getAllPets(
        onResult: (List<PetDetail>) -> Unit
    ) {
        // Create a FireStore reference
        val db = FirebaseFirestore.getInstance()
        val tag = "Firestore"

        db.collection("forms").get()
            .addOnSuccessListener { snapshot ->
                val petList = mutableListOf<PetDetail>()

                for (document in snapshot) {
                    val pet = document.toObject(PetDetail::class.java)
                    petList.add(pet)
                }

                onResult(petList)
                Log.e(tag, "Get all event data success")
            }
            .addOnFailureListener { e ->
                onResult(emptyList())
                Log.e(tag, "Failed to get event data: ${e.message}")
            }
    }

    private suspend fun uploadToStorage(uri: Uri, context: Context): Uri? {
        try {
            val storage = Firebase.storage

            // Create a storage reference from app
            val storageRef = storage.reference

            val unique_image_name = "${UUID.randomUUID()}"
            val spaceRef: StorageReference = storageRef.child("pets/$unique_image_name.jpeg")

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            var uploadedURI: Uri? = Uri.EMPTY

            byteArray?.let {
                val uploadTask = spaceRef.putBytes(byteArray)
                uploadTask
                    .addOnFailureListener {
                        Toast.makeText(
                            context,
                            "upload failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnSuccessListener { taskSnapshot ->

                        sendUploadSuccessNotification(context)
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
        } catch (e: Exception) {
            return null
        }
    }

    private fun sendUploadSuccessNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "upload_channel",
                "Upload Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "upload_channel")
            .setContentTitle("Upload Successful")
            .setContentText("Your file has been uploaded successfully.")
            .setSmallIcon(R.drawable.background_11) // Replace with your own drawable
            .build()

        notificationManager.notify(0, notification)
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