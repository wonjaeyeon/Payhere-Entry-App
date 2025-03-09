package com.teclast_korea.payhere_entry.data.data_source.local.db.di

import android.content.Context
import androidx.room.Room
import com.teclast_korea.payhere_entry.data.data_source.local.db.AppDatabase
import com.teclast_korea.payhere_entry.data.data_source.local.db.selected_app.SelectedAppDao
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {

    @Provides
    fun provideSelectedAppDao(db: AppDatabase): SelectedAppDao {
        return db.selectedAppDao()
    }

    @Provides
    @Singleton  // Make sure it's from the same annotation library as the rest
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "auto_entry_db"
        ).build()
    }


}