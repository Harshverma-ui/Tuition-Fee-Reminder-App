package com.example.fee_reminder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fee_reminder.viewmodel.StudentViewModel
import com.example.fee_reminder.data.Student
import android.app.Application
import androidx.compose.ui.platform.LocalContext
import com.example.fee_reminder.viewmodel.StudentViewModelFactory
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentScreen() {

    val application = LocalContext.current.applicationContext as Application

    val viewModel: StudentViewModel = viewModel(
        factory = StudentViewModelFactory(application)
    )
    var collectionDate by remember {
        mutableStateOf("")
    }
    var admissionDate by remember {
        mutableStateOf("")
    }
    var name by remember { mutableStateOf("") }
    var className by remember { mutableStateOf("") }
    var batchTiming by remember { mutableStateOf("") }
    val batchList = listOf(

        "Morning 7-8",
        "Morning 8-9",
        "Morning 9-10",
        "Morning 10-11",
        "Morning 11-12",

        "Evening 3-4",
        "Evening 4-5",
        "Evening 5-6",
        "Evening 6-7",
        "Evening 7-8",
        "Evening 8-9",
        "Evening 9-10"

    )

    var expanded by remember {
        mutableStateOf(false)
    }
    var phone by remember { mutableStateOf("") }
    var monthlyFee by remember { mutableStateOf("") }
    var nextDueDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Add Student",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Student Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = className,
            onValueChange = { className = it },
            label = { Text("Class") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {

            OutlinedTextField(
                value = batchTiming,
                onValueChange = { },
                readOnly = true,
                label = { Text("Batch") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {

                batchList.forEach { batch ->

                    DropdownMenuItem(

                        text = {
                            Text(batch)
                        },

                        onClick = {

                            batchTiming = batch

                            expanded = false

                        }

                    )

                }

            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = monthlyFee,
            onValueChange = { monthlyFee = it },
            label = { Text("Monthly Fee") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = collectionDate,
            onValueChange = {
                collectionDate = it
            },
            label = {
                Text("Collection Date")
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = admissionDate,
            onValueChange = {
                admissionDate = it
            },
            label = {
                Text("Admission Date")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = nextDueDate,
            onValueChange = { nextDueDate = it },
            label = { Text("First Due Date (dd/MM/yyyy)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                if (
                    name.isBlank() ||
                    className.isBlank() ||
                    batchTiming.isBlank() ||
                    phone.isBlank() ||
                    monthlyFee.isBlank() ||
                    nextDueDate.isBlank()

                ) {
                    return@Button
                }

                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                val dueDateMillis = try {
                    formatter.parse(nextDueDate)?.time
                } catch (e: Exception) {
                    null
                }

                if (dueDateMillis == null) {
                    return@Button
                }

                val student = Student(
                    name = name,
                    className = className,
                    batchTiming = batchTiming,
                    phone = phone,
                    monthlyFee = monthlyFee.toInt(),
                    collectionDate = collectionDate,
                    admissionDate = admissionDate,
                    lastPaidDate = 0L,
                    nextDueDate = dueDateMillis,
                    feePaid = false
                )

                viewModel.insertStudent(student)

                name = ""
                className = ""
                batchTiming = ""
                phone = ""
                monthlyFee = ""
                collectionDate = ""
                admissionDate = ""

                nextDueDate = ""

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Student")
        }

    }
}