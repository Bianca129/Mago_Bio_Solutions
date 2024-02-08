package com.example.mago.Screens

import android.widget.Toast
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
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mago.Components.Style.GreenBorderButton
import com.example.mago.Components.Utils.SharedPreferencesManager
import com.example.mago.Components.Style.TextFieldAdmin
import com.example.mago.R
import com.example.mago.Viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun Password(navController: NavController, userViewModel: UserViewModel){
val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        if(userViewModel.userId.value == ""){
            val userId = SharedPreferencesManager.getUserId()
            userViewModel.getUserData(userId)
        }else{
            userViewModel.getUserData(userViewModel.userId.value)
        }
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {

        var passwordNew by remember { mutableStateOf("") }
        var passwordNewConfirm by remember { mutableStateOf("") }

        TextFieldAdmin(
            label = stringResource(id = R.string.newPassword),
            value = passwordNew,
            onValueChange = {
                passwordNew = it
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextFieldAdmin(
            label = stringResource(id = R.string.confirmPassword),
            value = passwordNewConfirm,
            onValueChange = {
                passwordNewConfirm = it
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        GreenBorderButton(
            text = stringResource(id = R.string.changePassword),
            enabled = passwordNew != "" && passwordNewConfirm != "" && passwordNewConfirm == passwordNew
        ) {

            userViewModel.viewModelScope.launch {
                try {
                    val familyName = ""
                    val givenName = ""
                    val email = ""
                    val userId = SharedPreferencesManager.getUserId()

                    userViewModel.updatePassword(
                        authId = userId,
                        familyName = familyName,
                        givenName = givenName,
                        email = email,
                        password = passwordNew,
                        context = context
                    )
                } catch (e: Exception) {
                    Toast.makeText(context, "Error updating password: ${e.message}", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}