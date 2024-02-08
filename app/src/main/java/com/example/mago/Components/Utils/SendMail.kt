package com.example.mago.Components.Utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent

fun Context.sendMail(to: String, subject: String, body: String) {
    try {
        // Create an Intent with the ACTION_SEND action to send an email
        val intent = Intent(Intent.ACTION_SEND)

        // Set the MIME type for the email, can use "vnd.android.cursor.item/email" or "message/rfc822"
        intent.type = "vnd.android.cursor.item/email"

        // Set the recipient email address
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))

        // Set the subject of the email
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)

        // Set the body of the email
        intent.putExtra(Intent.EXTRA_TEXT, body) // Body is added here

        // Attach an empty ByteArray as a workaround to transmit the body text
        val byteArray = ByteArray(0)
        intent.putExtra(Intent.EXTRA_STREAM, byteArray)

        // Start the email activity
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // Handle the case where no email app is available
        showToast("No email app found on this device.")
    } catch (t: Throwable) {
        // Handle potential other types of exceptions
        showToast("An unexpected error occurred. Please try again.")
    }
}



