@file:Suppress("NAME_SHADOWING")

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
import com.example.mago.R
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.Viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun AssignUser(navController: NavController, userViewModel: UserViewModel, deviceViewModel: DeviceViewModel) {

    // User-related state
    var username by remember { mutableStateOf<String?>(null) }
    var userId by remember { mutableStateOf("") }
    val allUsers by userViewModel.allUsers.observeAsState(emptyList())

    // Composter-related state
    val allComposter by deviceViewModel.alldevices.observeAsState(emptyList())
    var devicename by remember { mutableStateOf<String?>(null) }
    var deviceId by remember { mutableStateOf(0) }

    val context = LocalContext.current

    // Trigger the refresh when the composable is first composed
    LaunchedEffect(key1 = true) {
        userViewModel.refreshUsers()
        deviceViewModel.refreshDevices()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        // Select user field
        val optionsAllUsers = allUsers.map { it.user.name }
        DropdownMenuAdmin(
            options = optionsAllUsers,
            label = stringResource(id = R.string.selectUser),
            placeholder =  stringResource(id = R.string.selectUser),
            onItemSelected = { selectedUser ->
                // Find the corresponding user information based on the selected name
                val selectedUser = allUsers.find { it.user.name == selectedUser }
                if (selectedUser != null) {
                    // Update both username and userId
                    username = selectedUser.user.name
                    userId = selectedUser.user.user_id
                }
            },
            selectedOption = username
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Select device field
        val optionsAllDevices = allComposter.map { it.name }
        DropdownMenuAdmin(
            options = optionsAllDevices,
            label = stringResource(id = R.string.selectDevice),
            placeholder = stringResource(id = R.string.selectDevice),
            onItemSelected = { selectedDevice ->
                // Find the corresponding device information based on the selected name
                val selectedDevice = allComposter.find { it.name == selectedDevice }
                if (selectedDevice != null) {
                    // Update both devicename and deviceId
                    devicename = selectedDevice.name
                    deviceId = selectedDevice.id
                }
            },
            selectedOption = devicename
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Assign user button
        GreenBorderButton(
            text = "Assign user",
            enabled = (username?.isNotEmpty() == true) && devicename?.isNotEmpty() == true
        ) {

            // Perform the action when the button is clicked
            deviceViewModel.viewModelScope.launch {
                try {
                    deviceViewModel.assignUserToDevice(userId, deviceId, context)

                    val (success, errorMessage) = deviceViewModel.assignDeviceUserCreationResult

                    if (success) {
                        // Clear the fields after successful assignment
                        userId = ""
                        deviceId = 0
                        username = null // Reset the username state to clear the dropdown
                        devicename = null
                    } else {
                        Toast.makeText(context, " $errorMessage", Toast.LENGTH_SHORT).show()

                    }
                } catch (e: Exception) {
                    Toast.makeText(context, context.getString(R.string.errorAssignUser) + " " + e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
