package com.example.mago.Components.Style

import com.example.mago.ui.theme.ComplementaryOlive
import com.example.mago.ui.theme.ComplementaryRed

enum class Color {
    MagoGreen,
    StatusYellow,
    ComplementaryRed
}

fun getOverallColor(temperature: Double, value: Double): Color {
    val temperatureColor = getTemperatureColorText(temperature)
    val materialInColor = getMaterialInColorText(value)

    return when {
        temperatureColor == ComplementaryRed || materialInColor == ComplementaryRed -> Color.ComplementaryRed
        temperatureColor == ComplementaryOlive || materialInColor == ComplementaryOlive -> Color.StatusYellow
        else -> Color.MagoGreen
    }
}
