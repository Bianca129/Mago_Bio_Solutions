@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.mago.Components.List

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.Components.Style.LoadingIndicator
import com.example.mago.Components.Style.SearchBarListBox
import com.example.mago.Components.Style.getOverallColor
import com.example.mago.Components.Utils.SharedPreferencesManager
import com.example.mago.Data.DeviceMetrics
import com.example.mago.Data.DeviceUser
import com.example.mago.Data.Notification
import com.example.mago.R
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.Viewmodel.UserViewModel
import com.example.mago.ui.theme.MagoGreen

@SuppressLint("SuspiciousIndentation")
@Composable
fun ListComposterUser(
    devicesUser: List<DeviceUser>,
    deviceViewModel: DeviceViewModel,
    userViewModel: UserViewModel,
    isDataLoading: Boolean?,
    deviceMetrics: Map<String, List<DeviceMetrics>>,
    deviceMessages: Map<String, List<Notification>>,
    navController: NavController
) {

    var searchText by remember { mutableStateOf("") }

    // Search and filter devices for regular users
    val filteredDevices = devicesUser.filter {
        it.device.name.contains(searchText, ignoreCase = true)
    }

    // Scroll state for the LazyColumn
    val scrollState = rememberScrollState()
    var selectedListAlphabetical by deviceViewModel.selectedFilterListAlphabetical

// Sorting logic based on the click on icon for alphabetical order
    val sortedDevices = when (selectedListAlphabetical) {
        true -> filteredDevices.sortedBy { it.device.name }
        else -> {
            val sortedDeviceColors = devicesUser.map { device ->
                val deviceMetric = deviceMetrics[device.device.id.toString()]

                // Define your criteria to get the color
                val color = if (!deviceMetric.isNullOrEmpty()) {
                    val sortedValues = deviceMetric.sortedBy { it.field.name }
                    getOverallColor(
                        temperature = sortedValues.find { it.field.name == "Temperature" }?.value
                            ?: 0.0,
                        value = sortedValues.find { it.field.name == "Material In" }?.value ?: 0.0
                    )
                } else {
                    MagoGreen // Default color if no criteria are met
                }

                device.device.id to color
            }.toMap()

            // Define the color order map
            val colorOrder = mapOf(
                "ComplementaryRed" to 0,
                "StatusYellow" to 1
                // Add more colors and their order as needed
            )

            // Sort the device colors based on the defined order
            val sortedDevicesColor = sortedDeviceColors.toList().sortedBy { (id, color) ->
                colorOrder[color?.toString()] ?: Int.MAX_VALUE
            }.toMap()

            // Order filteredDevices based on the order of colors
            filteredDevices.sortedBy { device ->
                val color = sortedDevicesColor[device.device.id]
                colorOrder[color?.toString()] ?: Int.MAX_VALUE
            }

        }
    }


    // Map original order to sorted order for navigation
    val originalOrderToSortedOrderMap =
        filteredDevices.withIndex().associate { (sortedIndex, device) ->
            device.device to sortedIndex
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (isDataLoading == true) {
            // Show loading indicator
            LoadingIndicator()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 0.dp),
            ) {
                // Row for search bar and alphabetical sort icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                ) {
                    SearchBarListBox(onSearchTextChange = { searchText = it })
                    Spacer(modifier = Modifier.width(10.dp))
                    //reload
                    Icon(
                        painter = painterResource(id = R.drawable.reload),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                if (userViewModel.isAdmin.value) {
                                    deviceViewModel.getDevicesWithMetricsAndNotifications()
                                } else {
                                    deviceViewModel.getDevicesWithMetricsAndNotificationsForUsers(
                                        SharedPreferencesManager.getUserId()
                                    )
                                }
                            }
                            .padding(end = 0.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    // Alphabetical sort icon
                    Icon(
                        painter = painterResource(id = R.drawable.sort_alphabetically),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                selectedListAlphabetical = !selectedListAlphabetical
                            }
                            .padding(end = 0.dp),
                        tint = if (selectedListAlphabetical) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))


                // Display ListBox elements based on the search and sorting
                sortedDevices.forEachIndexed { index, device ->
                    // Check that the index is within the valid range and that the list is not empty
                    if (index < (sortedDevices.size)) {
                        val deviceMetric = deviceMetrics[device.device.id.toString()]
                        val deviceMessage = deviceMessages[device.device.id.toString()]
                        // Use the mapped index for navigation
                        val originalIndex = originalOrderToSortedOrderMap[device.device]

                        // Define your criteria to get the color
                        val color = if (!deviceMetric.isNullOrEmpty()) {
                            val sortedValues = deviceMetric.sortedBy { it.field.name }
                            getOverallColor(
                                temperature = sortedValues.find { it.field.name == "Temperature" }?.value
                                    ?: 0.0,
                                value = sortedValues.find { it.field.name == "Material In" }?.value ?: 0.0
                            )
                        } else {
                            MagoGreen // Default color if no criteria are met
                        }
                        ListBox(
                                device.device,
                                deviceMetric,
                                navController,
                                deviceViewModel,
                                deviceMessage,
                                originalIndex ?: -1,
                                color
                            )
                    }
                }
            }
        }
    }

}