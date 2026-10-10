package com.example.woofon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.woofon.data.viewmodels.MainViewModel
import com.example.woofon.ui.theme.WoofOnTheme

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WoofOnTheme(darkTheme = mainViewModel.isDarkTheme.collectAsStateWithLifecycle().value) {
                NavPage()
            }
        }
    }
}
