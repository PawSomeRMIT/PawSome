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


package com.example.pawsome.presentation.add_form

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pawsome.R
import com.example.pawsome.common.TitleText
import com.example.pawsome.data.form.DataViewModel
import com.example.pawsome.data.form.FormUIEvent
import com.example.pawsome.model.Booking
import com.example.pawsome.model.PetDetail
import com.example.pawsome.model.User
import com.example.pawsome.presentation.authentication.components.ButtonComponent
import com.example.pawsome.presentation.authentication.components.CustomTextField
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


var imageUri: Uri? = null

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form(
    navHostController: NavHostController,
    dataViewModel: DataViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    // Pet details
    var petName by rememberSaveable { mutableStateOf("") }
    var petGender by rememberSaveable { mutableStateOf("") }
    var petBreed by rememberSaveable { mutableStateOf("") }
    var petAnimal by rememberSaveable { mutableStateOf("") }
    var petColor by rememberSaveable { mutableStateOf("") }
    var petAge by rememberSaveable { mutableStateOf("") }
    var petDescription by rememberSaveable { mutableStateOf("") }
    var petlocation by rememberSaveable { mutableStateOf("") }
    var bookingPrice by rememberSaveable { mutableStateOf("") }

    var isExpanded by remember { mutableStateOf(false) }

    // Remember scroll state
    val scrollState = rememberScrollState()

    val scope = rememberCoroutineScope()

    // Location
    // 1. Current location -> Geocode (Long, Lat)

    // Get user details
    val auth = FirebaseAuth.getInstance()
    val userID = auth.currentUser?.uid
    var userData by remember(userID) {
        mutableStateOf(User())
    }

    LaunchedEffect(userID) {
        if (userID != null) {
            val userRef = Firebase.firestore.collection("user").document(userID)
            val snapshot = userRef.get().await()

            snapshot?.let {
                val result = User(
                    userID = it.get("userID").toString(),
                    username = it.get("username").toString(),
                    email = it.get("email").toString(),
                    image = it.get("image").toString(),
                    membership = it.get("membership").toString(),
                    chatToken = it.get("chatToken").toString(),
                    history = it.get("history") as List<Booking>
                )

                userData = result
            }
        }
    }

    // ---- UI Layouts ----

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(246,246,246)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp, 16.dp)
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "Back", tint = Color.Black)
                }
                TitleText(value = "Add new pet")
            }

            ImagePicker()

            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    CustomTextField(
                        labelValue = stringResource(id = R.string.petname_field),
                        painterResource(id = R.drawable.baseline_text_format_24),
                        onTextChanged = {
                            scope.launch {
                                dataViewModel.onEvent(FormUIEvent.PetNameChanged(it))
                                petName = it
                            }
                        },
                        errorStatus = dataViewModel.formUIState.value.petNameError
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    CustomTextField(
                        labelValue = stringResource(id = R.string.petGender_field),
                        painterResource(id = R.drawable.baseline_transgender_24),
                        onTextChanged = {
                            scope.launch {
                                dataViewModel.onEvent(FormUIEvent.PetGenderChanged(it))
                                petGender = it
                            }
                        },
                        errorStatus = dataViewModel.formUIState.value.petGenderError
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    CustomTextField(
                        labelValue = stringResource(id = R.string.petBreed_field),
                        painterResource(id = R.drawable.baseline_pets_24),
                        onTextChanged = {
                            scope.launch {
                                dataViewModel.onEvent(FormUIEvent.PetBreedChanged(it))
                                petBreed = it
                            }
                        },
                        errorStatus = dataViewModel.formUIState.value.petBreedError
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    CustomTextField(
                        labelValue = stringResource(id = R.string.petAge_field),
                        painterResource(id = R.drawable.baseline_123_24),
                        onTextChanged = {
                            scope.launch {
                                dataViewModel.onEvent(FormUIEvent.PetAgeChanged(it))
                                petAge = it
                            }
                        },
                        keyboardType = "number",
                        errorStatus = dataViewModel.formUIState.value.petAgeError
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    CustomTextField(
                        labelValue = stringResource(id = R.string.petType_field),
                        painterResource(id = R.drawable.baseline_pets_24),
                        onTextChanged = {
                            scope.launch {
                                dataViewModel.onEvent(FormUIEvent.PetTypeChanged(it))
                                petAnimal = it
                            }
                        },
                        errorStatus = dataViewModel.formUIState.value.petTypeError
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    CustomTextField(
                        labelValue = stringResource(id = R.string.petColor_field),
                        painterResource(id = R.drawable.baseline_color_lens_24),
                        onTextChanged = {
                            scope.launch {
                                dataViewModel.onEvent(FormUIEvent.PetColorChanged(it))
                                petColor = it
                            }
                        },
                        errorStatus = dataViewModel.formUIState.value.petColorError
                    )
                }
            }

            CustomTextField(
                labelValue = stringResource(id = R.string.petLocation_field),
                painterResource(id = R.drawable.baseline_location_24),
                onTextChanged = {
                    scope.launch {
                        dataViewModel.onEvent(FormUIEvent.PetLocationChanged(it))
                        petlocation = it
                    }
                },
                errorStatus = dataViewModel.formUIState.value.petlocationError
            )

            CustomTextField(
                labelValue = stringResource(id = R.string.petDescription_field),
                painterResource(id = R.drawable.baseline_description_24),
                onTextChanged = {
                    scope.launch {
                        dataViewModel.onEvent(FormUIEvent.PetDescriptionChanged(it))
                        petDescription = it
                    }
                },
                errorStatus = dataViewModel.formUIState.value.petDescriptionError
            )

            CustomTextField(
                labelValue = stringResource(id = R.string.bookingPrice_field),
                painterResource(id = R.drawable.baseline_attach_money_24),
                onTextChanged = {
                    scope.launch {
                        dataViewModel.onEvent(FormUIEvent.BookingPriceChanged(it))
                        bookingPrice = it
                    }
                },
                keyboardType = "text",
                lastField = true,
                errorStatus = dataViewModel.formUIState.value.bookingPriceError
            )

            Spacer(modifier = Modifier.padding(8.dp))

            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val currentDate = sdf.format(Date())

//        val addressLatLong = getLocationFromAddress(petlocation, LocalContext.current)

            val context = LocalContext.current

            val newPetDetail = PetDetail(
                petName = petName,
                petGender = petGender,
                petBreed = petBreed,
                petAnimal = petAnimal,
                petColor = petColor,
                petStatus = "Available",
                petAge = petAge,
                petDescription = petDescription,
                img = imageUri,
                bookingPricePerDay = bookingPrice,
                ownerId = userData.userID,
                distance = 0.0,
                petAddress = petlocation
            )

            ButtonComponent(
                value = "Save",
                onButtonClicked = {
                    scope.launch {
                        dataViewModel
                            .saveForm(newPetDetail, context, navHostController)
                    }
                },
                isEnabled = dataViewModel.allFormValidatePassed.value
            )

            Spacer(modifier = Modifier.padding(50.dp))
        }
    }
}

