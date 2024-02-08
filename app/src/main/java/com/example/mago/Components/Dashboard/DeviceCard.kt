package com.example.mago.Components.Dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.Data.Device
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.Viewmodel.UserViewModel


@Composable
fun DeviceCard(
    navController: NavController,
    device: Device,
    deviceViewModel: DeviceViewModel,
    userViewModel: UserViewModel
) {

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth()) {
        // Display dashboard based on user type
        when {
            userViewModel.isAdmin.value -> {
                BoxDashboardAdmin(device, deviceViewModel)
                Spacer(modifier = Modifier.height(16.dp))
            }
            else -> {
                BoxDashboardUser(device = device, deviceViewModel = deviceViewModel)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Common actions for both admin and non-admin users
        ActionsRow(navController, device, userViewModel, context, deviceViewModel)

        Spacer(modifier = Modifier.height(16.dp))
        DashboardTabs(deviceViewModel, device, navController)
    }


}
