package com.example.woofon.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class Pages {
    WOL,
    SSH
}
class MainViewModel : ViewModel() {
    private val _isDarkTheme = MutableStateFlow(false)
    private val _idPage = MutableStateFlow(0)
    private val _selected = MutableStateFlow(Pages.WOL)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()
    val idPage: StateFlow<Int> = _idPage.asStateFlow()
    val selected: StateFlow<Pages> = _selected.asStateFlow()

    fun switchingTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun navigationPage(page: Int) {
        _idPage.value = page

        when(_idPage.value) {
            0 -> {
                _selected.value = Pages.WOL
            }
            1 -> {
                _selected.value = Pages.SSH
            }
        }
        Log.d("SELECTED", "${_selected.value}")
    }
}