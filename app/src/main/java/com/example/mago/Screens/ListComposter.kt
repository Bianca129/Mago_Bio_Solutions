package com.example.mago.Screens


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.mago.Components.List.ListComposterAdmin
import com.example.mago.Components.List.ListComposterUser
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.Viewmodel.UserViewModel


@Composable
fun ListComposter(navController: NavController, deviceViewModel: DeviceViewModel, userViewModel: UserViewModel) {
    // Observe device lists based on user type
    val devicesAdmin by deviceViewModel.devicesAdmin.observeAsState(emptyList())
    val devicesUser by deviceViewModel.devicesUser.observeAsState(emptyList())

    val deviceMetrics by deviceViewModel.deviceMetrics.observeAsState(emptyMap())
    val deviceMessages by deviceViewModel.notifications.observeAsState(emptyMap())
    val isDataLoading by deviceViewModel.isLoading.observeAsState()

    // Check if the user is an admin
    if (userViewModel.isAdmin.value) {
        ListComposterAdmin(devicesAdmin, deviceViewModel, userViewModel, isDataLoading, deviceMetrics, deviceMessages, navController)

    } else {
        ListComposterUser(devicesUser, deviceViewModel, userViewModel, isDataLoading, deviceMetrics, deviceMessages, navController)
    }
}


