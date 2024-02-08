package com.example.mago.Components.Dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.mago.Components.Style.BottomSheet
import com.example.mago.Components.Style.ComposterState
import com.example.mago.Components.Utils.convertIsoToDate
import com.example.mago.Data.Device
import com.example.mago.Data.IdStateComposter
import com.example.mago.R
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.ui.theme.Black
import com.example.mago.ui.theme.ComplementaryGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxDashboardAdmin(device: Device, deviceViewModel: DeviceViewModel){

    // Define the sheet state
    val sheetStateLog = rememberModalBottomSheetState()

    // Mutable state to control the visibility of the bottom sheet
    var showBottomSheetLog by remember { mutableStateOf(false) }

    val notifications by deviceViewModel.notifications.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .height(130.dp)
            .background(
                ComplementaryGrey,
                shape = RoundedCornerShape(
                    topStart = 25.dp,
                    topEnd = 25.dp,
                    bottomStart = 25.dp,
                    bottomEnd = 25.dp,
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.8f)
                    .padding(start = 0.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally


            ) {
                Text(
                    text = device.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = Black
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                            append(stringResource(id = R.string.showDeviceLogs))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, start = 24.dp)
                        .clickable { showBottomSheetLog = true },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Black
                )

                Text(
                    text = stringResource(id = R.string.lastUpdate)  + convertIsoToDate(device.updatedAt),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, start = 24.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = Black
                )

            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.2f)
                    .padding(end = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //var idStateComposterList by remember { mutableStateOf(emptyList<IdStateComposter>()) }

                val idStateComposterList by deviceViewModel.idComposterListState.collectAsState()
                val on = idStateComposterList.find { it.id == device.id }

                if (on != null) {
                    ComposterState(device = device, deviceViewModel = deviceViewModel)
                }else{
                    ComposterState(device = device, deviceViewModel = deviceViewModel)
                }

            }
        }
    }
    if (showBottomSheetLog) {
        val deviceNotifications = notifications!![device.id.toString()]
        BottomSheet(
            sheetStateLog,
            deviceNotifications,
            onDismiss = {
                showBottomSheetLog = false
            }
        )
    }
}