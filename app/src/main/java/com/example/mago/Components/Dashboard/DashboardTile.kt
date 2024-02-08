package com.example.mago.Components.Dashboard

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mago.Components.Style.getMaterialInColorContainer
import com.example.mago.Components.Style.getMaterialInColorText
import com.example.mago.Components.Style.getTemperatureColorContainer
import com.example.mago.Components.Style.getTemperatureColorText
import com.example.mago.ui.theme.ComplementaryGreen

@Composable
fun DashboardTile(
    metric: String,
    clickedMetric: String,
    icon: Int,
    value: String,
    modifier: Modifier = Modifier,
    onClick: (String, String) -> Unit // Updated to include both clicked metric and title information
) {
    val stringValue = value.split(" ").first()
    val doubleValue = stringValue.replace(",", ".").toDouble()

    val containerColor = when (metric) {
        "Temperature" -> getTemperatureColorContainer(doubleValue)
        "Material In" -> getMaterialInColorContainer(doubleValue)
        else -> ComplementaryGreen
    }

    val contentColor = when (metric) {
        "Temperature" -> getTemperatureColorText(doubleValue)
        "Material In" -> getMaterialInColorText(doubleValue)
        else -> ComplementaryGreen
    }

    Card(
        modifier = modifier
            .fillMaxWidth(1f)
            .height(105.dp)
            .clickable { onClick(clickedMetric, metric) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor,
        )

    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = metric,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(0.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}