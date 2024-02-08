package com.example.mago.Components.Style

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mago.Data.Device
import com.example.mago.R
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.ui.theme.ComplementaryGreen
import com.example.mago.ui.theme.ComplementaryGrey
import com.example.mago.ui.theme.Grey

@Composable
fun ComposterState(device: Device, deviceViewModel: DeviceViewModel) {
    //note: it's not possible to get the actual state of the eComposter

    val idStateComposterList by deviceViewModel.idComposterListState.collectAsState()
    var on = idStateComposterList.find { it.id == device.id }
    var state by remember { mutableStateOf(on!!.on) }

    //I dont't know why, but for one reason it is only working because of this Log.
    Log.d("state", "$state")

    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false)
            ) { state = !state
                idStateComposterList.find { it.id == device.id }?.let { foundItem ->
                    foundItem.on = !foundItem.on
                    deviceViewModel.idComposterListState.value = idStateComposterList.toMutableList()
                }
            }
            .size(40.dp)
            .background(
                if (on!!.on) {
                    ComplementaryGreen
                } else if (!on.on) {
                    Color.Transparent
                } else if (state) {
                    ComplementaryGreen
                } else {
                    Color.Transparent
                },
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (on != null) {
            Icon(
                painter = painterResource(id = R.drawable.state),
                contentDescription = null,
                tint = if (on.on) ComplementaryGrey else Grey,
                modifier = Modifier.padding(0.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(7.dp))
    if (on != null) {
        Text(
            text = if (on.on) "On" else "Off",
            style = MaterialTheme.typography.titleSmall,
            color = if (on.on) ComplementaryGreen else Grey
        )
    }
}