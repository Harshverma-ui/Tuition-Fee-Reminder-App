package com.example.fee_reminder.excel

import android.content.Context
import android.net.Uri
import com.example.fee_reminder.data.Student
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

object ExcelImporter {

    fun importStudents(
        context: Context,
        uri: Uri
    ): List<Student> {

        val students = mutableListOf<Student>()

        val inputStream =
            context.contentResolver.openInputStream(uri)

        val workbook = XSSFWorkbook(inputStream)

        val sheet = workbook.getSheetAt(0)

        val formatter =
            SimpleDateFormat(
                "dd-MMM-yyyy",
                Locale.ENGLISH
            )


        // Skip Header Row
        for (rowIndex in 1..sheet.lastRowNum) {

            val row = sheet.getRow(rowIndex)
                ?: continue

            val name =
                row.getCell(0)?.toString()?.trim().orEmpty()

            val className =
                row.getCell(1)?.toString()?.trim().orEmpty()

            val batch =
                row.getCell(2)?.toString()?.trim().orEmpty()

            val phone =
                row.getCell(3)?.toString()?.trim().orEmpty()

            val fee =
                row.getCell(4)?.numericCellValue?.toInt()
                    ?: 0

            val lastPaid =
                formatter.parse(
                    row.getCell(5)?.toString()?.trim()
                )?.time ?: System.currentTimeMillis()

            val nextDue =
                lastPaid + TimeUnit.DAYS.toMillis(30)

            students.add(

                Student(

                    name = name,

                    className = className,

                    batchTiming = batch,

                    phone = phone,

                    monthlyFee = fee,

                    lastPaidDate = lastPaid,

                    nextDueDate = nextDue,

                    feePaid = false

                )

            )

        }

        workbook.close()

        inputStream?.close()

        return students

    }

}