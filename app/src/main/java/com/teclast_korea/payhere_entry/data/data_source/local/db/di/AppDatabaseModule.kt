package com.teclast_korea.payhere_entry.data.data_source.local.db.di

import android.content.Context
import androidx.room.Room
import com.teclast_korea.payhere_entry.data.data_source.local.db.AppDatabase
import com.teclast_korea.payhere_entry.data.data_source.local.db.selected_app.SelectedAppDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDatabaseModule {

    @Provides
    fun provideSelectedAppDao(appDatabase: AppDatabase) : SelectedAppDao {
        return appDatabase.selectedAppDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }
}