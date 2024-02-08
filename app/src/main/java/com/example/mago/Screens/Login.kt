package com.example.mago.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.Components.Style.GreenBorderButton
import com.example.mago.Components.Utils.startAuth0Authentication
import com.example.mago.R
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.Viewmodel.UserViewModel

@Composable
fun Login(navController: NavController, userViewModel: UserViewModel, deviceViewModel: DeviceViewModel) {
    // Access the current context
    val context = LocalContext.current

    // Main container for the Login screen
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Background image
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = null, // Provide a suitable description or leave it null
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
        )

        // Content of the Login screen
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title of the application
            Row(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .padding(top = 100.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = stringResource(id = R.string.mago),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            // Login button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 100.dp)
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                GreenBorderButton(
                    text = stringResource(id = R.string.login),
                    enabled = true
                ) {
                    // Trigger authentication process when the Login button is clicked
                    startAuth0Authentication(
                        navController,
                        context,
                        userViewModel,
                        deviceViewModel
                    )
                }
            }
        }
    }
}

