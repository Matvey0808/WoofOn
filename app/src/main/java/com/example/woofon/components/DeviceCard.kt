package com.example.woofon.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.woofon.data.viewmodels.models.DeviceModel
import com.example.woofon.data.viewmodels.HomeViewModel
import com.example.woofon.network.sendMagicPacket
import kotlinx.coroutines.launch

@Composable
fun DeviceCard(
    deviceModel: DeviceModel,
) {
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .clickable(onClick = {
                scope.launch {
                    sendMagicPacket(
                        mac = deviceModel.macAddress,
                        broadcast = deviceModel.broadcastAddress,
                        port = deviceModel.port
                    )
                }
            }),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(deviceModel.title, fontSize = 18.sp)
            Text(deviceModel.macAddress)
        }
    }
}