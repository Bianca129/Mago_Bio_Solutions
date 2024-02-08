package com.example.mago.Components.Dashboard

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mago.ui.theme.ComplementaryGrey
import com.example.mago.ui.theme.StatusGreen
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldDashboard(label: String, onValueChange: (String) -> Unit, value: String) {
    // OutlinedTextField with customized properties
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(start = 16.dp, end = 16.dp),
        readOnly = false,
        value = value,
        onValueChange = {
            // Update the value when it changes
            onValueChange(it)
        },
        // Display label when there is no placeholder text
        placeholder = {
            if (value.isEmpty()) {
                Text(
                    text = label.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                    },
                    color = Color.Black
                )
            }
        },
        // Display the main label
        label = { Text(label) },
        // Customize colors for focused and unfocused states
        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (value.isNotEmpty()) StatusGreen else ComplementaryGrey,
            unfocusedBorderColor = if (value.isNotEmpty()) StatusGreen else ComplementaryGrey,
            focusedLabelColor = MaterialTheme.colorScheme.onBackground,
            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
        ),
    )
}
