package com.example.mago.Repositories

import android.content.Context
import android.widget.Toast
import com.example.mago.Data.GetUser
import com.example.mago.Data.GetUserData
import com.example.mago.Data.GetUserObject
import com.example.mago.Data.Remote.ApiService
import com.example.mago.Data.UpdateUser
import com.example.mago.Data.User
import com.example.mago.Data.Users
import com.example.mago.R

class UserRepository(private val apiService: ApiService) {

    suspend fun getUsers(): List<GetUserObject> {
        return try {
            apiService.getUsers()
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun getUsersWithId(id:String): GetUserObject {
        return try {
            apiService.getUsersWithId(id)
        } catch (e: Exception) {
            GetUserObject(user = GetUser(user_id = "", name= "", email = "", picture = ""), role = false)
        }
    }

    suspend fun createUser(familyName: String, givenName: String, email: String, sysAdmin: Boolean, password: String, context: Context): Pair<Boolean, String?> {
        try {
            val request = User(familyName, givenName, email, sysAdmin, password)
            val response = apiService.createUser(request)

            return if (response.isSuccessful) {
                // User creation is successful, return success status and null for the error message
                Toast.makeText(context, context.getString(R.string.successCreatingUser), Toast.LENGTH_LONG).show()
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
    suspend fun getUserData(id: String): GetUserData {
        return try {
            apiService.getUserData(id)
        } catch (e: Exception) {
            GetUserData(
                Users(
                    user_id = "defaultUserId",
                    family_name = "DefaultLastName",
                    given_name = "DefaultFirstName",
                    name = "DefaultFullName",
                    email = "default@example.com",
                    email_verified = false,
                    blocked = false,
                    createdAt = "2022-01-01T00:00:00Z",  // Datum und Uhrzeit können entsprechend angepasst werden
                    updatedAt = "2022-01-01T00:00:00Z",
                    picture = "default_profile_picture_url"
                ),
                role = false
            )
        }
    }
    suspend fun updatePassword(authId: String, familyName: String, givenName: String, email: String, password: String, context: Context): Pair<Boolean, String?> {
        try {
            val request = UpdateUser(familyName, givenName, email, password)
            val response = apiService.updateUser(authId, request)

            return if (response.isSuccessful) {
                // User creation is successful, return success status and null for the error message
                Toast.makeText(context, context.getString(R.string.successChangePassword), Toast.LENGTH_LONG).show()
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