package com.example.woofon.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.woofon.data.viewmodels.models.DeviceModel
import com.example.woofon.data.viewmodels.HomeViewModel
import com.example.woofon.network.sendMagicPacket
import kotlinx.coroutines.launch

@Composable
fun DeviceCard(
    deviceModel: DeviceModel,
    viewModel: HomeViewModel
) {
    val scope = rememberCoroutineScope()
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .combinedClickable(
                onClick = {
                    if (viewModel.listDevices.value.isNotEmpty()) {
                        viewModel.addDeviceToList(deviceModel)
                    } else {
                        scope.launch {
                            sendMagicPacket(
                                mac = deviceModel.macAddress,
                                broadcast = deviceModel.broadcastAddress,
                                port = deviceModel.port
                            )
                        }
                    }
                },
                onLongClick = {
                    viewModel.addDeviceToList(deviceModel)
                }
            ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = if (viewModel.listDevices.collectAsState().value.contains(deviceModel)) BorderStroke(
            2.dp,
            MaterialTheme.colorScheme.onPrimary
        ) else BorderStroke(2.dp, Color.Transparent),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(deviceModel.title, fontSize = 18.sp)
            Text(deviceModel.macAddress)
        }
    }
}