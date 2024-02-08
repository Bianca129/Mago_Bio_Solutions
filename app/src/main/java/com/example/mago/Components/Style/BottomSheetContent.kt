package com.example.mago.Components.Style

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.Data.Device
import com.example.mago.R

@Composable
fun BottomSheetContent(device: Device, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = device.name,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
            //.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "This is not possible, no call for that",
            style = androidx.wear.compose.material3.MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(5.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row() {
            CustomButton(
                text = stringResource(id = R.string.admin_createUser),
                borderColor = MaterialTheme.colorScheme.primary,
                fillColor = Color.Transparent,
                enabled = true,
                textColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigate("CreateUser")
            }
            Spacer(modifier = Modifier.width(16.dp))
            CustomButton(
                text = "Assign User",
                borderColor = MaterialTheme.colorScheme.primary,
                fillColor = Color.Transparent,
                enabled = true,
                textColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigate("AssignUser")
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}



