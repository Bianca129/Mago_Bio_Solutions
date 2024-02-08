package com.example.mago.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.mago.Components.Dashboard.adminDashboardPager
import com.example.mago.Components.Dashboard.userDashboardPager
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.Viewmodel.UserViewModel


@Composable
fun Dashboard(navController: NavController, deviceViewModel: DeviceViewModel, deviceID: String?, userViewModel: UserViewModel) {
    // Convert deviceID to the corresponding page index
    val currentPage = deviceID?.toIntOrNull() ?: 0

    if (userViewModel.isAdmin.value) {
        adminDashboardPager(deviceViewModel, currentPage, navController, userViewModel)

    } else {
        userDashboardPager(deviceViewModel, currentPage, navController, userViewModel)
    }
}



