package com.example.mago.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mago.Components.Style.DropdownMenuAdmin
import com.example.mago.Components.Style.GreenBorderButton
import com.example.mago.Components.Style.TextFieldAdmin
import com.example.mago.Components.Utils.SharedPreferencesManager
import com.example.mago.R
import com.example.mago.Viewmodel.DeviceViewModel
import kotlinx.coroutines.launch

@Composable
fun CreateDevice(navController: NavController, deviceViewModel: DeviceViewModel){
    var deviceType by remember { mutableStateOf<String?>(null) }
    var deviceTypeId by remember { mutableStateOf(0) }
    var name by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Collect the device types as a State
    val deviceTypeInformation by deviceViewModel.deviceTypesInformation.observeAsState(emptyList())


    // Trigger the refresh when the composable is first composed
    LaunchedEffect(key1 = true) {
        deviceViewModel.refreshDeviceTypes()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ){
        val options = deviceTypeInformation.map { it.name }

        DropdownMenuAdmin(
            options = options,
            label = stringResource(id = R.string.deviceType),
            placeholder = stringResource(id = R.string.deviceType),
            onItemSelected = { selectedType ->
                // Find the corresponding device type information based on the selected name
                val selectedDeviceInfo = deviceTypeInformation.find { it.name == selectedType }
                if (selectedDeviceInfo != null) {
                    // Update both deviceType and deviceTypeId
                    deviceType = selectedType
                    deviceTypeId = selectedDeviceInfo.id
                }
            },
            selectedOption = deviceType
        )



        Spacer(modifier = Modifier.height(20.dp))
        TextFieldAdmin(
            label = stringResource(id = R.string.nameComposter),
            value = name,
            onValueChange = {
                name = it
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        GreenBorderButton(
            text = stringResource(id = R.string.admin_createDevice),
            enabled = deviceType != null && name.isNotBlank()
        ) {
            val sendSettingsAtConn = false
            val sendSettingsNow = false
            val authId = SharedPreferencesManager.getUserId()
            val password = "Random"


            deviceViewModel.viewModelScope.launch {
                try {
                    deviceViewModel.createDevice(
                        name,
                        deviceTypeId,
                        sendSettingsAtConn,
                        sendSettingsNow,
                        authId,
                        password,
                        context
                    )


                   val (success, errorMessage) = deviceViewModel.deviceCreationResult

                    if (success) {
                        deviceViewModel.getDevicesWithMetricsAndNotifications()
                        name = ""
                        deviceType = null
                        deviceTypeId = 0
                        deviceViewModel.resetDeviceCreationResult()
                    } else {
                        // Show Toast with the error message
                        Toast.makeText(context, errorMessage ?: "An error occurred", Toast.LENGTH_SHORT).show()
                    }


                }catch (e: Exception) {
                    Toast.makeText(context, context.getString(R.string.errorCreatingDevice) + " " +  e.message, Toast.LENGTH_SHORT).show()
                }
            }
            

        }
    }
}

