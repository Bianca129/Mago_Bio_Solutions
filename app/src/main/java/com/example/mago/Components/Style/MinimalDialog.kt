package com.example.mago.Components.Style

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewModelScope
import com.example.mago.Components.Dashboard.TextFieldDashboard
import com.example.mago.Components.Utils.SharedPreferencesManager
import com.example.mago.Components.Utils.formatLabel
import com.example.mago.Data.Device
import com.example.mago.Data.DeviceSettings
import com.example.mago.Data.PostSetting
import com.example.mago.Data.SettingPost
import com.example.mago.R
import com.example.mago.Viewmodel.DeviceViewModel
import kotlinx.coroutines.launch

@Composable
fun MinimalDialog(
    device: Device,
    deviceViewModel: DeviceViewModel,
    settings: DeviceSettings,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.Top,
            ) {

                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Left Text
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .weight(0.5f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            stringResource(id = R.string.changeValue),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    // Spacer
                    Spacer(modifier = Modifier.weight(0.3f))

                    // Right Close X Icon
                    Column(
                        modifier = Modifier.weight(0.2f),
                        horizontalAlignment = Alignment.End,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    onDismissRequest()
                                }

                                .padding(8.dp),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                var newValue by remember { mutableStateOf(settings.value.toString()) }

                // Textfield for Settings
                TextFieldDashboard(
                    label = formatLabel(settings.setting.name),
                    value = newValue,
                    onValueChange = {
                        // Update the `newValue` variable as the user types
                        newValue = it
                    },
                )


                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically){
                    CustomButton(
                        text = stringResource(id = R.string.change),
                        borderColor = MaterialTheme.colorScheme.tertiary,
                        fillColor = Color.Transparent,
                        enabled = newValue !="",
                        textColor = MaterialTheme.colorScheme.tertiary) {
                        //postDeviceSettings

                        val deviceId = device.id
                        val userId = SharedPreferencesManager.getUserId()


                        deviceViewModel.viewModelScope.launch {
                            try {
                                deviceViewModel.postDeviceSettings(
                                    PostSetting(
                                        value = newValue.toDoubleOrNull(),
                                        setting = SettingPost(name = settings.setting.name, defaultValue = settings.setting.defaultValue, unitId = settings.setting.unit.id),
                                        deviceId = deviceId,
                                        userId = userId
                                    ), context
                                )


                                val (success, errorMessage) = deviceViewModel.postDeviceSettingResult

                                if (success) {
                                    deviceViewModel.resetpostDeviceSettingResult()
                                    deviceViewModel.getDeviceSettings(device.id)
                                } else {
                                    Toast.makeText(context, context.getString(R.string.errorCreatingDevice) + " " + errorMessage, Toast.LENGTH_SHORT).show()
                                }

                            }catch (e: Exception) {
                                Toast.makeText(context, context.getString(R.string.errorCreatingDevice) + " " + e, Toast.LENGTH_SHORT).show()

                            }
                        }


                    }
                }


            }
        }
    }
}


