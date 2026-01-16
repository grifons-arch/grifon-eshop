package com.example.grifon

import androidx.activity.ComponentActivity

data class TopBarDestination(
    val label: String,
    val activityClass: Class<out ComponentActivity>,
)
