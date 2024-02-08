package com.example.mago.Data


data class DeviceMetrics(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val value: Double,
    val field: Field,
    val loggable: LogCollectionType?
)





