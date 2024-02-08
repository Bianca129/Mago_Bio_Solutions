package com.example.mago.Components.Style

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun Switch() {
    // State to track the checked status of the Switch
    var checked by remember { mutableStateOf(true) }

    // Switch composable with custom colors
    Switch(
        checked = checked,
        onCheckedChange = {
            // Update the checked state when the Switch is toggled
            checked = it
        },
        // Customize colors for the Switch in both checked and unchecked states
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.onTertiary,
            checkedTrackColor = MaterialTheme.colorScheme.tertiary,
            checkedBorderColor = MaterialTheme.colorScheme.tertiary,
            uncheckedThumbColor = MaterialTheme.colorScheme.onTertiaryContainer,
            uncheckedTrackColor = MaterialTheme.colorScheme.tertiaryContainer,
            uncheckedBorderColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    )
}
