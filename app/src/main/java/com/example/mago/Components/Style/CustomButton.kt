package com.example.mago.Components.Style

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CustomButton(text: String, borderColor: Color, fillColor: Color, enabled: Boolean, textColor: Color, onClick: () -> Unit) {
    Button(
        onClick = {
            if (enabled) {
                onClick()
            }
        },
        enabled = enabled,
        modifier = Modifier
            .background(
                color = fillColor,
                shape = RoundedCornerShape(100.dp)
            )
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(100.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = fillColor,
            contentColor = textColor
        )
    ) {
        Text(text = text, color = textColor)
    }
}