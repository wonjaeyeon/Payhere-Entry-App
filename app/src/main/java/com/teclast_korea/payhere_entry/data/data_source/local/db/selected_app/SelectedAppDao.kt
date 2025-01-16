package com.teclast_korea.payhere_entry.data.data_source.local.db.selected_app

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SelectedAppDao {
    @Query("SELECT * FROM selected_app WHERE id = 0")
    suspend fun getSelectedApp(): SelectedAppEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSelectedApp(selectedApp: SelectedAppEntity)

    @Query("DELETE FROM selected_app WHERE id = 0")
    suspend fun clearSelectedApp()
}