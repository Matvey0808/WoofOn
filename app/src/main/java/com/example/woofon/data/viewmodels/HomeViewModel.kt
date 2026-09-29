package com.example.woofon.data.viewmodels

import androidx.compose.runtime.mutableStateListOf
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
    private val _selectedPort = MutableStateFlow(_ports.value[1])
    private val _isActiveDialog = MutableStateFlow(false)
    private val _listDeviceCard = MutableStateFlow<List<DeviceModel>>(emptyList())
    private val _isError = MutableStateFlow(mutableStateListOf(false, false, false))
    val textDeviceName: StateFlow<String> = _textDeviceName.asStateFlow()
    val textMacAddress: StateFlow<String> = _textMacAddress.asStateFlow()
    val textBroadcastAddress: StateFlow<String> = _textBroadcastAddress.asStateFlow()
    val ports: StateFlow<List<String>> = _ports.asStateFlow()
    val selectedPort: StateFlow<String> = _selectedPort.asStateFlow()
    val isActiveDialog: StateFlow<Boolean> = _isActiveDialog.asStateFlow()
    val listDeviceCard: StateFlow<List<DeviceModel>> = _listDeviceCard.asStateFlow()
    val isError: StateFlow<List<Boolean>> = _isError.asStateFlow()

    fun textInField(
        deviceText: String,
        macText: String,
        broadcastText: String,
        tag: TagsTextField
    ) {
        when (tag) {
            TagsTextField.DEVICE -> {
                _textDeviceName.value = deviceText
                _isError.value[0] = false
            }
            TagsTextField.MAC -> {
                _textMacAddress.value = macText
                _isError.value[1] = false
            }
            TagsTextField.BROADCAST -> {
                _textBroadcastAddress.value = broadcastText
                _isError.value[2] = false
            }
        }
    }

    fun portSwitching(port: String) {
        _selectedPort.value = port
    }

    fun toggleDialog() {
        _isActiveDialog.value = !_isActiveDialog.value
        if (!_isActiveDialog.value) {
            cleanField()
            _selectedPort.value = _ports.value[1]
        }
    }

    fun addDeviceCard(deviceModel: DeviceModel) {
        val _textStateList = listOf(_textDeviceName.value, _textMacAddress.value, _textBroadcastAddress.value)

        for ((i, j) in _isError.value.indices.zip(_textStateList)) {
            if (j == "") {
                _isError.value[i] = true
            } else {
                _isError.value[i] = false
            }
        }

        if (_isError.value.all { !it }) {
            cleanField()
            _listDeviceCard.value += deviceModel
            _isActiveDialog.value = false
        }
    }

    fun cleanField() {
        _textDeviceName.value = ""
        _textMacAddress.value = ""
        _textBroadcastAddress.value = ""
        _isError.value.fill(false)
    }
}