package com.example.mago.Components.Utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.jwt.JWT
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.example.mago.R
import com.example.mago.Screens.Screens
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.Viewmodel.UserViewModel


private var cachedCredentials: Credentials? = null
fun startAuth0Authentication(navController: NavController, context: Context, userViewModel: UserViewModel, deviceViewModel: DeviceViewModel) {
    // Create an Auth0 account with your application details
    val account = Auth0(
        context.getString(R.string.com_auth0_client_id),
        context.getString(R.string.com_auth0_domain)
    )

    // Set the required areas and target URL
    val scope = "openid profile email read:current_user"
    val audience = "https://${context.getString(R.string.com_auth0_audience)}"
    val scheme = context.getString(R.string.com_auth0_scheme)

    // Start authentication in the browser
    WebAuthProvider.login(account)
        .withScheme(scheme)
        .withScope(scope)
        .withAudience(audience)
        .start(context as Activity, object : com.auth0.android.callback.Callback<Credentials, AuthenticationException> {

            override fun onSuccess(result: Credentials) {

                // Navigate to the List screen upon successful authentication
                navController.navigate(Screens.List.route)

                // Cache credentials for later use
                cachedCredentials = result

                // Set Bearer token for user and device view models
                userViewModel.setBearerToken(result.accessToken)
                deviceViewModel.setBearerToken(result.accessToken)

                // Save token in SharedPreferences using SharedPreferencesManager
                SharedPreferencesManager.init(context)
                SharedPreferencesManager.setAuthToken(result.accessToken)

                // Decode AccessToken to extract user information
                val jwt = JWT(result.accessToken)

                // Extract user permissions and user ID
                val permissions = jwt.getClaim("permissions").asList(String::class.java)
                val userId = jwt.getClaim("sub")

                // Retrieve user information based on user ID
                userViewModel.getUsersWithId("${userId.asString()}")

                // Set user role (admin or regular user)
                if (permissions[0] == "admin") {
                    userViewModel.isAdmin.value = true
                    SharedPreferencesManager.setAdmin(true)
                    SharedPreferencesManager.setUserId("${userId.asString()}")
                    userViewModel.userId.value = "${userId.asString()}"
                    deviceViewModel.getDevicesWithMetricsAndNotifications()
                } else {
                    userViewModel.isAdmin.value = false
                    SharedPreferencesManager.setAdmin(false)
                    SharedPreferencesManager.setUserId("${userId.asString()}")
                    userViewModel.userId.value = "${userId.asString()}"
                    deviceViewModel.getDevicesWithMetricsAndNotificationsForUsers("${userId.asString()}")
                }

                // Show the bottom bar in the app UI
                deviceViewModel.showBottomBar.value = true
            }

            override fun onFailure(error: AuthenticationException) {
                Toast.makeText(context, context.getString(R.string.errorAuthentication) + " " + error, Toast.LENGTH_SHORT).show()

            }


        })
}

