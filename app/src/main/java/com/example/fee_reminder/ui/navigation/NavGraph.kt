package com.example.fee_reminder.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.fee_reminder.ui.screens.*
import androidx.navigation.navArgument


@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "dashboard"
    ) {

        composable("dashboard") {
            DashboardScreen(navController)
        }

        composable(
            route = "students/{filter}",
            arguments = listOf(
                navArgument("filter") {
                    defaultValue = "all"
                }
            )
        ) { backStackEntry ->

            val filter =
                backStackEntry.arguments?.getString("filter") ?: "all"

            StudentsScreen(
                navController = navController,
                filter = filter
            )

        }

        composable("students") {

            StudentsScreen(
                navController = navController,
                filter = "all"
            )

        }
        composable("add_student") {
            AddStudentScreen()
        }
        composable(
            route = "student_details/{studentId}"
        ) { backStackEntry ->

            val studentId =
                backStackEntry.arguments?.getString("studentId")?.toInt() ?: 0

            StudentDetailsScreen(
                studentId = studentId,
                navController = navController
            )

        }

        composable(
            route = "edit_student/{studentId}"
        ) { backStackEntry ->

            val studentId =
                backStackEntry.arguments?.getString("studentId")?.toInt() ?: 0

            EditStudentScreen(
                studentId = studentId,
                navController = navController
            )
        }
        composable("settings") {

            SettingsScreen(

            )

        }
        composable("reports") {
            ReportsScreen(navController)
        }

    }

}