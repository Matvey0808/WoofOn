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
    val textDeviceName: StateFlow<String> = _textDeviceName.asStateFlow()
    val textMacAddress: StateFlow<String> = _textMacAddress.asStateFlow()
    val textBroadcastAddress: StateFlow<String> = _textBroadcastAddress.asStateFlow()

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
}