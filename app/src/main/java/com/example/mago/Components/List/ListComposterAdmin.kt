@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.mago.Components.List

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.Components.Style.Color
import com.example.mago.Components.Style.LoadingIndicator
import com.example.mago.Components.Style.SearchBarListBox
import com.example.mago.Components.Style.getOverallColor
import com.example.mago.Components.Utils.SharedPreferencesManager
import com.example.mago.Data.Device
import com.example.mago.Data.DeviceMetrics
import com.example.mago.Data.IdStateComposter
import com.example.mago.Data.Notification
import com.example.mago.R
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.Viewmodel.UserViewModel
import com.example.mago.ui.theme.MagoGreen

@Composable
fun ListComposterAdmin(
    devicesAdmin: List<Device>,
    deviceViewModel: DeviceViewModel,
    userViewModel: UserViewModel,
    isDataLoading: Boolean?,
    deviceMetrics: Map<String, List<DeviceMetrics>>,
    deviceMessages: Map<String, List<Notification>>,
    navController: NavController
) {
    var searchText by remember { mutableStateOf("") }

    // Search and filter devices for admin users
    val filteredDevices = devicesAdmin.filter {
        it.name.contains(searchText, ignoreCase = true)
    }



    // Scroll state for the LazyColumn
    val scrollState = rememberScrollState()
    val selectedFilter by deviceViewModel.selectedFilterList
    var selectedListAlphabetical by deviceViewModel.selectedFilterListAlphabetical

    val sortedDevicesColorList: List<Device>
    val filterIdColorListById = deviceViewModel.idColorList
    val idStateComposterList by deviceViewModel.idComposterListState.collectAsState()

    // Sorting logic based on the selected filter
    val sortedDevices =
        if (selectedListAlphabetical) {
            filteredDevices.sortedBy { it.name }
        }else if(selectedFilter == "Error") {
            val errorDevices = filterIdColorListById.filter { it.color == Color.ComplementaryRed }
            // Extract the IDs from the filtered list
            val errorDeviceIds = errorDevices.map { it.id }
            filteredDevices.filter { it.id in errorDeviceIds }
        }else if(selectedFilter == "Warning") {
            val warningDevices = filterIdColorListById.filter { it.color == Color.StatusYellow }
            // Extract the IDs from the filtered list
            val warningDevicesIds = warningDevices.map { it.id }
            filteredDevices.filter { it.id in warningDevicesIds }
        }else if(selectedFilter == "Success") {
            val successDevices = filterIdColorListById.filter { it.color != Color.StatusYellow && it.color != Color.ComplementaryRed}
            // Extract the IDs from the filtered list
            val successDevicesIds = successDevices.map { it.id }
            filteredDevices.filter { it.id in successDevicesIds }
        }else if(selectedFilter == "Active") {
            val activeDevices = idStateComposterList.filter { it.on }
            val activeDevicesId = activeDevices.map{it.id}
            filteredDevices.filter {it.id in activeDevicesId}
        }else if(selectedFilter == "Inactive") {
            val inActiveDevices: List<IdStateComposter> = idStateComposterList.filter { !it.on }
            val inActiveDevicesId = inActiveDevices.map{it.id}
            filteredDevices.filter {it.id in inActiveDevicesId}
        } else {
            val sortedDeviceColors = devicesAdmin.associate { device ->
                val deviceMetric = deviceMetrics[device.id.toString()]

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

                device.id to color
            }

            // Define the color order map
            val colorOrder = mapOf(
                "ComplementaryRed" to 0,
                "StatusYellow" to 1
            )

            // Sort the device colors based on the defined order
            val sortedDevicesColor = sortedDeviceColors.toList().sortedBy { (id, color) ->
                colorOrder[color?.toString()] ?: Int.MAX_VALUE
            }.toMap()

            // Order filteredDevices based on the order of colors
            sortedDevicesColorList = filteredDevices.sortedBy { device ->
                val color = sortedDevicesColor[device.id]
                colorOrder[color?.toString()] ?: Int.MAX_VALUE
            }
            sortedDevicesColorList
        }


    // Map original order to sorted order for navigation
    val originalOrderToSortedOrderMap =
        filteredDevices.withIndex().associate { (sortedIndex, device) ->
            device to sortedIndex
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

                // FilterListBox component
                FilterListBoxes(selectedFilter = selectedFilter) { newFilter ->
                    deviceViewModel.selectedFilterList.value = newFilter
                }

                // Display ListBox elements based on the search and sorting
                sortedDevices.forEachIndexed { index, device ->


                    // Check that the index is within the valid range and that the list is not empty
                    if (index < sortedDevices.size) {
                        val deviceMetric = deviceMetrics[device.id.toString()]
                        val deviceMessage = deviceMessages[device.id.toString()]
                        val originalIndex = originalOrderToSortedOrderMap[device]

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
                            device,
                            deviceMetric,
                            navController,
                            deviceViewModel,
                            deviceMessage,
                            originalIndex ?: -1,
                            color
                        )
                    }
                }
                if(sortedDevices.isEmpty()){
                    Text( stringResource(id = R.string.noDevices))
                }
            }
        }
    }
}