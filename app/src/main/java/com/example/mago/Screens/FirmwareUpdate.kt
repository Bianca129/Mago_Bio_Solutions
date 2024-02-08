package com.example.mago.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.mago.Components.Dashboard.RowPreviousUpdates
import com.example.mago.Components.Style.GreenBorderButton
import com.example.mago.Components.Style.TextFieldAdmin
import com.example.mago.R
import com.example.mago.Viewmodel.DeviceViewModel
import kotlinx.coroutines.launch

@Composable
fun FirmwareUpdate(navController: NavController, deviceViewModel: DeviceViewModel, deviceId: Int) {
    // State variable to trigger recomposition after a successful firmware update
    var firmwareUpdateSuccessful by remember { mutableStateOf(false) }

    // Observe changes in the firmwareList LiveData
    val firmwareList by deviceViewModel.firmwareList.observeAsState(emptyMap())

    // Observe the LiveData for firmwareList changes
    LaunchedEffect(firmwareList) {
        deviceViewModel.refreshFirmware(deviceId)
    }

    // Extract the list of firmware updates for the specified deviceId
    val firmwareUpdatesForDevice = firmwareList[deviceId.toString()] ?: emptyList()

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        var file: String by remember { mutableStateOf("") }

        Text(
            stringResource(id = R.string.currentVersion),
            style = MaterialTheme.typography.bodyMedium
        )

        // Display the last version from the firmwareUpdatesForDevice list
        val lastVersion = firmwareUpdatesForDevice.lastOrNull()?.let { "Version: ${it.file}" }
            ?: "No versions available"
        Text(lastVersion, style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(15.dp))

        TextFieldAdmin(label = "Update firmware", onValueChange = { file = it }, value = file)

        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 0.dp), // Adjust padding as needed
            horizontalArrangement = Arrangement.End
        ) {
            GreenBorderButton(
                text = "Upload",
                enabled = file.isNotBlank()
            ) {
                // Handle the upload action
                deviceViewModel.viewModelScope.launch {
                    try {
                        deviceViewModel.updateFirmware(deviceId, file, context)

                        val (success, errorMessage) = deviceViewModel.firmwareCreationResult

                        if (success) {
                            // Set the state variable to trigger recomposition
                            firmwareUpdateSuccessful = true
                            // Clear the fields after successful assignment
                            file = ""
                        } else {
                            Toast.makeText(context, "Error updating firmware: $errorMessage", Toast.LENGTH_SHORT).show()

                        }
                    } catch (e: Exception) {
                        // Handle exceptions if needed
                        Toast.makeText(context, "Error updating firmware:  ${e.message}", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(stringResource(id = R.string.previouslyUploaded), style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(15.dp))

        // Display previous versions using RowDownload composable
        firmwareUpdatesForDevice.forEach { firmwareUpdate ->
            RowPreviousUpdates(
                date = firmwareUpdate.updatedAt,  // Use createdAt or updatedAt based on your requirement
                version = firmwareUpdate.file
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}



