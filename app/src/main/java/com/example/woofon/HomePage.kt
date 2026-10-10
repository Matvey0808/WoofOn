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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SettingsRemote
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.woofon.components.AddDeviceDialog
import com.example.woofon.data.viewmodels.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.woofon.components.DeviceCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    viewModel: HomeViewModel = viewModel()
) {
    val devices by viewModel.devices.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    NavigationMenu(
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        AnimatedVisibility(
                            visible = !viewModel.isSelect.collectAsState().value,
                            enter = expandHorizontally(
                                animationSpec = tween(durationMillis = 300),
                                expandFrom = Alignment.Start
                            ),
                            exit = shrinkHorizontally(
                                animationSpec = tween(durationMillis = 300),
                                shrinkTowards = Alignment.Start
                            )
                        ) {
                            Text("WoofOn", fontWeight = FontWeight.Bold)
                        }
                    },
                    navigationIcon = {
                        AnimatedVisibility(
                            visible = !viewModel.isSelect.collectAsState().value,
                            enter = expandHorizontally(
                                animationSpec = tween(durationMillis = 300),
                                expandFrom = Alignment.Start
                            ),
                            exit = shrinkHorizontally(
                                animationSpec = tween(durationMillis = 300),
                                shrinkTowards = Alignment.Start
                            )
                        ) {
                            IconButton(
                                {
                                    scope.launch {
                                        if (drawerState.isClosed) {
                                            drawerState.open()
                                        } else {
                                            drawerState.close()
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = null
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = viewModel.isSelect.collectAsState().value,
                            enter = expandHorizontally(
                                animationSpec = tween(durationMillis = 300),
                                expandFrom = Alignment.Start
                            ),
                            exit = shrinkHorizontally(
                                animationSpec = tween(durationMillis = 300),
                                shrinkTowards = Alignment.Start
                            )
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton({ viewModel.isSelectDeviceCard(isBack = true) }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = null
                                    )
                                }
                                Spacer(Modifier.padding(horizontal = 8.dp))
                                Text(
                                    "${viewModel.listDevices.collectAsState().value.size}",
                                    fontSize = 20.sp
                                )
                            }
                        }
                    },
                    actions = {
                        AnimatedVisibility(
                            visible = viewModel.isSelect.collectAsState().value,
                            enter = expandHorizontally(
                                animationSpec = tween(durationMillis = 300),
                                expandFrom = Alignment.Start
                            ),
                            exit = shrinkHorizontally(
                                animationSpec = tween(durationMillis = 300),
                                shrinkTowards = Alignment.Start
                            )
                        ) {
                            Row {
                                IconButton(
                                    onClick = {
                                        viewModel.listDevices.value.singleOrNull()
                                            ?.let(viewModel::editDeviceCard)
                                    },
                                    enabled = viewModel.listDevices.collectAsState().value.size == 1
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit"
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
}

@Composable
fun NavigationMenu(drawerState: DrawerState, content: @Composable () -> Unit) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(240.dp),
                drawerContainerColor = MaterialTheme.colorScheme.background,
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NavigationDrawerItem(
                        modifier = Modifier.width(220.dp),
                        label = { Text("WOL") },
                        icon = { Icon(Icons.Default.Devices, contentDescription = null) },
                        selected = true,
                        onClick = {},
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                    NavigationDrawerItem(
                        modifier = Modifier.width(220.dp),
                        label = { Text("SSH") },
                        icon = { Icon(Icons.Default.SettingsRemote, contentDescription = null) },
                        selected = false,
                        onClick = {},
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }
        }
    ) {
        content()
    }
}