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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.Components.Dashboard.MetricsInformationRowListBox
import com.example.mago.Components.Style.Color
import com.example.mago.Components.Style.ComposterState
import com.example.mago.Components.Utils.convertIsoToDate
import com.example.mago.Data.Device
import com.example.mago.Data.DeviceMetrics
import com.example.mago.Data.IdColorItem
import com.example.mago.Data.Notification
import com.example.mago.R
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.ui.theme.Black
import com.example.mago.ui.theme.ComplementaryLightRed
import com.example.mago.ui.theme.Grey
import com.example.mago.ui.theme.MagoGreen
import com.example.mago.ui.theme.StatusYellow


@Composable
fun ListBox(
    listBoxItem: Device,
    deviceMetric: List<DeviceMetrics>?,
    navController: NavController,
    deviceViewModel: DeviceViewModel,
    deviceMessages: List<Notification>?,
    index: Int,
    color: Any
) {

    val background =
        when (color) {
            Color.StatusYellow -> {
                StatusYellow
            }
            Color.ComplementaryRed -> {
                ComplementaryLightRed
            }
            else -> {
                MagoGreen
            }
        }

    val backgroundIdColorItem =
        when (color) {
            Color.StatusYellow -> {
                Color.StatusYellow
            }
            Color.ComplementaryRed -> {
                Color.ComplementaryRed
            }
            else -> {
                Color.MagoGreen
            }
        }

    // Assuming overallStatus is a Components.Color
    val idColorItem = IdColorItem(listBoxItem.id, backgroundIdColorItem)
    deviceViewModel.idColorList.add(idColorItem)    // Box representing each list item


    Box(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(bottom = 12.dp)
        .background(background,
            shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomStart = 10.dp,
                bottomEnd = 10.dp
            )
        )
        .clickable {
            if (index != -1) {
                navController.navigate("Dashboard/$index")
                deviceViewModel.updateCurrentPage(index)
            }
        }
    ) {
        // Row containing the content of each list item
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Left Column: Text information
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.85f)
                    .padding(start = 0.dp)
            ) {
                // Device name
                Text(
                    text = listBoxItem.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 12.dp)
                        .height(25.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = Black
                )

                // Last update text or spacer if no messages
                if (!deviceMessages.isNullOrEmpty() && deviceMessages[0].message.isNotEmpty()) {
                    Text(
                        text = deviceMessages[0].message,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 3.dp, start = 12.dp)
                            .height(14.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = Grey
                    )
                } else {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                MetricsInformationRowListBox(deviceMetric, index)

                Spacer(modifier = Modifier.height(10.dp))

                // Last update timestamp
                Text(
                    text = stringResource(id = R.string.lastUpdate ) + convertIsoToDate(listBoxItem.updatedAt),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, start = 12.dp)
                        .height(15.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = Grey
                )
            }

            // Right Column: Composter State
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.15f)
                    .padding(end = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ComposterState(listBoxItem, deviceViewModel)
            }
        }
    }
}



