package com.example.mago.Components.Style

import com.example.mago.R

fun getDrawableId(fieldName: String?): Int {
    // Implement logic to get the appropriate drawable ID based on the field name
    return when (fieldName) {
        "Temperature" -> R.drawable.thermostat
        "totEnergyUsed" -> R.drawable.energy
        "currPower" -> R.drawable.power
        "Material In" -> R.drawable.input
        "totMassOut" -> R.drawable.output
        else -> R.drawable.baseline_question_mark_24
    }
}