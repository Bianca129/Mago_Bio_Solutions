package com.example.mago.Components.Dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.MaterialTheme
import com.example.mago.Components.Utils.convertIsoToDate
import com.example.mago.ui.theme.ComplementaryGrey

@Composable
fun RowPreviousUpdates(date: String, version: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.3f)
        ) {
            Text(
                text = convertIsoToDate(date),
                style = MaterialTheme.typography.bodySmall,
                color = ComplementaryGrey,
                modifier = Modifier
                    .padding(5.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
        ) {
            Text(
                text = version,
                style = MaterialTheme.typography.bodySmall,
                color = ComplementaryGrey,
                modifier = Modifier
                    .padding(5.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(15.dp))
    Row(){
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}