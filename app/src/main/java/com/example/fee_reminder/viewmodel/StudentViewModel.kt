package com.example.fee_reminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fee_reminder.data.Student
import com.example.fee_reminder.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.example.fee_reminder.data.DatabaseProvider
import kotlinx.coroutines.flow.map

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StudentRepository

    val allStudents: Flow<List<Student>>

    init {

        val dao = DatabaseProvider
            .getDatabase(application)
            .studentDao()

        repository = StudentRepository(dao)
        allStudents = repository.allStudents


    }
    val totalStudents = allStudents.map { students ->
        students.size
    }

    val paidStudents = allStudents.map { students ->
        students.count { it.feePaid }
    }

    val pendingStudents = allStudents.map { students ->
        students.count { !it.feePaid }
    }

    val dueTodayStudents = allStudents.map { students ->

        val today = System.currentTimeMillis()

        students.count {

            !it.feePaid && it.nextDueDate <= today

        }
    }

    fun insertStudent(student: Student) {
        viewModelScope.launch {
            repository.insertStudent(student)
        }
    }

    fun updateStudent(student: Student) {
        viewModelScope.launch {
            repository.updateStudent(student)
        }
    }

    fun deleteStudent(student: Student) {
        viewModelScope.launch {
            repository.deleteStudent(student)
        }
    }

    fun getStudentById(studentId: Int): Flow<Student?> {
        return repository.getStudentById(studentId)
    }

    fun importStudents(
        students: List<Student>,
        onFinished: (Triple<Int, Int, Int>) -> Unit
    ) {

        viewModelScope.launch {

            val result = repository.importStudents(students)

            onFinished(result)

        }

    }
}