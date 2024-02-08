package com.example.mago.Data


data class Field(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val name: String,
    val unit: UnitField,
    val deviceType: DeviceType,
    val loggable: Boolean
)

