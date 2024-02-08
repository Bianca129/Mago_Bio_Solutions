package com.example.mago.Data

data class Device(
    val name: String,
    val deviceType: DeviceType,
    val sendSettingsAtConn: Boolean,
    val sendSettingsNow: Boolean,
    val authId: String,
    val id: Int,
    val createdAt: String,
    val updatedAt: String
)



