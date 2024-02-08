package com.example.mago.Components.Utils

import com.example.mago.Data.Months
import com.example.mago.Data.Period
import com.example.mago.Data.Weeks
import com.example.mago.Data.Years
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun getDateWithOffset(offset: Period): List<String> {
    val today = LocalDate.now()
    val dateWithOffset = when (offset) {
        is Weeks -> today.minus(offset.value, ChronoUnit.WEEKS)
        is Months -> today.minus(offset.value, ChronoUnit.MONTHS)
        is Years -> today.minus(offset.value, ChronoUnit.YEARS)
    }
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val todayFormatted = today.format(formatter)
    val dateWithOffsetFormatted = dateWithOffset.format(formatter)
    return listOf(todayFormatted, dateWithOffsetFormatted)
}