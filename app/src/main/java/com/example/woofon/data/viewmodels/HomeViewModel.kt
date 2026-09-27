package com.example.woofon.data.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class TagsTextField {
    DEVICE,
    MAC,
    BROADCAST
}

class HomeViewModel : ViewModel() {
    private val _textDeviceName = MutableStateFlow("")
    private val _textMacAddress = MutableStateFlow("")
    private val _textBroadcastAddress = MutableStateFlow("")
    private val _ports = MutableStateFlow(listOf("7", "9"))
    private val _selectedPort = MutableStateFlow(_ports.value[0])
    private val _isActiveDialog = MutableStateFlow(false)
    val textDeviceName: StateFlow<String> = _textDeviceName.asStateFlow()
    val textMacAddress: StateFlow<String> = _textMacAddress.asStateFlow()
    val textBroadcastAddress: StateFlow<String> = _textBroadcastAddress.asStateFlow()
    val ports: StateFlow<List<String>> = _ports.asStateFlow()
    val selectedPort: StateFlow<String> = _selectedPort.asStateFlow()
    val isActiveDialog: StateFlow<Boolean> = _isActiveDialog.asStateFlow()

    fun textInField(
        deviceName: String = "",
        macAddress: String = "",
        broadcastAddress: String = "",
        tag: TagsTextField
    ) {
        when(tag) {
            TagsTextField.DEVICE -> _textDeviceName.value = deviceName
            TagsTextField.MAC -> _textMacAddress.value = macAddress
            TagsTextField.BROADCAST -> _textBroadcastAddress.value = broadcastAddress
        }
    }

    fun portSwitching(port: String) {
        _selectedPort.value = port
    }

    fun toggleDialog() {
        _isActiveDialog.value = !_isActiveDialog.value
    }
}