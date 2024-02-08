package com.example.mago.Components.Style

import androidx.compose.ui.graphics.Color
import com.example.mago.ui.theme.ComplementaryGreen
import com.example.mago.ui.theme.ComplementaryLightRed
import com.example.mago.ui.theme.ComplementaryOlive
import com.example.mago.ui.theme.ComplementaryRed
import com.example.mago.ui.theme.MagoGreen
import com.example.mago.ui.theme.StatusYellow



fun getMaterialInColorText(value: Double): Color {
    return when (value) {
        in 0.0..70.0 -> ComplementaryGreen
        in 70.0..95.0 -> ComplementaryOlive
        else -> ComplementaryRed
    }
}

fun getMaterialInColorContainer(value: Double): Color {
    return when (value) {
        in 0.0..70.0 -> MagoGreen
        in 70.0..95.0 -> StatusYellow
        else -> ComplementaryLightRed
    }
}


