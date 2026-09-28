package com.example.woofon.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.woofon.data.viewmodels.HomeViewModel
import com.example.woofon.data.viewmodels.TagsTextField

@Composable
fun WoLField(viewModel: HomeViewModel, label: String, tag: TagsTextField, state: String, error: Int) {
    TextField(
        colors = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedLabelColor = MaterialTheme.colorScheme.onSurface,
            disabledLabelColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.padding(horizontal = 26.dp, vertical = 6.dp),
        label = { Text(label) },
        trailingIcon = {
            if (viewModel.isError.collectAsStateWithLifecycle().value[error]) {
                Icon(
                    imageVector = Icons.Default.ErrorOutline,
                    contentDescription = null
                )
            }
        },
        value = state,
        onValueChange = {
            viewModel.textInField(
                tag = tag,
                deviceText = it,
                macText = it,
                broadcastText = it
            )
        }
    )
}
