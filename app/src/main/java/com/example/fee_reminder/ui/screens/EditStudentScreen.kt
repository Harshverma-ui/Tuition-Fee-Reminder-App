package com.example.fee_reminder.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fee_reminder.data.Student
import com.example.fee_reminder.viewmodel.StudentViewModel
import com.example.fee_reminder.viewmodel.StudentViewModelFactory

@Composable
fun EditStudentScreen(
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

    if (student == null) {
        CircularProgressIndicator()
        return
    }

    var name by remember { mutableStateOf(student!!.name) }
    var className by remember { mutableStateOf(student!!.className) }
    var batch by remember { mutableStateOf(student!!.batchTiming) }
    var phone by remember { mutableStateOf(student!!.phone) }
    var fee by remember { mutableStateOf(student!!.monthlyFee.toString()) }
    var collectionDate by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            "Edit Student",
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

        OutlinedTextField(
            value = batch,
            onValueChange = { batch = it },
            label = { Text("Batch") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = fee,
            onValueChange = { fee = it },
            label = { Text("Monthly Fee") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

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

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                val updatedStudent = Student(
                    feePaid = student!!.feePaid,
                    id = student!!.id,
                    name = name,
                    className = className,
                    batchTiming = batch,
                    phone = phone,
                    monthlyFee = fee.toInt(),
                    collectionDate = collectionDate,
                    lastPaidDate = student!!.lastPaidDate,
                    nextDueDate = student!!.nextDueDate
                )

                viewModel.updateStudent(updatedStudent)

                navController.popBackStack()

            }
        ) {

            Text("Update Student")

        }

    }

}