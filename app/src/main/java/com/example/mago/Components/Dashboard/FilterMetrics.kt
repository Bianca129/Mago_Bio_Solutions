package com.example.mago.Components.Dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mago.Components.Utils.getDateWithOffset
import com.example.mago.Data.Months
import com.example.mago.Data.Weeks
import com.example.mago.Data.Years
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.ui.theme.Grey


@Composable
fun FilterMetrics(deviceId: String, deviceViewModel: DeviceViewModel, selectedFilterMetric: String?, onFilterSelected: (String?) -> Unit) {
    val deviceMetricsList = deviceViewModel.deviceMetrics.value?.get(deviceId)
    val fieldNames = deviceMetricsList?.flatMap { deviceMetricsList.map { deviceMetric -> deviceMetric.field.name } }?.distinct() ?: emptyList()
    LazyRow(
        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
        content = {
            items(fieldNames.size) { index ->
                val isSelected = selectedFilterMetric == fieldNames[index]
                val title = (fieldNames[index])

                Button(
                    onClick = {
                        if (!isSelected) {
                            onFilterSelected(fieldNames[index])

                            val fieldId: Int = when (title) {
                                "Material In" -> 17
                                "Temperature" -> 1
                                else -> 0
                            }

                            val dates: List<String>
                            val time = deviceViewModel.selectedFilterDiagramMetric.value
                            dates = when (time) {
                                "Weekly" -> getDateWithOffset(Weeks(11))
                                "Monthly" -> getDateWithOffset(Months(11))
                                else -> getDateWithOffset(Years(6))
                            }
                            deviceViewModel.getAggLogs(time, deviceId.toInt(), fieldId, dates[1], dates[0])
                        }
                    },
                    modifier = Modifier
                        .padding(start = 0.dp, end = 5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Grey,
                        contentColor = if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onTertiary,
                    )
                ) {
                    Text(text = title) // Text for the filters
                }
            }
        }
    )
}