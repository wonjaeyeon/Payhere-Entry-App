package com.teclast_korea.payhere_entry.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.teclast_korea.payhere_entry.data.data_source.local.db.AppDatabase
import com.teclast_korea.payhere_entry.data.repository.SelectedAppRepository
import com.teclast_korea.payhere_entry.data.utils.getInstalledApps
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint  // If using Hilt, optional
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}