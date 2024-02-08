package com.example.mago.Components.Dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mago.Components.Style.Switch
import com.example.mago.Data.DeviceSettings
import com.example.mago.R
import com.example.mago.ui.theme.ComplementaryGrey
import java.util.Locale

@Composable
fun ItemSettingDashboard(setting: DeviceSettings, arrow: Boolean = false, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Text left
        Column(
            modifier = Modifier
                .weight(0.7f)
                .padding(start = 5.dp)
        ) {
            Text(
                text = setting.setting.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()) else it.toString() },
                style = MaterialTheme.typography.titleMedium
            )
        }

        Column(
            modifier = Modifier
                .weight(0.25f)
                .padding(start = 5.dp)
        ) {
            Text(
                text = setting.value.toString(),
                style = MaterialTheme.typography.titleSmall,
                color = ComplementaryGrey
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .weight(0.05f)
                .padding(start = 5.dp)
        ) {
            if (arrow) {
                Icon(
                    painter = painterResource(id = R.drawable.forward),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Switch()
            }

        }
    }

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
    )
}