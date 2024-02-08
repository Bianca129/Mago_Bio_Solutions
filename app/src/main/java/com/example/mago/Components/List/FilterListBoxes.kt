package com.example.mago.Components.List

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mago.ui.theme.Grey

@Composable
fun FilterListBoxes(selectedFilter: String?, onFilterSelected: (String?) -> Unit) {
    val filters = listOf("Active", "Error", "Inactive", "Success", "Warning")

    LazyRow(
        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
        content = {
            items(filters.size) { filter ->
                val isSelected = selectedFilter == filters[filter]

                Button(
                    onClick = {
                        onFilterSelected(if (isSelected) null else filters[filter])
                    },
                    modifier = Modifier
                        .padding(start = 0.dp, end = 5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Grey,
                        contentColor = if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onTertiary,
                    )
                ) {
                    Text(text = filters[filter])
                }
            }
        }
    )
}
