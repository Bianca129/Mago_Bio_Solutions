package com.example.mago.Components.Style

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp


@Composable
fun GreenBorderButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {

    // Button with conditional appearance
    Button(
        onClick = {
            if (enabled) {
                // Trigger the provided onClick function when enabled
                onClick()
            }
        },
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary),
        enabled = enabled,
        contentPadding = PaddingValues(16.dp), // Adjust padding as needed
        colors = ButtonDefaults.buttonColors(
            contentColor =
            if (enabled) {
                MaterialTheme.colorScheme.onTertiary // Text color for the enabled state
            } else {
                MaterialTheme.colorScheme.tertiary // Text color for the disabled state
            },
            containerColor =
            if (enabled) {
                MaterialTheme.colorScheme.tertiary // Background color for the enabled state
            } else {
                MaterialTheme.colorScheme.background // Background color for the disabled state
            },
            disabledContentColor =
            if (enabled) {
                MaterialTheme.colorScheme.background // Text color for the enabled state
            } else {
                MaterialTheme.colorScheme.tertiary // Text color for the disabled state
            },
            disabledContainerColor =
            if (enabled) {
                MaterialTheme.colorScheme.tertiary // Background color for the enabled state
            } else {
                MaterialTheme.colorScheme.background // Background color for the disabled state
            }
        )
    ) {
        Text(text = text)
    }
}
