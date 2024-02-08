package com.example.mago.Components.Dashboard

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimeSelectorDiagram(text: String, isSelected: Boolean, onClick: () -> Unit) {
    // Button with customized properties
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(start = 0.dp, end = 0.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            // Customize container color based on selection state
            containerColor = if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.tertiaryContainer,
            // Customize content color based on selection state
            contentColor = if (isSelected) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onTertiaryContainer,
        )
    ) {
        // Display the text inside the button
        Text(text = text)
    }
}
