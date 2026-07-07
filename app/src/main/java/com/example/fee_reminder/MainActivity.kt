package com.example.fee_reminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.fee_reminder.ui.navigation.NavGraph

import androidx.activity.result.contract.ActivityResultContracts
import com.example.fee_reminder.ui.theme.FeeReminderTheme
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import com.example.fee_reminder.notifications.NotificationHelper
import com.example.fee_reminder.notifications.FeeReminderWorker
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {



    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {
                // Permission granted
            } else {
                // Permission denied
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                notificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )

            }

        }


        NotificationHelper.createNotificationChannel(this)

        val workRequest =
            PeriodicWorkRequestBuilder<FeeReminderWorker>(
                24,
                TimeUnit.HOURS
            ).build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "fee_reminder_worker",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )

        setContent {
            FeeReminderTheme {
                NavGraph()
            }
        }


    }
}