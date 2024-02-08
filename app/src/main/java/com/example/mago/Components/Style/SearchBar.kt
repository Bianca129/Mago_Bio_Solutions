package com.example.mago.Components.Style

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mago.R
import com.example.mago.ui.theme.Black
import com.example.mago.ui.theme.ComplementaryBlue
import com.example.mago.ui.theme.ComplementaryGrey

@Composable
fun SearchBarListBox(onSearchTextChange: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    TextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onSearchTextChange(it)
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                style = MaterialTheme.typography.titleMedium,
                color = ComplementaryBlue
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Black,
            )
        },
        trailingIcon = {
            // Display close icon only when there's text in the search bar
            if (searchText.isNotEmpty()) {
                IconButton(
                    onClick = {
                        searchText = ""
                        onSearchTextChange("")
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Black
                    )
                }
            }
        },
        visualTransformation = if (searchText.isEmpty()) VisualTransformation.None else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        maxLines = 1,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.80f)
            .onFocusChanged { isFocused = it.isFocused }
            .clip(RoundedCornerShape(40.dp))
            .height(50.dp),
        shape = RoundedCornerShape(40.dp),
        colors = TextFieldDefaults.run {
            colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = ComplementaryGrey,
                unfocusedContainerColor = ComplementaryGrey,
                disabledContainerColor = ComplementaryGrey,
                cursorColor = Color.Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        },
        textStyle = TextStyle(
            fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
        )
    )
}
