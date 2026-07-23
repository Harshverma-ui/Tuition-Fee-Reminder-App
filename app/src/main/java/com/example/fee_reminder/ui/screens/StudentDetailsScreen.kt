package com.example.fee_reminder.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditCalendar
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fee_reminder.viewmodel.StudentViewModel
import com.example.fee_reminder.viewmodel.StudentViewModelFactory
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
    var showEditDateDialog by remember {
        mutableStateOf(false)
    }

    var editedDate by remember {
        mutableStateOf("")
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

        Text(
            text = if (student!!.collectionDate.isBlank())
                "Collection Date : --"
            else
                "Collection Date : ${student!!.collectionDate}"
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = if (student!!.admissionDate.isBlank())
                "Admission Date : --"
            else
                "Admission Date : ${student!!.admissionDate}"
        )

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Last Paid : $lastPaid"
            )

            IconButton(
                onClick = {

                    editedDate =
                        if (student!!.lastPaidDate == 0L)
                            ""
                        else
                            SimpleDateFormat(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                            ).format(Date(student!!.lastPaidDate))

                    showEditDateDialog = true

                }
            ) {

                Icon(
                    imageVector = Icons.Rounded.EditCalendar,
                    contentDescription = "Edit Last Paid Date"
                )

            }

        }

        Text("Next Due : $nextDue")

        Spacer(modifier = Modifier.height(12.dp))

        Spacer(modifier = Modifier.height(40.dp))

        Button(

            onClick = {

                if (!student!!.feePaid) {

                    val today = System.currentTimeMillis()

                    val nextMonth =
                        today + (30L * 24 * 60 * 60 * 1000)

                    val updatedStudent = student!!.copy(

                        feePaid = true,

                        lastPaidDate = today,

                        nextDueDate = nextMonth

                    )

                    viewModel.updateStudent(updatedStudent)

                } else {

                    val updatedStudent = student!!.copy(

                        feePaid = false

                    )

                    viewModel.updateStudent(updatedStudent)

                }

            },

            modifier = Modifier.fillMaxWidth()

        ) {

            Text(

                if (student!!.feePaid)
                    "Mark Fee Unpaid"
                else
                    "Mark Fee Paid"

            )

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

    if (showEditDateDialog) {

        AlertDialog(

            onDismissRequest = {
                showEditDateDialog = false
            },

            title = {
                Text("Edit Last Paid Date")
            },

            text = {

                OutlinedTextField(
                    value = editedDate,
                    onValueChange = {
                        editedDate = it
                    },
                    label = {
                        Text("dd/MM/yyyy")
                    }
                )

            },

            confirmButton = {

                TextButton(

                    onClick = {

                        try {

                            val formatter =
                                SimpleDateFormat(
                                    "dd/MM/yyyy",
                                    Locale.getDefault()
                                )

                            val lastPaidMillis =
                                formatter.parse(editedDate)?.time
                                    ?: return@TextButton

                            val nextDueMillis =
                                lastPaidMillis + (30L * 24 * 60 * 60 * 1000)

                            val updatedStudent =
                                student!!.copy(

                                    lastPaidDate = lastPaidMillis,

                                    nextDueDate = nextDueMillis,

                                    feePaid = true

                                )

                            viewModel.updateStudent(updatedStudent)

                            showEditDateDialog = false

                        } catch (e: Exception) {

                            // Invalid date format

                        }

                    }

                ) {

                    Text("Save")

                }

            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showEditDateDialog = false
                    }
                ) {
                    Text("Cancel")
                }

            }

        )

    }


}