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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pawsome.common.AlertDialogBox
import com.example.pawsome.data.form.DataViewModel
import com.example.pawsome.domain.screens.SettingScreen
import com.example.pawsome.presentation.homescreen.component.HorizontalHomeEventCard
import kotlinx.coroutines.launch

@Composable
fun MyPetListScreen(
    navHostController: NavHostController,
    user: com.example.pawsome.model.User,
    dataViewModel: DataViewModel = viewModel()
) {
    val formHistoryViewModel: FormHistoryViewModel = viewModel(factory = viewModelFactory {
        FormHistoryViewModel(user = user)
    })

    val context = LocalContext.current

    // Check data loading
    val loadingState by formHistoryViewModel.isLoading.collectAsState(initial = false)

    val myPetList by formHistoryViewModel.petsList.collectAsState()
    var deletedPetID: String? = null

    val scope = rememberCoroutineScope()

    val isDialogOpen = remember { mutableStateOf(false) }

    when {
        isDialogOpen.value -> {
            AlertDialogBox(
                onDismissRequest = { isDialogOpen.value = false },
                onConfirmation = {
                    isDialogOpen.value = false
                    dataViewModel.deletePetDetail(deletedPetID, context, navHostController)
                },
                dialogTitle = "Delete confirmation",
                dialogText = "This form cannot be restored once it is deleted.",
                dialogIcon = Icons.Outlined.Warning
            )
        }
    }

    if (loadingState) {
        Text(text = "Loading data...")
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(bottom = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                              navHostController.popBackStack()
                    },
                    modifier = Modifier.padding(top = 10.dp, start = 0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                Text("My Pets",  fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 10.dp))
                Spacer(modifier = Modifier.width(400.dp))
            }

            if (myPetList.size > 0) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    userScrollEnabled = true
                ) {
                    items(myPetList) { pet ->
                        //Display to the card
                        HorizontalHomeEventCard(
                            petDetail = pet,
                            editable = true,
                            onEventClick = {
                            },
                            onEditButtonClick = {
                                scope.launch {
                                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                        "petDetail",
                                        pet
                                    )

                                    navHostController.navigate(SettingScreen.EditPet.route)
                                }
                            },
                            onDeleteButtonClick = {
                                deletedPetID = pet.id
                                isDialogOpen.value = true
                            },
                    )

                        // Add spacing at last form displayed
                        if (pet == myPetList[myPetList.size - 1])
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                            )
                    }
                }
            }
            else {
                Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                    Text(
                        text = "You haven't not uploaded any pet yet 🥲", fontSize = 22.sp
                    )
                }
            }
        }
    }
}

inline fun viewModelFactory(crossinline f: () -> FormHistoryViewModel) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = f() as T
    }

