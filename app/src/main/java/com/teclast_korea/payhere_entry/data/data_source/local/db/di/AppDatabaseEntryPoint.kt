package com.teclast_korea.payhere_entry.data.data_source.local.db.di

import com.teclast_korea.payhere_entry.data.data_source.local.db.AppDatabase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppDatabaseEntryPoint {
    fun provideAppDatabase(): AppDatabase
}