package com.example.mago.Components.Dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.MaterialTheme
import com.example.mago.Components.Style.Switch
import com.example.mago.R

@Composable
fun SettingListItem(settingTextResourceId: Int, showArrow: Boolean = false, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = settingTextResourceId),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.width(8.dp))
        if (showArrow) {
            Icon(
                painter = painterResource(id = R.drawable.forward),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Switch()
        }
    }
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth()
    )
}

