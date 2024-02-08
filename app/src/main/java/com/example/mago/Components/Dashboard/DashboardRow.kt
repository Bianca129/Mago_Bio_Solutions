package com.example.mago.Components.Dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.Components.Style.getDrawableId
import com.example.mago.Data.DeviceMetrics
import com.example.mago.Screens.Screens
import com.example.mago.Viewmodel.DeviceViewModel

@Composable
fun DashboardRow(rowValues: List<DeviceMetrics>, navController: NavController, deviceId: String, deviceViewModel: DeviceViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        //Field --> DeviceMetric
        for ((index, deviceMetric) in rowValues.withIndex()) {
            DashboardTile(
                metric = deviceMetric.field.name,
                clickedMetric = deviceMetric.field.name,
                icon = getDrawableId(deviceMetric.field.name),
                value = "${"%.2f".format(deviceMetric.value)} " + (deviceMetric.field.unit.symbol),
                modifier = Modifier.weight(1f),
                onClick = { clickedMetric, metric ->
                    // Pass both clickedMetric and deviceId to the onClick function
                    navController.navigate("${Screens.Metric.route}/$clickedMetric/$deviceId") {
                        launchSingleTop = true
                        restoreState = true
                        deviceViewModel.updateTitle("$clickedMetric.", false)
                    }

                }
            )
            // Add Spacer if it's the last item and the total number of items is odd
            if (index == rowValues.size - 1 && rowValues.size % 2 == 1) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
