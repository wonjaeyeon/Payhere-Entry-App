package com.teclast_korea.payhere_entry.data.repository

import com.teclast_korea.payhere_entry.data.data_source.local.db.selected_app.SelectedAppDao
import com.teclast_korea.payhere_entry.data.data_source.local.db.selected_app.SelectedAppEntity

class SelectedAppRepository(private val selectedAppDao: SelectedAppDao) {

    suspend fun getSelectedApp(): SelectedAppEntity? = selectedAppDao.getSelectedApp()

    suspend fun setSelectedApp(packageName: String) {
        val entity = SelectedAppEntity(id = 0, packageName = packageName)
        selectedAppDao.setSelectedApp(entity)
    }
}