package com.example.mago.Components.Utils

import com.example.mago.Data.Weeks
import com.example.mago.Viewmodel.DeviceViewModel

// Separate function for fetching data if required
fun fetchDataIfRequired(deviceViewModel: DeviceViewModel, fieldId: Int, deviceId: String) {
    if (fieldId != 0) {
        val dates = getDateWithOffset(Weeks(11))
        deviceViewModel.getAggLogs("Weekly", deviceId.toInt(), 1, dates[1], dates[0])
    }
}