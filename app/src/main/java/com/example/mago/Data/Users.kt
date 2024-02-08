package com.example.mago.Data

data class Users(
    val user_id: String,
    val family_name: String,
    val given_name: String,
    val name: String,
    val email: String,
    val email_verified: Boolean,
    val blocked: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val picture: String
)