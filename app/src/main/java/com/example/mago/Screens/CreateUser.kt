package com.example.mago.Screens


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
import com.example.mago.Components.Style.DropdownMenuAdmin
import com.example.mago.Components.Style.GreenBorderButton
import com.example.mago.Components.Style.TextFieldAdmin
import com.example.mago.R
import com.example.mago.Viewmodel.UserViewModel
import kotlinx.coroutines.launch


@Composable
fun CreateUser(navController: NavController, userViewModel: UserViewModel) {
    // User-related state
    var userType by remember { mutableStateOf<String?>(null) }
    var firstname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var familyname by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        // Dropdown menu for user type selection
        val options = listOf("Admin", "User")

        DropdownMenuAdmin(
            options = options,
            label = stringResource(id = R.string.userType),
            placeholder = stringResource(id = R.string.selectUserType),
            onItemSelected = { selectedType ->
                userType = selectedType
            },
            selectedOption = userType
        )



        Spacer(modifier = Modifier.height(20.dp))

        // TextFields for user information
        TextFieldAdmin(
            label = stringResource(id = R.string.firstNameUser),
            value = firstname,
            onValueChange = {
                firstname = it
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextFieldAdmin(
            label =stringResource(id = R.string.familyNameUser),
            value = familyname,
            onValueChange = {
                familyname = it
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextFieldAdmin(
            label = stringResource(id = R.string.EMail),
            value = email,
            onValueChange = {
                email = it
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextFieldAdmin(
            label = stringResource(id = R.string.password),
            value = password,
            onValueChange = {
                password = it
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Button to create user
        GreenBorderButton(
            text = stringResource(id = R.string.createUser),
            enabled = userType != null &&
                    firstname.isNotBlank() &&
                    familyname.isNotBlank() &&
                    email.isNotBlank() &&
                    password.isNotBlank()
        ) {
            var sysAdmin = userType == "Admin" // Adjust the condition based on the actual value

            userViewModel.viewModelScope.launch {
                try {
                    // Call the userViewModel function to create a user
                    userViewModel.createUser(
                        familyname,
                        firstname,
                        email,
                        sysAdmin,
                        password,
                        context
                    )

                    // Retrieve the result of user creation
                    val (success, errorMessage) = userViewModel.userCreationResult

                    if (success) {

                        // Reset fields and options after successful user creation
                        firstname = ""
                        familyname = ""
                        password = ""
                        email = ""
                        userType = null
                        sysAdmin = false
                        // Reset the user creation result before making a new attempt
                        userViewModel.resetUserCreationResult()
                    } else {
                        Toast.makeText(context, "Error creating user:  $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Error creating user: ${e.message}", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}




