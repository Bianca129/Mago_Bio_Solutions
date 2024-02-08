package com.example.mago.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.Components.Style.GreenBorderButton
import com.example.mago.Components.Utils.SharedPreferencesManager
import com.example.mago.Components.Style.TextFieldAdmin
import com.example.mago.Components.Utils.sendMail
import com.example.mago.R
import com.example.mago.Viewmodel.UserViewModel

@Composable
fun Contact(navController: NavController, userViewModel : UserViewModel) {

    LaunchedEffect(key1 = true) {
        if(userViewModel.userId.value == ""){
            val userId = SharedPreferencesManager.getUserId()
            userViewModel.getUserData(userId)
        }else{
            userViewModel.getUserData(userViewModel.userId.value)
        }
    }

    val context = LocalContext.current
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        TextFieldAdmin(
            label = stringResource(id = R.string.subject),
            value = subject,
            onValueChange = {
                subject = it
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldAdmin(
            label = stringResource(id = R.string.message),
            value = message,
            onValueChange = {
                message = it
            }
        )


        Spacer(modifier = Modifier.height(20.dp))

        GreenBorderButton(
            text = stringResource(id = R.string.email),
            enabled = subject != "" && message != ""
        ) {
            context.sendMail(to = "721042@student.inholland.nl", subject = subject, body = message)

        }
    }
}
