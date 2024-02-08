@file:Suppress("DEPRECATION")

package com.example.mago.Components.Dashboard

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.Data.Device
import com.example.mago.R
import com.example.mago.Viewmodel.DeviceViewModel

@Composable
fun DashboardTabs(deviceViewModel: DeviceViewModel, device: Device, navController: NavController){
    var selectedTabIndex by remember { mutableStateOf(0) }

    TabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = selectedTabIndex,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.tertiary,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    ) {
        Tab(
            selected = selectedTabIndex == 0,
            onClick = { selectedTabIndex = 0 },
            text = { Text(text= stringResource(id = R.string.metrics),
                style= MaterialTheme.typography.titleLarge)
            }
        )
        Tab(
            selected = selectedTabIndex == 1,
            onClick = { selectedTabIndex = 1 },
            text = { Text(text= stringResource(id = R.string.settings),
                style= MaterialTheme.typography.titleLarge)
            }
        )

    }

    when (selectedTabIndex) {
        0 -> {
            //Metrics
            Spacer(modifier = Modifier.height(16.dp))
            ShowMetrics(deviceViewModel, device, navController)
        }
        1 -> {
            //Settings
            Spacer(modifier = Modifier.height(16.dp))
            ShowSettings(deviceViewModel, device)
        }
    }
}