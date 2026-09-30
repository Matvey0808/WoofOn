package com.example.woofon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.woofon.components.AddDeviceDialog
import com.example.woofon.data.viewmodels.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.woofon.components.DeviceCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    viewModel: HomeViewModel = viewModel()
) {
    val devices by viewModel.devices.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (!viewModel.isSelect.collectAsState().value) {
                        Text("WoofOn", fontWeight = FontWeight.Bold)
                    }
                },
                navigationIcon = {
                    if (viewModel.isSelect.collectAsState().value) {
                        IconButton({ viewModel.isSelectDeviceCard() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                },
                actions = {
                    if (viewModel.isSelect.collectAsState().value) {
                        IconButton({}) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null
                            )
                        }
                        IconButton({}) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                { viewModel.toggleDialog() },
                containerColor = MaterialTheme.colorScheme.surface,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
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
            Button({ viewModel.isSelectDeviceCard() }) {
                Text("View")
            }
        }
    }
}
