package com.example.fee_reminder.notifications

import android.content.Context
import com.example.fee_reminder.data.DatabaseProvider
import kotlinx.coroutines.flow.first
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fee_reminder.MainActivity
import android.util.Log
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

class FeeReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        Log.d("FeeReminder", "Worker Started")

        val dao = DatabaseProvider
            .getDatabase(applicationContext)
            .studentDao()

        val students = dao
            .getAllStudents()
            .first()

        val pendingStudents = students.filter {

            !it.feePaid &&
                    it.nextDueDate <= System.currentTimeMillis()

        }

        if (pendingStudents.isNotEmpty()) {

            val message = pendingStudents
                .take(5)
                .joinToString("\n") {
                    "• ${it.name} (${it.batchTiming})"
                }

            val finalMessage =
                if (pendingStudents.size > 5) {
                    "$message\n+${pendingStudents.size - 5} more students"
                } else {
                    message
                }

            val intent = Intent(applicationContext, MainActivity::class.java)

            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(
                applicationContext,
                NotificationHelper.CHANNEL_ID
            )
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Fee Reminder")
                .setContentText("${pendingStudents.size} students have pending fees")
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(finalMessage)
                )
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            if (
                Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {

                NotificationManagerCompat
                    .from(applicationContext)
                    .notify(1001, notification)

            }
        }
        return Result.success()


    }
}