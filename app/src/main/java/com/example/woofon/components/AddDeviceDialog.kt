package com.example.woofon.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.woofon.data.viewmodels.HomeViewModel
import com.example.woofon.data.viewmodels.TagsTextField

@Composable
fun AddDeviceDialog(
    viewModel: HomeViewModel
) {
    val isEditing = viewModel.editingDevice.collectAsStateWithLifecycle().value != null
    Dialog(onDismissRequest = { viewModel.toggleDialog() }) {
        Card(
            modifier = Modifier.size(height = 360.dp, width = 300.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    if (isEditing) "Edit Device" else "Add Device",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 14.dp)
                )
                WoLField(
                    viewModel,
                    "Device Name",
                    tag = TagsTextField.DEVICE,
                    state = viewModel.textDeviceName.collectAsStateWithLifecycle().value,
                    error = 0,
                    keyboardOpt = KeyboardType.Unspecified,
                    visualTransformation = VisualTransformation.None
                )
                WoLField(
                    viewModel,
                    "MAC Address",
                    tag = TagsTextField.MAC,
                    state = viewModel.textMacAddress.collectAsStateWithLifecycle().value,
                    error = 1,
                    keyboardOpt = KeyboardType.Unspecified,
                    visualTransformation = MacVisualTransformation()
                )
                WoLField(
                    viewModel,
                    "Broadcast Address",
                    tag = TagsTextField.BROADCAST,
                    state = viewModel.textBroadcastAddress.collectAsStateWithLifecycle().value,
                    error = 2,
                    keyboardOpt = KeyboardType.Number,
                    visualTransformation = VisualTransformation.None
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 26.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Port")
                    Spacer(Modifier.padding(vertical = 10.dp))
                    viewModel.ports.collectAsStateWithLifecycle().value.forEach { text ->
                        RadioButton(
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.surface
                            ),
                            selected = (text == viewModel.selectedPort.collectAsStateWithLifecycle().value),
                            onClick = { viewModel.portSwitching(text) }
                        )
                        Text(text)
                    }
                }
                Button(
                    onClick = { viewModel.saveDeviceCard() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(if (isEditing) "Save" else "Add")
                }
            }
        }
    }
}
