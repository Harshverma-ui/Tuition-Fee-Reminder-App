package com.example.fee_reminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    val className: String,

    val batchTiming: String,

    val phone: String,

    val monthlyFee: Int,
    val collectionDate: String = "",

    val lastPaidDate: Long,

    val nextDueDate: Long,

    val feePaid: Boolean,
    val admissionDate: String = ""
)