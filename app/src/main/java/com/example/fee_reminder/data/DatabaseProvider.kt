package com.example.fee_reminder.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {

        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "fee_reminder_database"
            )
                .fallbackToDestructiveMigration(true)
                .build()

            INSTANCE = instance

            instance
        }
    }
}