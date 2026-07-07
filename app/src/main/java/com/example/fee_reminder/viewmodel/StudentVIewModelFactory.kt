package com.example.fee_reminder.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StudentViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            return StudentViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}