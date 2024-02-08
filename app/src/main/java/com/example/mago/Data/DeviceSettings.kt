package com.example.mago.Data

data class DeviceSettings(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val value: Double?,
    val setting: Setting,
    val updateStatus: String,
    val deviceId: Int,
    val userId: String,


)

data class Setting(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val name: String,
    val defaultValue: Int,
    val unit: UnitField
)

