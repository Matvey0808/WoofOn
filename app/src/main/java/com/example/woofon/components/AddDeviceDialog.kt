package com.example.woofon.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.woofon.data.viewmodels.DeviceModel
import com.example.woofon.data.viewmodels.HomeViewModel
import com.example.woofon.data.viewmodels.TagsTextField

@Composable
fun AddDeviceDialog(
    viewModel: HomeViewModel
) {
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
                    "Add Device",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 14.dp)
                )
                Spacer(Modifier.padding(vertical = 10.dp))
                TextField(
                    colors = TextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.padding(horizontal = 26.dp),
                    label = { Text("Device name") },
                    trailingIcon = {
                        if (viewModel.isError.collectAsStateWithLifecycle().value[0]) {
                            Icon(
                                imageVector = Icons.Default.ErrorOutline,
                                contentDescription = null
                            )
                        }
                    },
                    value = viewModel.textDeviceName.collectAsStateWithLifecycle().value,
                    onValueChange = { text ->
                        viewModel.textInField(tag = TagsTextField.DEVICE, deviceName = text)
                    }
                )
                TextField(
                    colors = TextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.padding(horizontal = 26.dp, vertical = 8.dp),
                    label = { Text("MAC Address") },
                    trailingIcon = {
                        if (viewModel.isError.collectAsStateWithLifecycle().value[1]) {
                            Icon(
                                imageVector = Icons.Default.ErrorOutline,
                                contentDescription = null
                            )
                        }
                    },
                    value = viewModel.textMacAddress.collectAsStateWithLifecycle().value,
                    onValueChange = { text ->
                        viewModel.textInField(tag = TagsTextField.MAC, macAddress = text)
                    }
                )
                TextField(
                    colors = TextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.padding(horizontal = 26.dp),
                    label = { Text("Broadcast Address") },
                    value = viewModel.textBroadcastAddress.collectAsStateWithLifecycle().value,
                    trailingIcon = {
                        if (viewModel.isError.collectAsStateWithLifecycle().value[2]) {
                            Icon(
                                imageVector = Icons.Default.ErrorOutline,
                                contentDescription = null
                            )
                        }
                    },
                    onValueChange = { text ->
                        viewModel.textInField(
                            tag = TagsTextField.BROADCAST,
                            broadcastAddress = text
                        )
                    }
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
                    onClick = {
                        viewModel.addDeviceCard(
                            deviceModel = DeviceModel(
                                title = viewModel.textDeviceName.value,
                                macAddress = viewModel.textMacAddress.value,
                                broadcastAddress = viewModel.textBroadcastAddress.value,
                                port = viewModel.selectedPort.value.toInt()
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text("Add")
                }
            }
        }
    }
}