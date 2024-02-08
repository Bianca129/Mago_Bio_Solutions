package com.example.mago.Components.Utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

//put date in another format to show it nice
fun convertIsoToDate(isoDate: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        val date = inputFormat.parse(isoDate)

        val outputFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
        outputFormat.format(date!!)
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}