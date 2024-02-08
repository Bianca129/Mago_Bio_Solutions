package com.example.mago.Data



data class FirmwareUpdates(
    val id : Int,
    val createdAt : String,
    val updatedAt: String,
    val updateStatus: String,
    val deviceId: Int,
    val file: String,
    val currParts: Int,
    val totParts: Int
)