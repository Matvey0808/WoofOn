package com.example.woofon.data.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.woofon.data.viewmodels.models.DeviceModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class TagsTextField {
    DEVICE,
    MAC,
    BROADCAST
}

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _textDeviceName = MutableStateFlow("")
    private val _textMacAddress = MutableStateFlow("")
    private val _textBroadcastAddress = MutableStateFlow("")
    private val _ports = MutableStateFlow(listOf("7", "9"))
    private val _selectedPort = MutableStateFlow(_ports.value[1])
    private val _isActiveDialog = MutableStateFlow(false)
    private val db = AppDatabase.getInstance(application)
    private val dao = db.getDeviceDao()
    val devices: StateFlow<List<DeviceModel>> = dao.getDevices()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    private val _isSelect = MutableStateFlow(false)
    private val _isError = MutableStateFlow(mutableStateListOf(false, false, false))
    private val _listDevices = MutableStateFlow<List<DeviceModel>>(emptyList())
    val textDeviceName: StateFlow<String> = _textDeviceName.asStateFlow()
    val textMacAddress: StateFlow<String> = _textMacAddress.asStateFlow()
    val textBroadcastAddress: StateFlow<String> = _textBroadcastAddress.asStateFlow()
    val ports: StateFlow<List<String>> = _ports.asStateFlow()
    val selectedPort: StateFlow<String> = _selectedPort.asStateFlow()
    val isActiveDialog: StateFlow<Boolean> = _isActiveDialog.asStateFlow()
    val isError: StateFlow<List<Boolean>> = _isError.asStateFlow()
    val isSelect: StateFlow<Boolean> = _isSelect.asStateFlow()
    val listDevices: StateFlow<List<DeviceModel>> = _listDevices.asStateFlow()

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
        val textStateList =
            listOf(_textDeviceName.value, _textMacAddress.value, _textBroadcastAddress.value)

        for ((i, j) in _isError.value.indices.zip(textStateList)) {
            if (j == "") {
                _isError.value[i] = true
            } else {
                _isError.value[i] = false
            }
        }

        if (_isError.value.all { !it }) {
            cleanField()
            viewModelScope.launch {
                dao.addDevice(device = deviceModel)
            }
            _isActiveDialog.value = false
        }
    }

    fun isSelectDeviceCard() {
        if (_listDevices.value.isNotEmpty()) {
            _isSelect.value = true
        } else {
            _isSelect.value = false
        }
    }

    fun deleteDeviceCard(id: List<Int>) {
        viewModelScope.launch {
            dao.deleteDevice(idDevice = id)
            _listDevices.value = emptyList()
            _isSelect.value = false
        }
    }

    fun addDeviceToList(device: DeviceModel) {
        if (!_listDevices.value.contains(device)) {
            _listDevices.value += device
        } else {
            _listDevices.value -= device
        }
        isSelectDeviceCard()
        Log.d("LIST", "${_listDevices.value}, ${_listDevices.value.size}")
    }

    fun cleanField() {
        _textDeviceName.value = ""
        _textMacAddress.value = ""
        _textBroadcastAddress.value = ""
        _isError.value.fill(false)
    }
}