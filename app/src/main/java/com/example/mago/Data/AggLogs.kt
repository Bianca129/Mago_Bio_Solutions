package com.example.mago.Data

data class AggLogs(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val averageValue: Double,
    val minValue: Double,
    val maxValue: Double,
    val device: Device,
    val field: Field,
    val referenceDate: String
)