@Composable
fun ImagePicker(maxSelection: Int = 1) {
    var selectedImage by remember{
        mutableStateOf<Uri?>(Uri.parse(""))
    }

    val context = LocalContext.current

    val selectBtn = if (maxSelection > 1) {
        "Select up to $maxSelection photos"
    } else {
        "Select a photo"
    }

    val singleImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImage = uri }
    )

//    val multipleImagePickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickMultipleVisualMedia(
//            maxItems = if (maxSelection > 1) maxSelection else 2
//        ),
//        onResult = { uris -> selectedImages = uris }
//    )

    fun launchPhotoPicker() {
        singleImagePickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageLayoutView(selectedImages = selectedImage)

        Button(
            onClick = { launchPhotoPicker() },
            modifier = Modifier.width(200.dp),
            elevation = ButtonDefaults.buttonElevation(10.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black
            )
        ) {
            Icon(imageVector = Icons.Filled.AddAPhoto, contentDescription = "Add photo")
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(selectBtn)
        }
        Column {
            LaunchedEffect(key1 = selectedImage) {
                selectedImage?.let {
                    imageUri = it
                }
            }

            if (selectedImage != null) {
                Text(text = context.getImageName(selectedImage!!))
            }
        }
    }
}

fun Context.getImageName(uri: Uri): String {
    var imageName = ""
    val cursor = contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (it.moveToFirst()) {
            imageName = it.getString(nameIndex)
        }
    }
    return imageName
}


@Composable
fun ImageLayoutView(selectedImages: Uri?) {
    AsyncImage(
        model = selectedImages,
        contentDescription = "Site image",
        modifier = Modifier
            .height(300.dp)
            .width(380.dp)
            .clip(RoundedCornerShape(20.dp))
    )
}

fun Int.toMonthName(): String {
    return DateFormatSymbols().months[this]
}

fun Date.toFormattedString(): String {
    val simpleDateFormat = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}


fun formatDate(timeInMillis: Long): String {
    val formatter = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return formatter.format(Date(timeInMillis))
}
