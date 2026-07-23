package com.example.fee_reminder.ui.screens


import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import android.app.Application
import androidx.compose.foundation.clickable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fee_reminder.viewmodel.StudentViewModel
import com.example.fee_reminder.viewmodel.StudentViewModelFactory
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentsScreen(
    navController: NavController,
    filter: String
){

    val application = LocalContext.current.applicationContext as Application

    val viewModel: StudentViewModel = viewModel(
        factory = StudentViewModelFactory(application)
    )

    val students by viewModel.allStudents.collectAsState(initial = emptyList())

    val formatter = remember {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    }
    val batchList = listOf(
        "All",
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

    var selectedBatch by remember {
        mutableStateOf("All")
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    var searchText by remember {
        mutableStateOf("")
    }
    val filteredStudents = students.filter { student ->

        val matchesBatch =
            selectedBatch == "All" ||
                    student.batchTiming == selectedBatch

        val matchesSearch =
            student.name.contains(
                searchText,
                ignoreCase = true
            )

        matchesBatch && matchesSearch

    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Search Student")
            },
            placeholder = {
                Text("Enter student name...")
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {

            OutlinedTextField(
                value = selectedBatch,
                onValueChange = { },
                readOnly = true,
                label = {
                    Text("Sort by Batch")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {

                batchList.forEach { batch ->

                    DropdownMenuItem(
                        text = { Text(batch) },
                        onClick = {

                            selectedBatch = batch
                            expanded = false

                        }
                    )

                }

            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(filteredStudents) { student ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("student_details/${student.id}")
                        }
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column {

                                Text(
                                    text = student.name,
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Text(
                                    text = "Batch : ${student.batchTiming}"
                                )

                            }

                            AssistChip(
                                onClick = { },
                                label = {

                                    Text(
                                        if (student.feePaid)
                                            "🟢 Paid"
                                        else
                                            "🔴 Pending"
                                    )

                                }
                            )

                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text("Class : ${student.className}")

                        Text("Monthly Fee : ₹${student.monthlyFee}")


                        Text(
                            text = if (student.collectionDate.isBlank())
                                "Collection : --"
                            else
                                "Collection : ${student.collectionDate}"
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            "Due : ${
                                formatter.format(
                                    Date(student.nextDueDate)
                                )
                            }"
                        )

                    }

                }

            }

        }

    }

}