package com.example.woofon.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.insert
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.woofon.data.viewmodels.HomeViewModel
import com.example.woofon.data.viewmodels.TagsTextField

@Composable
fun WoLField(
    viewModel: HomeViewModel,
    label: String,
    tag: TagsTextField,
    state: String,
    error: Int,
    keyboardOpt: KeyboardType,
    visualTransformation: VisualTransformation
) {
    TextField(
        colors = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedLabelColor = MaterialTheme.colorScheme.onSurface,
            disabledLabelColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = MaterialTheme.colorScheme.surface
        ),
        visualTransformation = visualTransformation,
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
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardOpt
        )
    )
}

class MacVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 12) text.text.substring(0..11) else text.text
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i % 2 == 1 && i != 11) out += ":"
        }
        val macOffsetTranslator =
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 1) return offset
                    if (offset <= 3) return offset + 1
                    if (offset <= 5) return offset + 2
                    if (offset <= 7) return offset + 3
                    if (offset <= 9) return offset + 4
                    if (offset <= 11) return offset + 5
                    return 17
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 2) return offset
                    if (offset <= 5) return offset - 1
                    if (offset <= 8) return offset - 2
                    if (offset <= 11) return offset - 3
                    if (offset <= 14) return offset - 4
                    if (offset <= 16) return offset - 5
                    return 12
                }
            }

        return TransformedText(AnnotatedString(out), macOffsetTranslator)
    }
}
