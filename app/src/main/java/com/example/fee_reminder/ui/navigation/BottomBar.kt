package com.example.fee_reminder.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BottomBar(
    navController: androidx.navigation.NavController
) {

    var selected by remember {
        mutableStateOf(0)
    }

    val items = listOf(
        Screen.Dashboard,
        Screen.Students,
        Screen.Reports,
        Screen.Settings
    )

    NavigationBar {

        items.forEachIndexed { index, screen ->

            NavigationBarItem(

                selected = selected == index,

                onClick = {

                    selected = index

                    navController.navigate(screen.route)

                },

                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title
                    )
                },

                label = {
                    Text(screen.title)
                }

            )

        }

    }

}