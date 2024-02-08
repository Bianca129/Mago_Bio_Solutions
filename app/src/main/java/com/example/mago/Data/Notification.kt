package com.example.mago.Data

data class Notification(
    val id: Int,
    val timeStamp: String,
    val statusTypeId: Int,
    val message: String,
    val deviceID: Int,
)

