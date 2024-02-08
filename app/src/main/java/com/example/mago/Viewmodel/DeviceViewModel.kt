package com.example.mago.Viewmodel

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.mago.Data.AggLogs
import com.example.mago.Data.Device
import com.example.mago.Data.DeviceMetrics
import com.example.mago.Data.DeviceSettings
import com.example.mago.Data.DeviceType
import com.example.mago.Data.DeviceUser
import com.example.mago.Data.FirmwareUpdates
import com.example.mago.Data.IdColorItem
import com.example.mago.Data.IdStateComposter
import com.example.mago.Data.Notification
import com.example.mago.Data.PostSetting
import com.example.mago.Data.Remote.ApiClient
import com.example.mago.Data.Remote.ApiClient.notificationsApiService
import com.example.mago.Data.Remote.ApiService
import com.example.mago.Repositories.DeviceRepository
import com.example.mago.Repositories.FirmwareRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class DeviceViewModel(application: Application) : AndroidViewModel(application) {


    private val _accessToken = MutableLiveData<String>()
    fun setBearerToken(token: String) {
        ApiClient.setBearerToken(token)
        _accessToken.value = token

    }


    private var _title = mutableStateOf(TitleData("My devices", useDifferentFont = false))
    val title: State<TitleData> get() = _title

    data class TitleData(val text: String, val useDifferentFont: Boolean)

    fun updateTitle(newTitle: String, useDifferentFont: Boolean) {
        _title.value = TitleData(newTitle, useDifferentFont)
    }

    val selectedFilterList = mutableStateOf<String?>(null)
    val selectedFilterListAlphabetical = mutableStateOf(false)//Icon Alphabetical order --> Boolean
    val selectedFilterMetric = mutableStateOf<String?>(null)
    val selectedFilterMetricArrow = mutableStateOf<Int>(19)
    val selectedFilterDiagramMetric = mutableStateOf<String>("Weekly")

    private val devicesApiService: ApiService = ApiClient.devicesApiService
    private val deviceRepository = DeviceRepository(devicesApiService)

    private val orchestratorApiService: ApiService = ApiClient.orchestratorApiService
    private val deviceRepositoryOrchestrator = DeviceRepository(orchestratorApiService)

    private val apiServiceMetric: ApiService = ApiClient.metricsApiService
    private val deviceRepositoryMetric = DeviceRepository(apiServiceMetric)

    private val apiServiceNotification: ApiService = notificationsApiService
    private val deviceRepositoryNotifications = DeviceRepository(apiServiceNotification)

    private val _devicesAdmin = MutableLiveData<List<Device>>()
    val devicesAdmin: LiveData<List<Device>> get() = _devicesAdmin


    private val _devicesUser = MutableLiveData<List<DeviceUser>>()
    val devicesUser: LiveData<List<DeviceUser>> get() = _devicesUser


    private val _deviceMetricsMap = MutableLiveData<Map<String, List<DeviceMetrics>>>()
    val deviceMetrics: LiveData<Map<String, List<DeviceMetrics>>> get() = _deviceMetricsMap


    private val _alldevices = MutableLiveData<List<Device>>()
    val alldevices: LiveData<List<Device>> get() = _alldevices

    private val _notificationsMap = MutableLiveData<Map<String, List<Notification>>>()
    val notifications: LiveData<Map<String, List<Notification>>> get() = _notificationsMap


    private val _deviceTypes = MutableLiveData<List<DeviceType>>()
    val deviceTypesInformation: LiveData<List<DeviceType>> get() = _deviceTypes


    private val _aggLogs = MutableLiveData<List<AggLogs>>()
    val aggLogs: LiveData<List<AggLogs>> get() = _aggLogs


    private val _deviceSettings = MutableLiveData<List<DeviceSettings>>()
    val deviceSettings: LiveData<List<DeviceSettings>> get() = _deviceSettings

    //Firmware
    private val _firmwareList = MutableLiveData<Map<String, List<FirmwareUpdates>>>()
    val firmwareList: LiveData<Map<String, List<FirmwareUpdates>>> get() = _firmwareList

    var currentPage by mutableStateOf(0)
        private set

    // Funktion zum Aktualisieren von currentPage
    fun updateCurrentPage(newPage: Int) {
        currentPage = newPage
    }


    private val mainHandler = Handler(Looper.getMainLooper())
    private fun showToast(message: String) {
        mainHandler.post {
            Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleApiError(errorMessage: String) {
        showToast(errorMessage)
    }

    //private val _isLoading = MutableLiveData<Boolean>()
    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private fun updateLoadingState(isLoading: Boolean) {
        // Überprüfen Sie, ob der Code im Hauptthread ausgeführt wird
        if (Looper.myLooper() == Looper.getMainLooper()) {
            _isLoading.value = isLoading
        } else {
            // Falls im Hintergrundthread, zur Haupt-Thread-Executor wechseln
            viewModelScope.launch(Dispatchers.Main) {
                _isLoading.value = isLoading
            }
        }
    }


    private fun getDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateLoadingState(true)
                val devicesResponse = deviceRepository.getDevices()

                // Check if the coroutine is still active before updating LiveData
                if (coroutineContext[Job]?.isActive == true) {
                    _alldevices.postValue(devicesResponse)
                }
            } catch (networkException: IOException) {
                // Handle network error
                val errorMessage = "Network error fetching devices"
                handleApiError(errorMessage)
            } catch (serverException: HttpException) {
                // Handle server error (e.g., 404, 500, etc.)
                val errorMessage = "Server error fetching devices"
                handleApiError(errorMessage)
            } catch (e: Exception) {
                // Handle other errors
                val errorMessage = "Error fetching devices"
                handleApiError(errorMessage)
            } finally {
                // Ensure that loading state is updated even if there's an exception
                withContext(Dispatchers.Main) {
                    // Check if the coroutine is still active before updating the loading state
                    if (coroutineContext[Job]?.isActive == true) {
                        updateLoadingState(false)
                    }
                }
            }
        }
    }

    fun refreshDevices() {
        getDevices()
    }

    val idComposterListState = MutableStateFlow<MutableList<IdStateComposter>>(mutableListOf())
    fun getDevicesWithMetricsAndNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateLoadingState(true)

                val devicesResponse = deviceRepository.getDevices()
                if (devicesResponse.isNotEmpty()) {
                    // Process metrics, notifications, firmware, etc., only if devicesResponse is not empty
                    val metricsMap = mutableMapOf<String, List<DeviceMetrics>>()
                    val notificationMap = mutableMapOf<String, List<Notification>>()
                    val firmwareHistoryMap = mutableMapOf<String, List<FirmwareUpdates>>()
                    val deviceSettingsList = mutableListOf<DeviceSettings>()

                    for (device in devicesResponse) {
                        //As we has no state for the composter, its like that
                        val idStateComposter = IdStateComposter(device.id, true)
                        val currentList = idComposterListState.value.toMutableList()
                        currentList.add(idStateComposter)
                        idComposterListState.value = currentList
                        try {
                            val metricsResponse = deviceRepositoryMetric.getDeviceMetrics(device.id)
                            metricsMap[device.id.toString()] = metricsResponse

                            // Fetch notifications for each device
                            val notificationResponse =
                                deviceRepositoryNotifications.getNotification(device.id)
                            notificationMap[device.id.toString()] = notificationResponse

                            val firmwareHistoryResponse =
                                firmwareRepository.getFirmwareHistory(device.id)
                            firmwareHistoryMap[device.id.toString()] = firmwareHistoryResponse


                            val settingsResponse = deviceRepository.getDeviceSettings(device.id)
                            for (setting in settingsResponse) {
                                // Create your result object using the information from the response
                                val resultItem = DeviceSettings(
                                    value = setting.value,
                                    setting = setting.setting,
                                    updateStatus = setting.updateStatus,
                                    deviceId = setting.deviceId,
                                    userId = setting.userId,
                                    id = setting.id,
                                    createdAt = setting.createdAt,
                                    updatedAt = setting.updatedAt
                                )

                                deviceSettingsList.add(resultItem)
                            }
                        } catch (e: HttpException) {
                            when (e.code()) {
                                404 -> {
                                    // Handle 404 errors for resources not found
                                    if (e.message?.contains(
                                            "notification",
                                            ignoreCase = true
                                        ) == true
                                    ) {
                                        // the most devices has no notifications, that's why I didn't implement a toast for that, so thats why there is here this Log
                                        Log.d(
                                            "DeviceViewModel",
                                            "Notifications not found for device ${device.id}"
                                        )
                                    } else {
                                        handleApiError("Resource not found: ${e.message}")
                                    }
                                }

                                500 -> {
                                    // Handle 500 errors for internal server errors
                                    handleApiError("Internal server error fetching devices")
                                }

                                else -> {
                                    // Handle other HTTP errors
                                    handleApiError("HTTP error: ${e.code()}, ${e.message}")
                                }
                            }

                        } catch (e: Resources.NotFoundException) {
                            //no device settings found, I don't want currently an error Message for that, because a lot of devices currently have not everything now
                        } catch (e: IOException) {
                            // Handle network errors
                            handleApiError("Network error fetching devices with metrics and notifications")
                        } catch (e: Exception) {
                            // Handle other exceptions
                            handleApiError("General error processing device ID ${device.id}")
                        }
                    }

                    // Check if the coroutine is still active before updating LiveData
                    if (coroutineContext[Job]?.isActive == true) {
                        withContext(Dispatchers.Main) {
                            _devicesAdmin.value = devicesResponse
                            _deviceMetricsMap.value = metricsMap
                            _notificationsMap.value = notificationMap
                            _deviceSettings.value = deviceSettingsList
                            _firmwareList.value = firmwareHistoryMap
                        }
                    }
                }
            } catch (e: Exception) {
                // Check if the coroutine is still active before handling the exception
                if (coroutineContext[Job]?.isActive == true) {
                    val errorMessage = "Error fetching devices with metrics and notifications"
                    handleApiError(errorMessage)
                }
            } finally {
                // Ensure that loading state is updated even if there's an exception
                withContext(Dispatchers.Main) {
                    // Check if the coroutine is still active before updating the loading state
                    if (coroutineContext[Job]?.isActive == true) {
                        updateLoadingState(false)
                    }
                }
            }
        }
    }

    fun getDevicesWithMetricsAndNotificationsForUsers(userId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateLoadingState(true)
                val devicesResponse = deviceRepository.getDevicesForUsers("$userId")
                val metricsMap = mutableMapOf<String, List<DeviceMetrics>>()
                val notificationMap = mutableMapOf<String, List<Notification>>()
                val deviceSettingsList = mutableListOf<DeviceSettings>()

                for (device in devicesResponse) {
                    try {
                        val metricsResponse =
                            deviceRepositoryMetric.getDeviceMetrics(device.device.id)
                        metricsMap[device.device.id.toString()] = metricsResponse

                        // Fetch notifications for each device
                        val notificationResponse =
                            deviceRepositoryNotifications.getNotification(device.device.id)
                        notificationMap[device.device.id.toString()] = notificationResponse

                        // As we have no state for the composter, it's like that
                        val idStateComposter = IdStateComposter(device.device.id, true)
                        val currentList = idComposterListState.value.toMutableList()
                        currentList.add(idStateComposter)
                        idComposterListState.value = currentList

                        val settingsResponse = deviceRepository.getDeviceSettings(device.device.id)

                        for (setting in settingsResponse) {
                            // Create your result object using the information from the response
                            val resultItem = DeviceSettings(
                                value = setting.value,
                                setting = setting.setting,
                                updateStatus = setting.updateStatus,
                                deviceId = setting.deviceId,
                                userId = setting.userId,
                                id = setting.id,
                                createdAt = setting.createdAt,
                                updatedAt = setting.updatedAt
                            )

                            deviceSettingsList.add(resultItem)
                        }
                    } catch (networkException: IOException) {
                        // Handle network error
                        val errorMessage =
                            "Network error fetching devices with metrics and notifications"
                        handleApiError(errorMessage)
                    } catch (serverException: HttpException) {
                        // Handle server error (e.g., 404, 500, etc.)
                        val errorMessage =
                            "Server error fetching devices with metrics and notifications"
                        handleApiError(errorMessage)
                    } catch (notFoundException: Resources.NotFoundException) {
                        // its not implemented, because a lot of devices has no notification, metric or setting
                        val errorMessage = "Device settings not found for ID ${device.device.id}"
                        // handleApiError(errorMessage)
                    } catch (e: Exception) {
                        // Handle other errors
                        val errorMessage = "Error fetching devices with metrics and notifications"
                        handleApiError(errorMessage)
                    }
                }

                // Check if the coroutine is still active before updating LiveData
                if (coroutineContext[Job]?.isActive == true) {
                    withContext(Dispatchers.Main) {
                        _devicesUser.value = devicesResponse
                        _deviceMetricsMap.value = metricsMap
                        _notificationsMap.value = notificationMap
                        _deviceSettings.value = deviceSettingsList
                    }
                }
            } catch (e: Exception) {
                // Check if the coroutine is still active before handling the exception
                if (coroutineContext[Job]?.isActive == true) {
                    val errorMessage = "Error fetching devices with metrics and notifications"
                    handleApiError(errorMessage)
                }
            } finally {
                // Ensure that loading state is updated even if there's an exception
                withContext(Dispatchers.Main) {
                    // Check if the coroutine is still active before updating the loading state
                    if (coroutineContext[Job]?.isActive == true) {
                        updateLoadingState(false)
                    }
                }
            }
        }
    }


    private fun getDeviceTypes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateLoadingState(true)
                val deviceTypesResponse = deviceRepository.getDeviceTypes()
                val deviceTypesList = mutableListOf<DeviceType>()
                deviceTypesList.addAll(deviceTypesResponse)

                _deviceTypes.postValue(deviceTypesList)
            } catch (networkException: IOException) {
                // Handle network error
                handleApiError("Network error fetching deviceTypes")
            } catch (serverException: HttpException) {
                when (serverException.code()) {
                    400 -> {
                        // Handle 400 Bad Request
                        handleApiError("Bad Request fetching deviceTypes")
                    }

                    401 -> {
                        // Handle 401 Unauthorized
                        handleApiError("Unauthorized fetching deviceTypes")
                    }

                    404 -> {
                        // Handle 404 Not Found
                        handleApiError("Not Found fetching deviceTypes")
                    }

                    500 -> {
                        // Handle 500 Internal Server Error
                        handleApiError("Internal Server Error fetching deviceTypes")
                    }

                    else -> {
                        // Handle other HTTP errors
                        handleApiError("HTTP error: ${serverException.code()}, ${serverException.message}")
                    }
                }
            } catch (e: Exception) {
                // Handle other errors
                handleApiError("Error fetching deviceTypes")
            } finally {
                // Ensure that loading state is updated even if there's an exception
                withContext(Dispatchers.Main) {
                    // Check if the coroutine is still active before updating the loading state
                    if (coroutineContext[Job]?.isActive == true) {
                        updateLoadingState(false)
                    }
                }
            }
        }
    }

    fun refreshDeviceTypes() {
        getDeviceTypes()
    }

    var deviceCreationResult by mutableStateOf<Pair<Boolean, String?>>(Pair(false, null))

    // Function to reset the user creation result
    fun resetDeviceCreationResult() {
        deviceCreationResult = Pair(false, null)
    }

    fun resetpostDeviceSettingResult() {
        postDeviceSettingResult = Pair(false, null)
    }


    suspend fun createDevice(
        name: String,
        deviceTypeId: Int,
        sendSettingsAtConn: Boolean,
        sendSettingsNow: Boolean,
        authId: String,
        password: String,
        context: Context
    ) {
        deviceCreationResult = try {
            val result = deviceRepository.createDevice(
                name,
                deviceTypeId,
                sendSettingsAtConn,
                sendSettingsNow,
                authId,
                password,
                context
            )
            result
        } catch (e: HttpException) {
            // Handle HTTP errors
            val errorMessage = when (e.code()) {
                400 -> "Bad Request: ${e.message()}"
                401 -> "Unauthorized: ${e.message()}"
                403 -> "Forbidden: ${e.message()}"
                404 -> "Not Found: ${e.message()}"
                500 -> "Internal Server Error: ${e.message()}"
                else -> "HTTP Error: ${e.code()}, ${e.message()}"
            }
            handleApiError(errorMessage + e)
            Pair(false, errorMessage)
        } catch (e: IOException) {
            // Handle network errors
            handleApiError("Network error creating device $e")
            Pair(false, "Network error occurred.")
        } catch (e: Exception) {
            // Handle other exceptions
            handleApiError("Unexpected error creating device + $e")
            Pair(false, "An unexpected error occurred.")
        }
    }

    var assignDeviceUserCreationResult by mutableStateOf<Pair<Boolean, String?>>(Pair(false, null))
        private set


    suspend fun assignUserToDevice(userId: String, deviceId: Int, context: Context) {
        assignDeviceUserCreationResult = try {
            // Perform the necessary network or database operations here
            deviceRepositoryOrchestrator.assignUserToDevice(userId, deviceId, context)
        } catch (e: Exception) {
            val errorMessage = "Error assigning user to device"
            handleApiError(errorMessage + e)
            Pair(false, "An unexpected error occurred.")
        }
    }


    fun getMetricsForDevice(deviceId: String): LiveData<List<DeviceMetrics>?> {
        return deviceMetrics.map { metricsMap ->
            metricsMap[deviceId]
        }
    }


    fun getAggLogs(
        timeSelector: String,
        deviceId: Int,
        fieldId: Int,
        startDate: String,
        endDate: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateLoadingState(true)
                val aggLogsDeferred = async {
                    deviceRepositoryMetric.getAggLogs(
                        timeSelector,
                        deviceId,
                        fieldId,
                        startDate,
                        endDate
                    )
                }

                val aggLogsResponse = aggLogsDeferred.await()

                _aggLogs.postValue(aggLogsResponse)

            } catch (exception: Exception) {
                handleApiError("Error getAggLogs +$exception")
            } finally {
                updateLoadingState(false)
            }
        }
    }

    // Create a mutable list to store elements of IdColorItem
    val idColorList: MutableList<IdColorItem> = mutableListOf()

    var showBottomBar = mutableStateOf(false)


    private val firmwareApiService: ApiService = ApiClient.firmwareApiService
    private val firmwareRepository = FirmwareRepository(firmwareApiService)

    var firmwareCreationResult by mutableStateOf<Pair<Boolean, String?>>(Pair(false, null))
        private set


    suspend fun updateFirmware(deviceId: Int, file: String, context: Context) {
        firmwareCreationResult = try {
            // Perform the necessary network or database operations here
            firmwareRepository.updateFirmware(deviceId, file, context)
        } catch (e: Exception) {
            val errorMessage = "Error updating Firmware"
            handleApiError(errorMessage + e)
            Pair(false, "An unexpected error occurred.")
        }
    }

    suspend fun refreshFirmware(deviceId: Int) {

        val firmwareHistoryMap = mutableMapOf<String, List<FirmwareUpdates>>()
        val firmwareHistoryResponse = firmwareRepository.getFirmwareHistory(deviceId)
        firmwareHistoryMap[deviceId.toString()] = firmwareHistoryResponse
    }

    var postDeviceSettingResult by mutableStateOf<Pair<Boolean, String?>>(Pair(false, null))
        private set

    suspend fun postDeviceSettings(postSetting: PostSetting, context: Context) {
        postDeviceSettingResult = try {
            deviceRepository.postDeviceSettings(postSetting, context)
        } catch (e: HttpException) {
            // Handle HTTP errors
            val errorMessage = when (e.code()) {
                400 -> "Bad Request: ${e.message()}"
                401 -> "Unauthorized: ${e.message()}"
                403 -> "Forbidden: ${e.message()}"
                404 -> "Not Found: ${e.message()}"
                500 -> "Internal Server Error: ${e.message()}"
                else -> "HTTP Error: ${e.code()}, ${e.message()}"
            }
            handleApiError(errorMessage + e)
            Pair(false, errorMessage)
        } catch (e: IOException) {
            // Handle network errors
            handleApiError("Network error update setting $e")
            Pair(false, "Network error occurred.")
        } catch (e: Exception) {
            // Handle other exceptions
            handleApiError("Unexpected error update setting + $e")
            Pair(false, "An unexpected error occurred.")
        }
    }


     suspend fun getDeviceSettings(deviceId: Int) {
        val deviceSettingsList = mutableListOf<DeviceSettings>()
        val settingsResponse = deviceRepository.getDeviceSettings(deviceId)
        for (setting in settingsResponse) {
            // Create your result object using the information from the response
            val resultItem = DeviceSettings(
                value = setting.value,
                setting = setting.setting,
                updateStatus = setting.updateStatus,
                deviceId = setting.deviceId,
                userId = setting.userId,
                id = setting.id,
                createdAt = setting.createdAt,
                updatedAt = setting.updatedAt
            )

            deviceSettingsList.add(resultItem)
        }
         _deviceSettings.value = deviceSettingsList
    }
}


