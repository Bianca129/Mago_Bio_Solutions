package com.example.mago.Components.Dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.Data.Device
import com.example.mago.R
import com.example.mago.Viewmodel.DeviceViewModel

@Composable
fun ShowMetrics(deviceViewModel: DeviceViewModel, device: Device, navController: NavController) {
    val deviceMetrics by deviceViewModel.deviceMetrics.observeAsState(emptyMap())
    val deviceMetric = deviceMetrics[device.id.toString()]
    val sortedValues = deviceMetric?.sortedBy { it.field.name }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (deviceMetric != null && sortedValues != null) {


            for (rowValues in sortedValues.chunked(2)) {
                DashboardRow(rowValues, navController, device.id.toString(), deviceViewModel)
            }
        }

        if(sortedValues.isNullOrEmpty()){
            Text(stringResource(id = R.string.noMetrics) + " " + device.name + ".")
        }

    }
}