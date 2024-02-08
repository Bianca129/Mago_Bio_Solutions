package com.example.mago.Repositories

import CreateDevice
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import com.example.mago.Data.AggLogs
import com.example.mago.Data.AssignUserToDevice
import com.example.mago.Data.Device
import com.example.mago.Data.DeviceFetchException
import com.example.mago.Data.DeviceMetrics
import com.example.mago.Data.DeviceSettings
import com.example.mago.Data.DeviceType
import com.example.mago.Data.DeviceUser
import com.example.mago.Data.Notification
import com.example.mago.Data.PostSetting
import com.example.mago.Data.Remote.ApiClient
import com.example.mago.Data.Remote.ApiService
import com.example.mago.R
import retrofit2.HttpException


@Suppress("UNREACHABLE_CODE")
class DeviceRepository(private val apiService: ApiService) {

    suspend fun getDevices(): List<Device> {

        return try {
            apiService.getDevices()
        } catch (e: Exception) {
            throw DeviceFetchException("Error fetching devices", e)
            emptyList()
        }
    }


    suspend fun getDevicesForUsers(userId: String): List<DeviceUser> {
        return try {
            apiService.getDevicesForUsers(userId)
        } catch (e: Exception) {
            throw DeviceFetchException("Error fetching devices for user", e)
            emptyList()
        }
    }

    //getDeviceTypes
    suspend fun getDeviceTypes(): List<DeviceType> {
        return try {
            apiService.getDeviceTypes()
        } catch (e: Exception) {
            throw DeviceFetchException("Error fetching device types", e)
            emptyList()
        }
    }


    suspend fun getDeviceSettings(deviceId: Int): List<DeviceSettings> {
        return try {
            val settings = apiService.getDeviceSettings(deviceId)
            settings
        } catch (e: HttpException) {
            when (e.code()) {
                404 -> {
                    // Return an empty list or another indicator that no settings were found
                    emptyList()
                }
                else -> {
                    throw DeviceFetchException("Error fetching device settings for ID $deviceId", e)
                }
            }
        } catch (e: Exception) {
            throw DeviceFetchException("Error fetching device settings for ID $deviceId", e)
        }
    }




    suspend fun postDeviceSettings(postSetting: PostSetting, context: Context): Pair<Boolean, String?> {
      try {
          val response = apiService.postDeviceSettings(postSetting)

          return if (response.isSuccessful) {
              // is successful, return success status and null for the error message
              Toast.makeText(context, context.getString(R.string.successFetchingDevice), Toast.LENGTH_LONG).show()
              Pair(true, null)
          } else {
              //failed, return success status as false and the error message
              val errorMessage = response.errorBody()?.string() ?: "Unknown error"
              Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
              Pair(false, errorMessage)
          }
      } catch (e: Exception) {
          // Handle other exceptions if needed
          return Pair(false, context.getString(R.string.unexpectedError))
      }
  }



    suspend fun getAggLogs(timeSelector: String, deviceId: Int, fieldId: Int, startDate:String, endDate: String ): List<AggLogs> {
        return try {
            val response = ApiClient.metricsApiService.getAggLogs(timeSelector, deviceId, fieldId, startDate, endDate)
            response.ifEmpty {
                // Instead of throwing an exception, return an empty list
                emptyList()
            }
        } catch (e: Exception) {
            throw e
        }
    }


    suspend fun getDeviceMetrics(deviceId: Int): List<DeviceMetrics> {
        return try {
            val response = ApiClient.metricsApiService.getDeviceMetrics(deviceId)
            response.ifEmpty {
                // Instead of throwing an exception, return an empty list
                emptyList()
            }
        } catch (e: Exception) {
            throw e
        }
    }



    suspend fun getNotification(deviceId: Int): List<Notification> {
        return try {
            val response = ApiClient.notificationsApiService.getNotification(deviceId)
            response
        } catch (e: Exception) {
            listOf(Notification(id = -1, timeStamp = "", statusTypeId = -1, message = "", deviceID = -1))
        }
    }

    suspend fun createDevice(name: String, deviceTypeId: Int , sendSettingsAtConn: Boolean, sendSettingsNow: Boolean, authId: String, password: String, context: Context): Pair<Boolean, String?> {
        try {
            val request = CreateDevice(name, deviceTypeId, sendSettingsAtConn, sendSettingsNow, authId, password)
            val response = apiService.createDevice(request)

            return if (response.isSuccessful) {
                // User creation is successful, return success status and null for the error message
                Toast.makeText(context, context.getString(R.string.successCreatingDevice), Toast.LENGTH_LONG).show()
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


    suspend fun assignUserToDevice(userId: String, deviceId: Int, context: Context): Pair<Boolean, String?> {
        return try {
            val request = AssignUserToDevice(userId, deviceId)
            val response = apiService.assignUserToDevice(request)

            if (response.isSuccessful) {
                // User creation is successful, return success status and null for the error message
                Toast.makeText(context, context.getString(R.string.successAssignUser), Toast.LENGTH_LONG).show()
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


