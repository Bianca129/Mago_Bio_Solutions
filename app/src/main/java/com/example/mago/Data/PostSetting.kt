package com.example.mago.Data

data class PostSetting(
    val value: Double?,
    val setting: SettingPost,
    val deviceId: Int,
    val userId: String
)

data class SettingPost(
    val name : String,
    val defaultValue: Int,
    val unitId : Int
)