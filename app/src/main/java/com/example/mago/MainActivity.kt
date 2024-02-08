package com.example.mago

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.mago.Components.Utils.SharedPreferencesManager
import com.example.mago.Components.Utils.SurfaceMago
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.Viewmodel.UserViewModel
import com.example.mago.ui.theme.MagoTheme


class MainActivity : ComponentActivity() {
    @get:SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    private val deviceViewModel: DeviceViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MagoTheme {
                SharedPreferencesManager.init(applicationContext)
                val token = SharedPreferencesManager.getAuthToken()
                val admin = SharedPreferencesManager.isAdmin()
                val userId = SharedPreferencesManager.getUserId()

                if(token != ""){
                    deviceViewModel.showBottomBar.value = true
                    userViewModel.isAdmin.value = admin == true
                    if(userViewModel.isAdmin.value){
                        deviceViewModel.getDevicesWithMetricsAndNotifications()
                    }else{
                        deviceViewModel.getDevicesWithMetricsAndNotificationsForUsers(userId)
                    }
                }
                SurfaceMago(deviceViewModel, userViewModel, token)
            }
        }
    }
}



