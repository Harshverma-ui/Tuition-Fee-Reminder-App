package com.example.fee_reminder.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Dashboard : Screen(
        "dashboard",
        "Home",
        Icons.Default.Home
    )

    object Students : Screen(
        "students",
        "Students",
        Icons.Default.People
    )

    object Reports : Screen(
        "reports",
        "Reports",
        Icons.Default.BarChart
    )

    object Settings : Screen(
        "settings",
        "Settings",
        Icons.Default.Settings
    )
}