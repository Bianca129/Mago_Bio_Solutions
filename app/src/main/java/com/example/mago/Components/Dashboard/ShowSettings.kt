package com.example.mago.Components.Dashboard

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mago.Components.Style.MinimalDialog
import com.example.mago.Data.Device
import com.example.mago.Data.DeviceSettings
import com.example.mago.R
import com.example.mago.Viewmodel.DeviceViewModel

@Composable
fun ShowSettings(deviceViewModel: DeviceViewModel, device: Device) {
    // Observe device settings from the ViewModel
    val deviceSettings by deviceViewModel.deviceSettings.observeAsState()

    // Use LaunchedEffect to observe changes in device settings
    LaunchedEffect(device.id) {
        // Fetch device settings when device.id changes
        deviceViewModel.getDeviceSettings(device.id)
    }

    // Filter settings for the current device
    val deviceSettingsForDevice = deviceSettings?.filter { it.deviceId == device.id } ?: emptyList()
    var selectedSettings by remember { mutableStateOf<DeviceSettings?>(null) }

    // Map to store associations between setting names and newest DeviceSettings
    val newestSettingsMap = remember { mutableMapOf<String, DeviceSettings>() }

    // Column to display the settings
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Check if there are no settings for the current device
        if (deviceSettingsForDevice.isEmpty()) {
            Text(stringResource(id = R.string.noSettings) + " " + device.name + ".")
        } else {
            // Iterate through settings to find the newest DeviceSettings for each name
            for (settings in deviceSettingsForDevice) {

                val currentNewest = newestSettingsMap[settings.setting.name]
                if (currentNewest == null || settings.setting.updatedAt > currentNewest.setting.updatedAt) {
                    newestSettingsMap[settings.setting.name] = settings
                }
            }

            // Display each setting as an ItemSettingDashboard
            newestSettingsMap.values.forEach { newestSetting ->
                ItemSettingDashboard(setting = newestSetting, arrow = true) {
                    // Set the selected entry when clicked
                    selectedSettings = newestSetting
                }
            }
        }

        // Check if an item is selected and display the dialog
        selectedSettings?.let { settings ->
            MinimalDialog(
                device,
                deviceViewModel,
                settings = settings
            ) {
                // Execute code here to be run when the dialog is dismissed
                selectedSettings = null // Close the dialog
            }
        }
    }
}
