package com.example.mago.Components.Style

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.MaterialTheme
import com.example.mago.Components.Utils.convertIsoToDate
import com.example.mago.Data.Notification
import com.example.mago.R
import com.example.mago.ui.theme.ComplementaryGrey
import com.example.mago.ui.theme.Grey
import com.example.mago.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    sheetState: SheetState,
    notifications: List<Notification>?,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            // Hide the bottom sheet
            onDismiss.invoke()
        },
        containerColor = Grey,
        modifier = Modifier
    ) {
        // Content of the bottom sheet
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 20.dp)

        ) {
            Row(){
                Column(modifier = Modifier
                    .fillMaxWidth(0.23f)){
                    Text(
                        text = stringResource(id = R.string.dateTime),
                        style = MaterialTheme.typography.bodySmall,
                        color = ComplementaryGrey,
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }
                Column(modifier = Modifier
                    .fillMaxWidth(0.77f)){
                    Text(
                        text = stringResource(id = R.string.message),
                        style = MaterialTheme.typography.bodySmall,
                        color = ComplementaryGrey,
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.height(10.dp))

            notifications?.forEach { notification ->
                // Display each notification
                Row() {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.23f)
                            .padding(5.dp)
                    ) {
                        Text(
                            text = convertIsoToDate(notification.timeStamp),
                            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                            color = White
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.77f)
                            .padding(5.dp)
                    ) {
                        Text(
                            text = notification.message,
                            style = MaterialTheme.typography.bodySmall,
                            color = White
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = ComplementaryGrey
                )
            }
        }
    }
}
