package com.example.mago.Data

data class UnitField(
    val id : Int,
    val createdAt: String,
    val updatedAt: String,
    val name: String,
    val symbol: String,
    val factor: Int,
    val offset: Int
)