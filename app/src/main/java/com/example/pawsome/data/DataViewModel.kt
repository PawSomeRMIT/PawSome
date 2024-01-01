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


package com.example.pawsome.data

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pawsome.model.EventData
import com.example.pawsome.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class DataViewModel: ViewModel() {
    // Temp fetching data
    private val tempFetchData = mutableStateOf(listOf<EventData>())

    // Event list for storing fetching data
    val eventsList: State<List<EventData>> = tempFetchData

    init {
        getAllEvents { dataList ->
            tempFetchData.value = dataList
            Log.d("firestore", "GET FORM DATA SUCCESS: ${eventsList.value.size}")
        }
    }

//    fun saveForm(
//        tag: String,
//        image: Uri?,
//        organizerName: String,
//        eventName: String,
//        eventTime: String,
//        location: String,
//        attendeeCount: Int,
//        description: String
//    ) {
//        // Create a storage reference
//        val storage = FirebaseStorage.getInstance().reference.child("sites/${UUID.randomUUID()}.jpg")
//
//        // Create a FireStore reference
//        val db = FirebaseFirestore.getInstance()
//
//        var imgUrl: String = ""
//
//        image?.let {
//            storage.putFile(it)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        storage.downloadUrl.addOnSuccessListener { uri ->
//                            imgUrl = uri.toString()
//
//                            val formData = EventData(
//                                tag = tag,
//                                imageUrl =  imgUrl,
//                                organizerName =  organizerName,
//                                eventName = eventName,
//                                eventTime = eventTime,
//                                location = location,
//                                attendeeCount =  attendeeCount,
//                                description = description)
//
//                            db.collection("form")
//                                .add(formData)
//                                .addOnSuccessListener {
//                                    updateUserDetails()
//
//                                    Log.d("Firestore", "Inside_OnSuccessListener")
//                                    Log.d("Firestore", "Save form successfully")
//                                }
//                                .addOnFailureListener{
//                                    Log.d("Firestore", "Inside_OnFailureListener")
//                                    Log.d("Firestore", "Exception= ${it.message}")
//                                    Log.d("Firestore", "Exception= ${it.localizedMessage}")
//                                }
//                        }
//                    } else {
//                        Log.d("Storage", "Upload failed: ${task.exception?.message}")
//                    }
//                }
//        }
//    }


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


    fun getAllEvents(
        onResult: (List<EventData>) -> Unit
    ) {
        // Create a FireStore reference
        val db = FirebaseFirestore.getInstance()
        val tag = "firestore"

        db.collection("form")
            .get()
            .addOnSuccessListener {snapshot ->
                val eventList = mutableListOf<EventData>()

                for (document in snapshot) {
                    val event = document.toObject(EventData::class.java)
                    eventList.add(event)
                }

                onResult(eventList)
                Log.e(tag, "Get all event data success")
            }
            .addOnFailureListener {e ->
                onResult(emptyList())
                Log.e(tag, "Failed to get event data: ${e.message}")
            }
    }
}