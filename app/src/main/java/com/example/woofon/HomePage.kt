package com.example.woofon

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    AnimatedVisibility(
                        visible = !viewModel.isSelect.collectAsState().value,
                        enter = expandHorizontally(
                            animationSpec = tween(durationMillis = 400),
                            expandFrom = Alignment.Start
                        ),
                        exit = shrinkHorizontally(
                            animationSpec = tween(durationMillis = 400),
                            shrinkTowards = Alignment.Start
                        )
                    ) {
                        Text("WoofOn", fontWeight = FontWeight.Bold)
                    }
                },
                navigationIcon = {
                    AnimatedVisibility(
                        visible = viewModel.isSelect.collectAsState().value,
                        enter = expandHorizontally(
                            animationSpec = tween(durationMillis = 400),
                            expandFrom = Alignment.Start
                        ),
                        exit = shrinkHorizontally(
                            animationSpec = tween(durationMillis = 400),
                            shrinkTowards = Alignment.Start
                        )
                    ) {
                        Row (verticalAlignment = Alignment.CenterVertically) {
                            IconButton({ viewModel.isSelectDeviceCard() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null
                                )
                            }
                            Spacer(Modifier.padding(horizontal = 8.dp))
                            Text("${viewModel.listDevices.collectAsState().value.size}", fontSize = 20.sp)
                        }
                    }
                },
                actions = {
                    AnimatedVisibility(
                        visible = viewModel.isSelect.collectAsState().value,
                        enter = expandHorizontally(
                            animationSpec = tween(durationMillis = 400),
                            expandFrom = Alignment.Start
                        ),
                        exit = shrinkHorizontally(
                            animationSpec = tween(durationMillis = 400),
                            shrinkTowards = Alignment.Start
                        )
                    ) {
                        Row {
                            IconButton({}) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null
                                )
                            }
                            IconButton(
                                {
                                    viewModel.deleteDeviceCard(viewModel.listDevices.value.map { it.id })
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null
                                )
                            }
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
        }
    }
}
