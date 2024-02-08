package com.example.mago.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.mago.Components.Utils.SharedPreferencesManager
import com.example.mago.Viewmodel.DeviceViewModel


@Composable
fun Logout(navController: NavController, deviceViewModel: DeviceViewModel) {
    navController.navigate(Screens.Login.route)
    val context = LocalContext.current

    // Use SharedPreferencesManager to clear token, admin, userId, profile picture and username
    SharedPreferencesManager.init(context)
    SharedPreferencesManager.setAuthToken("")
    SharedPreferencesManager.setAdmin(false)
    SharedPreferencesManager.setUserId("")
    SharedPreferencesManager.setProfilePicture("")
    SharedPreferencesManager.setUsername("")
    deviceViewModel.showBottomBar.value = false
}





