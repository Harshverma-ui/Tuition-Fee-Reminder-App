package com.example.fee_reminder.ui.screens
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import com.example.fee_reminder.ui.components.StatCard
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fee_reminder.ui.navigation.BottomBar
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fee_reminder.viewmodel.StudentViewModel
import com.example.fee_reminder.viewmodel.StudentViewModelFactory
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material.icons.rounded.Event
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.material.icons.rounded.Add

@Composable
fun DashboardScreen(
    navController: NavController
) {
    val application = LocalContext.current.applicationContext as Application

    val viewModel: StudentViewModel = viewModel(
        factory = StudentViewModelFactory(application)
    )

    val totalStudents by viewModel.totalStudents.collectAsState(initial = 0)

    val paidStudents by viewModel.paidStudents.collectAsState(initial = 0)

    val pendingStudents by viewModel.pendingStudents.collectAsState(initial = 0)

    val dueTodayStudents by viewModel.dueTodayStudents.collectAsState(initial = 0)

    Scaffold(


        bottomBar = {
            BottomBar(navController)
        },

        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    navController.navigate("add_student")
                }
            ) {

                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Student"
                )

            }

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)

        ) {

            Text(
                text = "Fee Reminder",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(

                text = " Have a Good Day..! ",

                style = MaterialTheme.typography.bodyLarge

            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                StatCard(
                    title = "Students",
                    value = totalStudents.toString(),
                    icon = Icons.Rounded.Groups
                )

                StatCard(
                    title = "Paid",
                    value = paidStudents.toString(),
                    icon = Icons.Rounded.CheckCircle
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                StatCard(
                    title = "Pending",
                    value = pendingStudents.toString(),
                    icon = Icons.Rounded.Warning
                )

                StatCard(
                    title = "Due Today",
                    value = dueTodayStudents.toString(),
                    icon = Icons.Rounded.Event
                )

            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                onClick = {
                    navController.navigate("students/today_pending")
                }
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Today's Pending Fees",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (dueTodayStudents > 0) {

                        Text(
                            text = "$dueTodayStudents Students Need Attention",
                            style = MaterialTheme.typography.bodyLarge
                        )

                    } else {

                        Text(
                            text = "No pending fees today.",
                            style = MaterialTheme.typography.bodyLarge
                        )

                    }

                }

            }

        }

    }

}