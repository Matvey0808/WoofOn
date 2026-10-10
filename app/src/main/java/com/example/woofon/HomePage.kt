package com.example.woofon

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.woofon.components.AddDeviceDialog
import com.example.woofon.data.viewmodels.HomeViewModel
import com.example.woofon.components.DeviceCard
@Composable
fun HomePage(
    viewModel: HomeViewModel,
) {
    val devices by viewModel.devices.collectAsState()
    LazyColumn {
        items(devices) { items ->
            DeviceCard(items, viewModel)
        }
    }
    when {
        viewModel.isActiveDialog.collectAsState().value -> {
            AddDeviceDialog(
                viewModel = viewModel
            )
        }
    }
}
