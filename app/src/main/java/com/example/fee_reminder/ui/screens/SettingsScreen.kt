package com.example.fee_reminder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.app.Application
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fee_reminder.excel.ExcelImporter
import com.example.fee_reminder.viewmodel.StudentViewModel
import com.example.fee_reminder.viewmodel.StudentViewModelFactory
import android.widget.Toast

@Composable
fun SettingsScreen(
) {

    val application =
        LocalContext.current.applicationContext as Application

    val context = LocalContext.current

    val viewModel: StudentViewModel = viewModel(
        factory = StudentViewModelFactory(application)
    )
    val excelPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->

        if (uri != null) {

            val students =
                ExcelImporter.importStudents(
                    context,
                    uri
                )

            viewModel.importStudents(students) { result ->

                val (imported, updated, skipped) = result

                Toast.makeText(
                    context,
                    """
                            Imported : $imported
                            Updated : $updated
                            Skipped : $skipped
        """.trimIndent(),
                    Toast.LENGTH_LONG
                ).show()

            }

        }

    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                excelPicker.launch(
                    arrayOf(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                    )
                )

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📥 Import Students")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📤 Export Students")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📄 Download Sample Excel")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🔔 Notification Time")
        }

    }

}