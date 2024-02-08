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
fun Account(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        SettingListItem(R.string.acc_profile, true) {
            navController.navigate("Profile")
        }

        SettingListItem(R.string.acc_notifications, false) {
            //no page for that
        }

        SettingListItem(R.string.acc_userManual, true) {
            navController.navigate("UserManual")
        }
        SettingListItem(R.string.acc_contact, true) {
            navController.navigate("Contact")
        }
        SettingListItem(R.string.acc_privacy, true) {
            navController.navigate("Privacy")
        }

    }
}



