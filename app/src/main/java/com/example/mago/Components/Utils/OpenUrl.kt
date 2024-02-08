package com.example.mago.Components.Utils

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent

fun openUrl(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, android.net.Uri.parse(url))
}