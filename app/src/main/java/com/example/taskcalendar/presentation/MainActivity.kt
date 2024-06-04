package com.example.taskcalendar.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.taskcalendar.ui.CalendarTheme
import com.example.taskcalendar.util.SetupNavigation
import dagger.hilt.android.AndroidEntryPoint
import com.example.taskcalendar.util.Screen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalendarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    SetupNavigation(
                        startDestination = Screen.HomeScreen.route
                    )
                }
            }
        }
    }
}