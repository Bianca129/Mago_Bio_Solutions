package com.example.mago.Repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.mago.Data.DeviceIdFile
import com.example.mago.Data.FirmwareUpdates
import com.example.mago.Data.Remote.ApiService
import com.example.mago.R

class FirmwareRepository(private val apiService: ApiService) {

    suspend fun getFirmwareHistory(deviceId: Int): List<FirmwareUpdates>{
        return try {
            val response = apiService.getFirmwareHistory(deviceId)
            response
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun updateFirmware(deviceId: Int, file: String, context: Context): Pair<Boolean, String?> {
        return try {
            val request = DeviceIdFile(deviceId, file)
            val response = apiService.updateFirmware(request)

            if (response.isSuccessful) {
                // User creation is successful, return success status and null for the error message
                Toast.makeText(context, context.getString(R.string.successFirmwareUpdate), Toast.LENGTH_LONG).show()
                Pair(true, null)
            } else {
                // User creation failed, return success status as false and the error message
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                Pair(false, errorMessage)
            }
        } catch (e: Exception) {
            return Pair(false, context.getString(R.string.unexpectedError))

        }
    }

}
