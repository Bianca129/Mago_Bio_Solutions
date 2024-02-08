package com.example.mago.Viewmodel

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mago.Components.Utils.SharedPreferencesManager
import com.example.mago.Data.GetUser
import com.example.mago.Data.GetUserObject
import com.example.mago.Data.Remote.ApiClient
import com.example.mago.Data.Remote.ApiService
import com.example.mago.Repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class UserViewModel (application: Application) : AndroidViewModel(application) {

    private val usersApiService: ApiService = ApiClient.userApiService
    private val userRepository = UserRepository(usersApiService)


    //Admin / User Login
    var isAdmin = mutableStateOf(false)
    val userId = mutableStateOf<String>("")


    private val _allUsers = MutableLiveData<List<GetUserObject>>()
    val allUsers: LiveData<List<GetUserObject>> get() = _allUsers
    var familyName: String = "DefaultLastName"
    var givenName: String = "DefaultFirstName"
    var email: String = "default@example.com"


    private val _accessToken = MutableLiveData<String>()

    init{
        SharedPreferencesManager.init(application)
        val token= SharedPreferencesManager.getAuthToken()
        if(token != null){
            ApiClient.setBearerToken("$token")
            setBearerToken("$token")
        }
    }

    fun setBearerToken(token: String) {
        ApiClient.setBearerToken(token)
        _accessToken.value = token

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

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }


    private fun updateLoadingState(isLoading: Boolean) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            _isLoading.value = isLoading
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                _isLoading.value = isLoading
            }
        }
    }


    private val loggedInUser = mutableStateOf<GetUserObject?>(null)



    fun getUsersWithId(id: String){

        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateLoadingState(true)
                val devicesResponse = userRepository.getUsersWithId(id)

                // Check if the coroutine is still active before updating LiveData
                if (coroutineContext[Job]?.isActive == true) {
                    loggedInUser.value = devicesResponse
                    SharedPreferencesManager.setUsername(devicesResponse.user.name)
                    SharedPreferencesManager.setProfilePicture(devicesResponse.user.picture)
                }
            } catch (networkException: IOException) {
                // Handle network error
                val errorMessage = "Network error getting logged in user"
                handleApiError(errorMessage + networkException)
            } catch (serverException: HttpException) {
                // Handle server error (e.g., 404, 500, etc.)
                val errorMessage = "Server error getting logged in user"
                handleApiError(errorMessage )
            } catch (e: Exception) {
                // Handle other errors
                val errorMessage = "Error getting logged in user"
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


    private fun getUsers() {
        val userList = mutableListOf<GetUserObject>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userResponse = userRepository.getUsers()
                for (user in userResponse) {
                    val userResults = GetUserObject(
                        user = GetUser(
                            user_id = user.user.user_id,
                            name = user.user.name,
                            email = user.user.email,
                            picture = user.user.picture
                        ),
                        role = user.role
                    )
                    userList.add(userResults)
                }

                // Use postValue to update LiveData on the main thread
                withContext(Dispatchers.Main) {
                    _allUsers.postValue(userList)
                }

            } catch (networkException: IOException) {
                // Handle network error
                handleApiError("Network error fetching users + $networkException")
            } catch (serverException: HttpException) {
                when (serverException.code()) {
                    400 -> {
                        // Handle 400 Bad Request
                        handleApiError("Bad Request error fetching users + $serverException")
                    }
                    401 -> {
                        // Handle 401 Unauthorized
                        handleApiError("Unauthorized error fetching users + $serverException")
                    }
                    404 -> {
                        // Handle 404 Not Found
                        handleApiError("Not Found error fetching users + $serverException")
                    }
                    500 -> {
                        // Handle 500 Internal Server Error
                        handleApiError("Internal Server Error fetching users + $serverException")
                    }
                    else -> {
                        // Handle other HTTP errors
                        handleApiError("HTTP error: ${serverException.code()}, ${serverException.message}")
                    }
                }
            }
            catch (e: Exception) {
                // Handle other errors
                handleApiError("Error fetching users +$e")
            } finally {
                withContext(Dispatchers.Main) {
                    // Check if the coroutine is still active before updating the loading state
                    if (coroutineContext[Job]?.isActive == true) {
                        updateLoadingState(false)
                    }
                }
            }
        }
    }



    fun refreshUsers(){
        getUsers()
    }

    var userCreationResult by mutableStateOf<Pair<Boolean, String?>>(Pair(false, null))
        private set

    // Function to reset the user creation result
    fun resetUserCreationResult() {
        userCreationResult = Pair(false, null)
    }

    suspend fun createUser(familyName: String, givenName: String, email: String, sysAdmin: Boolean, password: String, context: Context) {
        userCreationResult = try {
            val result = userRepository.createUser(familyName, givenName, email, sysAdmin, password, context)
            // Set the userCreationResult to the result from the repository
            result
        } catch (e: Exception) {
            val errorMessage = "Error creating user"
            handleApiError(errorMessage + e)
            Pair(false, "An unexpected error occurred.")
        }
    }



    suspend fun getUserData(id:String) {
        try {
            val result = userRepository.getUserData(id)
            // Set the userCreationResult to the result from the repository
            familyName = result.user.family_name
            givenName = result.user.given_name
            email = result.user.email

        } catch (e: Exception) {
            val errorMessage = "Error get userdata"
            handleApiError(errorMessage + e)
            Pair(false, "An unexpected error occurred.")
        }
    }

    var userUpdateResult by mutableStateOf<Pair<Boolean, String?>>(Pair(false, null))
        private set
    suspend fun updatePassword(authId: String, familyName: String, givenName: String, email: String, password: String, context: Context) {
        userUpdateResult = try {
            val result = userRepository.updatePassword(authId, familyName, givenName, email, password, context)
            result
        } catch (e: Exception) {
            val errorMessage = "Error update password"
            handleApiError(errorMessage + e)
            Pair(false, "An unexpected error occurred.")
        }
    }

}

