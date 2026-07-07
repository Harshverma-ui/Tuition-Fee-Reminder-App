package com.example.fee_reminder.repository

import com.example.fee_reminder.data.Student
import com.example.fee_reminder.data.StudentDao
import kotlinx.coroutines.flow.Flow

class StudentRepository(
    private val studentDao: StudentDao
) {

    val allStudents: Flow<List<Student>> =
        studentDao.getAllStudents()

    suspend fun insertStudent(student: Student) {
        studentDao.insertStudent(student)
    }

    suspend fun updateStudent(student: Student) {
        studentDao.updateStudent(student)
    }

    suspend fun deleteStudent(student: Student) {
        studentDao.deleteStudent(student)
    }

    fun getStudentById(studentId: Int): Flow<Student?> {
        return studentDao.getStudentById(studentId)
    }

    suspend fun importStudents(
        students: List<Student>
    ): Triple<Int, Int, Int> {

        var imported = 0
        var updated = 0
        var skipped = 0

        students.forEach { student ->

            val existing =
                studentDao.getStudentByPhone(student.phone)

            if (existing == null) {

                studentDao.insertStudent(student)

                imported++

            } else {

                val updatedStudent = student.copy(
                    id = existing.id
                )

                if (updatedStudent != existing) {

                    studentDao.updateStudent(updatedStudent)

                    updated++

                } else {

                    skipped++

                }

            }

        }

        return Triple(
            imported,
            updated,
            skipped
        )

    }
}