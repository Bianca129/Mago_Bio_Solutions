package com.example.mago.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.Components.Account.CircleImageWithLink
import com.example.mago.Components.Dashboard.SettingListItem
import com.example.mago.Components.Utils.SharedPreferencesManager
import com.example.mago.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Profile(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        val username = SharedPreferencesManager.getUsername()
        val picture = SharedPreferencesManager.getProfilePicture()

        Row(){

            if(picture != null){
                CircleImageWithLink(
                    imageUrl = picture,
                    linkUrl = picture,
                    context = LocalContext.current
                )
            }

            Spacer(modifier = Modifier.width(32.dp))

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                if (username != null){
                    Text("$username",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        SettingListItem(R.string.profile_pw, true) {
            navController.navigate("Password")
        }

        SettingListItem(R.string.profile_logout, true) {
            navController.navigate("Logout")
        }
    }
}
