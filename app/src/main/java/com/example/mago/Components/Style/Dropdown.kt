package com.example.mago.Components.Style

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.mago.R
import com.example.mago.ui.theme.ComplementaryGrey
import com.example.mago.ui.theme.StatusGreen



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownMenuAdmin(
    options: List<T>,
    label: String,
    placeholder: String,
    onItemSelected: (T) -> Unit,
    selectedOption: T?
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = selectedOption?.toString() ?: placeholder,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = if (expanded) R.drawable.arrow_up else R.drawable.arrow_down),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            expanded = !expanded
                        }
                )
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (selectedOption != null) StatusGreen else ComplementaryGrey,
                unfocusedBorderColor = if (selectedOption != null) StatusGreen else ComplementaryGrey,
                focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
            )
        )

        // Set a higher zIndex for DropdownMenu and apply an offset
        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .border(
                    0.5.dp,
                    if (selectedOption != null) StatusGreen else ComplementaryGrey,
                    RoundedCornerShape(8.dp)
                )
                .zIndex(1f) // Set a higher zIndex
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(option)
                        expanded = false
                    },
                    text = { Text(option.toString()) },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )

                // Add Divider if not the last element in the list
                if (option != options.last()) {
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                }
            }
        }
    }
}