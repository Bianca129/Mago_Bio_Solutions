package com.example.mago.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.R

@Composable
fun UserManual(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = stringResource(id = R.string.userManual_1), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_2), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_3))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_4), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_5))
        Text(text = stringResource(id = R.string.userManual_6))
        Text(text = stringResource(id = R.string.userManual_7))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_8), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_9))
        Text(text = stringResource(id = R.string.userManual_10))
        Text(text = stringResource(id = R.string.userManual_11))
        Text(text = stringResource(id = R.string.userManual_12))
        Text(text = stringResource(id = R.string.userManual_13))
        Text(text = stringResource(id = R.string.userManual_14))
        Text(text = stringResource(id = R.string.userManual_15))
        Text(text = stringResource(id = R.string.userManual_16))
        Text(text = stringResource(id = R.string.userManual_17))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_18), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_19))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_20), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_21))
        Text(text = stringResource(id = R.string.userManual_22))
        Text(text = stringResource(id = R.string.userManual_23))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_24), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_25))
        Text(text = stringResource(id = R.string.userManual_26))
        Text(text = stringResource(id = R.string.userManual_27))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_28), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_29))
        Text(text = stringResource(id = R.string.userManual_30))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_31), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_32))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_33), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_34))
        Text(text = stringResource(id = R.string.userManual_35))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_36), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_37))
        Text(text = stringResource(id = R.string.userManual_38))
        Text(text = stringResource(id = R.string.userManual_39))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_40), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_41))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_42), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_43))
        Text(text = stringResource(id = R.string.userManual_44))
        Text(text = stringResource(id = R.string.userManual_45))
        Text(text = stringResource(id = R.string.userManual_46))
        Text(text = stringResource(id = R.string.userManual_47))
        Text(text = stringResource(id = R.string.userManual_48))
        Text(text = stringResource(id = R.string.userManual_49))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_50), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_51))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_52), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_53))
        Text(text = stringResource(id = R.string.userManual_54))
        Text(text = stringResource(id = R.string.userManual_55))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_56), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_57))
        Text(text = stringResource(id = R.string.userManual_58))
        Text(text = stringResource(id = R.string.userManual_59))
        Text(text = stringResource(id = R.string.userManual_60))
        Text(text = stringResource(id = R.string.userManual_61))
        Text(text = stringResource(id = R.string.userManual_62))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_63), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_64))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.userManual_65), style = MaterialTheme.typography.titleSmall)
        Text(text = stringResource(id = R.string.userManual_66))

    }
}