package com.example.mago.Components.Dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mago.Components.Utils.getDateWithOffset
import com.example.mago.Data.Months
import com.example.mago.Data.Weeks
import com.example.mago.Data.Years
import com.example.mago.Viewmodel.DeviceViewModel

@Composable
fun FilterTimeSelectorDiagram(deviceViewModel: DeviceViewModel, deviceId : Int, fieldId : Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ){
            TimeSelectorDiagram("Weekly", deviceViewModel.selectedFilterDiagramMetric.value == "Weekly") {
                deviceViewModel.selectedFilterDiagramMetric.value = "Weekly"
                val dates = getDateWithOffset(Weeks(11))
                deviceViewModel.getAggLogs("Weekly", deviceId, fieldId, dates[1],dates[0])

            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ){
            TimeSelectorDiagram("Monthly", deviceViewModel.selectedFilterDiagramMetric.value == "Monthly") {
                deviceViewModel.selectedFilterDiagramMetric.value = "Monthly"
                val dates = getDateWithOffset(Months(11))
                deviceViewModel.getAggLogs("Monthly", deviceId, fieldId, dates[1],dates[0])

            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ){
            TimeSelectorDiagram("Yearly", deviceViewModel.selectedFilterDiagramMetric.value == "Yearly") {
                deviceViewModel.selectedFilterDiagramMetric.value = "Yearly"
                val dates = getDateWithOffset(Years(6))
                deviceViewModel.getAggLogs("Yearly", deviceId, fieldId, dates[1],dates[0])
            }
        }
    }
}