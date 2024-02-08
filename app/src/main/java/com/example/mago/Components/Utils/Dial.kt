package com.example.mago.Components.Utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.dial(phone: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // Handle scenario where the device does not support phone calls
        showToast("Cannot make a phone call on this device.")

    } catch (t: Throwable) {
        // Handle other potential exceptions
        showToast("An unexpected error occurred. Please try again.")
    }
}

