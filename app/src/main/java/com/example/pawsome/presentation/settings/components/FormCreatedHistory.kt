package com.example.pawsome.presentation.settings.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pawsome.domain.PetsListScreen
import com.example.pawsome.presentation.homescreen.component.HorizontalHomeEventCard
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

@Composable
fun FormCreatedHistory(
    navHostController: NavHostController,
    location: LatLng,
    user: com.example.pawsome.model.User
) {
    val formHistoryViewModel: FormHistoryViewModel = viewModel(factory = viewModelFactory {
        FormHistoryViewModel(currentLocation = location, user = user)
    })

    // Check data loading
    val loadingState by formHistoryViewModel.isLoading.collectAsState(initial = false)

    val myPetList by formHistoryViewModel.petsList.collectAsState()

    var scope = rememberCoroutineScope()

    if (loadingState) {
        Text(text = "Loading data...")
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(bottom = 20.dp)
        ) {
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
                        onEventClick = {
                            scope.launch {
                                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                    "petDetail",
                                    pet
                                )

                                val owner = formHistoryViewModel.getUserFromFireStore(uId = pet.ownerId)

                                Log.d("Before nav", owner.toString())

                                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                    "owner",
                                    owner
                                )

                                navHostController.navigate(PetsListScreen.DetailScreen.route)
                            }
                        }
                    )

                    // Add spacing at last form displayed
                    if (pet == myPetList[myPetList.size-1])
                        Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))
                }
            }
        }
    }
}

inline fun viewModelFactory(crossinline f: () -> FormHistoryViewModel) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = f() as T
    }
