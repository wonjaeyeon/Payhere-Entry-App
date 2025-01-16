package com.teclast_korea.payhere_entry.data.data_source.local.db.selected_app

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selected_app")
data class SelectedAppEntity(
    @PrimaryKey val id: Int = 0,  // We have only 1 entry
    val packageName: String
)