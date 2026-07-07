package com.example.fee_reminder.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fee_reminder.viewmodel.StudentViewModel
import com.example.fee_reminder.viewmodel.StudentViewModelFactory
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StudentDetailsScreen(
    studentId: Int,
    navController: NavController
) {

    val application = LocalContext.current.applicationContext as Application

    val viewModel: StudentViewModel = viewModel(
        factory = StudentViewModelFactory(application)
    )

    val student by viewModel
        .getStudentById(studentId)
        .collectAsState(initial = null)

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    if (student == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Student Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Name : ${student!!.name}")
        Spacer(modifier = Modifier.height(10.dp))

        Text("Class : ${student!!.className}")
        Spacer(modifier = Modifier.height(10.dp))

        Text("Batch : ${student!!.batchTiming}")
        Spacer(modifier = Modifier.height(10.dp))

        Text("Phone : ${student!!.phone}")
        Spacer(modifier = Modifier.height(10.dp))

        Text("Monthly Fee : ₹${student!!.monthlyFee}")
        Spacer(modifier = Modifier.height(10.dp))

        val formatter = remember {
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        }

        val lastPaid =
            if (student!!.lastPaidDate == 0L)
                "Never"
            else
                formatter.format(Date(student!!.lastPaidDate))

        val nextDue =
            formatter.format(Date(student!!.nextDueDate))

        Text(
            text = if (student!!.feePaid)
                "Status : ✅ Paid"
            else
                "Status : ❌ Pending"
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Last Paid : $lastPaid")

        Text("Next Due : $nextDue")

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {

                val today = System.currentTimeMillis()

                val nextMonth =
                    today + (30L * 24 * 60 * 60 * 1000)

                val updatedStudent = student!!.copy(

                    lastPaidDate = today,

                    nextDueDate = nextMonth,

                    feePaid = true

                )

                viewModel.updateStudent(updatedStudent)

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mark Fee Paid")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                navController.navigate("edit_student/$studentId")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit Student")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                showDeleteDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete Student")
        }
    }

    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {
                showDeleteDialog = false
            },

            title = {
                Text("Delete Student")
            },

            text = {
                Text(
                    "Are you sure you want to delete ${student!!.name}?"
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        viewModel.deleteStudent(student!!)

                        showDeleteDialog = false

                        navController.popBackStack()

                    }
                ) {
                    Text("Delete")
                }

            },

            dismissButton = {

                OutlinedButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text("Cancel")
                }

            }

        )

    }
}