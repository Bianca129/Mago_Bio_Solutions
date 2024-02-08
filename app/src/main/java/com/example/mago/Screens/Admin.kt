package com.example.mago.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.Components.Dashboard.SettingListItem
import com.example.mago.R

@Composable
fun Admin(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        SettingListItem(R.string.admin_createDevice, true) {
            navController.navigate("CreateDevice")
        }
        SettingListItem(R.string.admin_createUser, true) {
            navController.navigate("CreateUser")
        }
        SettingListItem(R.string.admin_assignUser, true) {
            navController.navigate("AssignUser")
        }
    }
}



