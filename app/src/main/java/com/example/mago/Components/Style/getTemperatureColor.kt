package com.example.mago.Components.Style

import androidx.compose.ui.graphics.Color
import com.example.mago.ui.theme.ComplementaryGreen
import com.example.mago.ui.theme.ComplementaryLightRed
import com.example.mago.ui.theme.ComplementaryOlive
import com.example.mago.ui.theme.ComplementaryRed
import com.example.mago.ui.theme.MagoGreen
import com.example.mago.ui.theme.StatusYellow

fun getTemperatureColorText(temperature: Double): Color {
    return when (temperature) {
        in 30.0..60.0 -> ComplementaryGreen
        in 10.0..30.0, in 60.0..70.0 -> ComplementaryOlive
        0.0 -> ComplementaryGreen
        else -> ComplementaryRed
    }
}

fun getTemperatureColorContainer(temperature: Double): Color {
    return when (temperature) {
        in 30.0..60.0 -> MagoGreen
        in 10.0..30.0, in 60.0..70.0 -> StatusYellow
        0.0 -> MagoGreen
        else -> ComplementaryLightRed
    }
}

