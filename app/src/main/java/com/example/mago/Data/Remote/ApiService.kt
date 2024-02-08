package com.example.mago.Data.Remote

import CreateDevice
import com.example.mago.Data.AggLogs
import com.example.mago.Data.AssignUserToDevice
import com.example.mago.Data.Device
import com.example.mago.Data.DeviceIdFile
import com.example.mago.Data.DeviceMetrics
import com.example.mago.Data.DeviceSettings
import com.example.mago.Data.DeviceType
import com.example.mago.Data.DeviceUser
import com.example.mago.Data.FirmwareUpdates
import com.example.mago.Data.GetUserData
import com.example.mago.Data.GetUserObject
import com.example.mago.Data.Notification
import com.example.mago.Data.PostSetting
import com.example.mago.Data.UpdateUser
import com.example.mago.Data.User
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //admin get devices
    @GET("deviceMS/Device")
    suspend fun getDevices(
    ): List<Device>

    //user get devices
    @GET("deviceMS/UsersOnDevices/{userId}")
    suspend fun getDevicesForUsers(
        @Path("userId") userId : String,
    ): List<DeviceUser>

    @GET("deviceMS/DeviceType")
    suspend fun getDeviceTypes(
    ): List<DeviceType>

    @GET("deviceMS/DeviceSettings/{deviceId}")
    suspend fun getDeviceSettings(
        @Path("deviceId") deviceId: Int,
    ): List<DeviceSettings>

    @POST("deviceMS/DeviceSettings")
    suspend fun postDeviceSettings(
        @Body request: PostSetting
    ): Response<Void>


    @GET("orchestrate/device-metrics/Metrics/{id}")
    suspend fun getDeviceMetrics(
        @Path("id") deviceId: Int,
    ): List<DeviceMetrics>

    @GET("orchestrator/notification/device/{id}")
    suspend fun getNotification(
        @Path("id")deviceId: Int,
    ): List<Notification>

    @GET("users")
    suspend fun getUsers(
    ): List<GetUserObject>

    @GET("users/{id}")
    suspend fun getUsersWithId(
        @Path("id") id : String,
    ): GetUserObject


    @POST("users")
    suspend fun createUser(
        @Body request: User
    ): Response<Void>

    @GET("users/{id}")
    suspend fun getUserData(
        @Path("id") id : String,
    ): GetUserData


    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id : String,
        @Body request: UpdateUser
    ): Response<Void>


    @POST("deviceMS/Device")
    suspend fun createDevice(
        @Body request: CreateDevice,
    ): Response<Void>

    @POST("orchestrator/device/user-on-device")
    suspend fun assignUserToDevice(
        @Body request: AssignUserToDevice,
    ): Response<Void>


    @GET("orchestrate/aggregated-logs/AggregatedLogs/{aggregatedLogDateType}/{deviceId}/{fieldId}")
    suspend fun getAggLogs(
        @Path("aggregatedLogDateType") aggregatedLogDateType: String,
        @Path("deviceId") deviceId: Int,
        @Path("fieldId") fieldId: Int,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null

    ): List<AggLogs>

    @GET("orchestrate/device-firmware/Firmware/{deviceId}")
    suspend fun getFirmwareHistory(
        @Path("deviceId") deviceId : Int,
    ): List<FirmwareUpdates>

    @POST("orchestrate/device-firmware/Firmware")
    suspend fun updateFirmware(
        @Body request: DeviceIdFile,
    ): Response<Void>


}



object ApiClient {
    const val BASE_URL = "https://device-microservice-service-mago-backend.apps.ocp4-inholland.joran-bergfeld.com/"
    private const val BASE_METRICS_URL = "https://device-metrics-orchestrator-service-mago-backend.apps.ocp4-inholland.joran-bergfeld.com/"
    private const val BASE_NOTIFICATIONS_URL = "https://user-device-noti-orchestrator-service-mago-backend.apps.ocp4-inholland.joran-bergfeld.com/"
    private const val BASE_USER_URL = "https://user-microservice-service-mago-backend.apps.ocp4-inholland.joran-bergfeld.com/"
    private const val BASE_DEVICE_METRICS_ORCHESTRATOR = "https://user-device-noti-orchestrator-service-mago-backend.apps.ocp4-inholland.joran-bergfeld.com/"
    private const val BASE_FIRMWARE_URL = "https://device-firmware-orchestrator-service-mago-backend.apps.ocp4-inholland.joran-bergfeld.com/"


    // Variable to store the token
    private var token: String? = null

    //Baseclient
    private var baseClient = OkHttpClient.Builder()
        .hostnameVerifier { _, _ -> true }
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }
        .build()



    // API-Service for BASE_URL
    private var _devicesApiService: ApiService? = null
    val devicesApiService: ApiService
        get() {
            if (_devicesApiService == null) {
                _devicesApiService = createApiService(BASE_URL, baseClient)
            }
            return _devicesApiService!!
        }

    // API-Service for BASE_URL
    private var _firmwareApiService: ApiService? = null
    val firmwareApiService: ApiService
        get() {
            if (_firmwareApiService == null) {
                _firmwareApiService = createApiService(BASE_FIRMWARE_URL, baseClient)
            }
            return _firmwareApiService!!
        }

    // API-Service for BASE_URL
    private var _orchestrator: ApiService? = null
    val orchestratorApiService: ApiService
        get() {
            if (_orchestrator == null) {
                _orchestrator = createApiService(BASE_DEVICE_METRICS_ORCHESTRATOR, baseClient)
            }
            return _orchestrator!!
        }

    // API-Service for BASE_METRICS_URL
    private var _metricsApiService: ApiService? = null
    val metricsApiService: ApiService
        get() {
            if (_metricsApiService == null) {
                _metricsApiService = createApiService(BASE_METRICS_URL, baseClient)
            }
            return _metricsApiService!!
        }

    private var _notificationApiService: ApiService? = null
    val notificationsApiService: ApiService
        get() {
            if (_notificationApiService == null) {
                _notificationApiService = createApiService(BASE_NOTIFICATIONS_URL, baseClient)
            }
            return _notificationApiService!!
        }


    private var _userApiService: ApiService? = null
    val userApiService: ApiService
        get() {
            if (_userApiService == null) {
                _userApiService = createApiService(BASE_USER_URL, baseClient)
            }
            return _userApiService!!
        }





    // set Bearer Token
    // ApiClient
    fun setBearerToken(newToken: String) {
        // Update the token variable
        token = newToken

        // Füge den Token zum Header des baseClients hinzu
        baseClient = baseClient.newBuilder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }


    private fun createApiService(baseUrl: String, client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}

