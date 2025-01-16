package com.teclast_korea.payhere_entry.data.data_source.local.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.teclast_korea.payhere_entry.data.data_source.local.db.selected_app.SelectedAppDao
import com.teclast_korea.payhere_entry.data.data_source.local.db.selected_app.SelectedAppEntity

@Database(entities = [SelectedAppEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun selectedAppDao(): SelectedAppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "auto_entry_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}