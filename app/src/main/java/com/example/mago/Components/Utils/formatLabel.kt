package com.example.mago.Components.Utils

import java.util.Locale

// Function to format label
fun formatLabel(name: String): String {
    return name.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
    }
}