package com.example.mago.Components.Dashboard

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.Components.Style.BottomSheetContent
import com.example.mago.Components.Style.CustomButton
import com.example.mago.Components.Utils.convertIsoToDate
import com.example.mago.Components.Utils.dial
import com.example.mago.Data.Device
import com.example.mago.R
import com.example.mago.Screens.Screens
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.Viewmodel.UserViewModel
import com.example.mago.ui.theme.ComplementaryGrey
import com.example.mago.ui.theme.ComplementaryLightRed
import com.example.mago.ui.theme.ComplementaryOlive
import com.example.mago.ui.theme.Grey
import com.example.mago.ui.theme.MagoGreen
import com.example.mago.ui.theme.StatusRed
import com.example.mago.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionsRow(
    navController: NavController,
    device: Device,
    userViewModel: UserViewModel,
    context: Context,
    deviceViewModel: DeviceViewModel
) {

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val idColorList = deviceViewModel.idColorList
    val backgroundButton = idColorList.find { it.id == device.id }

    val backgroundWarning = MaterialTheme.colorScheme.onError
    val backgroundError = ComplementaryLightRed

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(start = 0.dp),
            horizontalAlignment = Alignment.Start
        ) {
            when {
                // Display different action for admin and non-admin users
                userViewModel.isAdmin.value -> {


                    CustomButton(
                        text = stringResource(id = R.string.assignedUser),
                        borderColor = MaterialTheme.colorScheme.tertiary,
                        fillColor = Color.Transparent,
                        enabled = true,
                        textColor = MaterialTheme.colorScheme.tertiary
                    ) {
                        //navController.navigate("AssignUser")
                        showBottomSheet = true
                    }
                }
                else -> {
                    Text(stringResource(id = R.string.lastUpdate) +  "\n"+  convertIsoToDate(device.updatedAt))
                }
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                when {
                    // Display different action for admin and non-admin users
                    userViewModel.isAdmin.value -> {
                        Column(
                            modifier = Modifier
                                .padding(0.dp)
                                .clickable {
                                    //navController.navigate("FirmwareUpdate/${device.id}")
                                    navController.navigate("${Screens.FirmwareUpdate.route}/${device.id}") {
                                        launchSingleTop = true
                                        restoreState = true
                                        deviceViewModel.updateTitle("Firmware Update", false)
                                    }
                                }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.update),
                                contentDescription = null,
                                tint = Grey,
                                modifier = Modifier.padding(0.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(color = Grey)) {
                                        append(stringResource(id = R.string.update))
                                    }
                                    append("\n")
                                    withStyle(style = SpanStyle(color = Grey)) {
                                        append(stringResource(id = R.string.firmware))
                                    }
                                },
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(0.dp),
                                softWrap = true
                            )
                        }
                    }
                    else -> {

                            CustomButton(
                                text = stringResource(id = R.string.notifyMago),
                                borderColor = when (backgroundButton?.color) {
                                    com.example.mago.Components.Style.Color.MagoGreen -> ComplementaryGrey
                                    com.example.mago.Components.Style.Color.StatusYellow -> backgroundWarning
                                    com.example.mago.Components.Style.Color.ComplementaryRed -> backgroundError
                                    else -> {
                                        ComplementaryGrey
                                    }
                                },
                                fillColor = when (backgroundButton?.color) {
                                    com.example.mago.Components.Style.Color.MagoGreen -> ComplementaryGrey
                                    com.example.mago.Components.Style.Color.StatusYellow -> backgroundWarning
                                    com.example.mago.Components.Style.Color.ComplementaryRed -> backgroundError
                                    else -> {
                                        ComplementaryGrey
                                    }
                                },
                                enabled = when (backgroundButton?.color) {
                                    com.example.mago.Components.Style.Color.MagoGreen -> false
                                    com.example.mago.Components.Style.Color.StatusYellow -> true
                                    com.example.mago.Components.Style.Color.ComplementaryRed -> true
                                    else -> {
                                        false
                                    }
                                },
                                textColor = when (backgroundButton?.color) {
                                    com.example.mago.Components.Style.Color.MagoGreen -> White
                                    com.example.mago.Components.Style.Color.StatusYellow -> ComplementaryOlive
                                    com.example.mago.Components.Style.Color.ComplementaryRed -> StatusRed
                                    else -> {
                                        White
                                    }
                                }
                            ) {
                                context.dial(phone = "12345678")
                            }

                    }
                }
            }
        }
    }
    if (showBottomSheet) {
        // Display the bottom sheet using ModalBottomSheet
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet = false },
            modifier = Modifier.padding(bottom = 20.dp),
            containerColor = Grey
        ) {
            // Content of the bottom sheet
            BottomSheetContent(device, navController)
        }
    }
}