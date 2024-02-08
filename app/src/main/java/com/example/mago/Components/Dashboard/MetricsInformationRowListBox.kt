package com.example.mago.Components.Dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mago.Components.Style.getMaterialInColorText
import com.example.mago.Components.Style.getTemperatureColorText
import com.example.mago.Data.DeviceMetrics
import com.example.mago.R
import com.example.mago.ui.theme.ComplementaryGreen

@Composable
fun MetricsInformationRowListBox(deviceMetric: List<DeviceMetrics>?, index: Int) {
    // Metrics information
    val sortedValues = deviceMetric?.sortedBy { it.field.name }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 12.dp)
            .height(30.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icons for specific metrics
        val iconMap = mapOf(
            "Temperature" to R.drawable.thermostat,
            "currPower" to R.drawable.energy,           //not existing in database
            "Material In" to R.drawable.input
        )

        if (deviceMetric != null && index >= 0 && sortedValues != null) {
            for ((fieldName, iconResourceId) in iconMap) {
                val value = sortedValues.find { it.field.name == fieldName }

                if (value != null) {
                    // Display Icon and Text
                    Icon(
                        painter = painterResource(id = iconResourceId),
                        contentDescription = null,
                        tint = when (fieldName) {
                            "Temperature" -> {
                                getTemperatureColorText(value.value)
                            }
                            "Material In" -> {
                                getMaterialInColorText(value.value)
                            }
                            else -> {
                                ComplementaryGreen
                            }
                        },
                        modifier = Modifier.padding(0.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "${"%.2f".format(value.value)} " + value.field.unit.symbol,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(0.dp),
                        color =  when (fieldName) {
                            "Temperature" -> {
                                getTemperatureColorText(value.value)
                            }
                            "Material In" -> {
                                getMaterialInColorText(value.value)
                            }
                            else -> {
                                ComplementaryGreen
                            }
                        },
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }
        }
    }
}