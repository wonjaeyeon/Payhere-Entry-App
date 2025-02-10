package com.teclast_korea.payhere_entry.data.repository.di

import com.teclast_korea.payhere_entry.data.data_source.local.db.selected_app.SelectedAppDao
import com.teclast_korea.payhere_entry.data.repository.SelectedAppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSelectedAppRepository(
        selectedAppDao: SelectedAppDao
    ): SelectedAppRepository {
        return SelectedAppRepository(selectedAppDao)
    }
}