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

    private lateinit var repository: SelectedAppRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(this)
        repository = SelectedAppRepository(db.selectedAppDao())

        lifecycleScope.launch {
            val selectedApp = repository.getSelectedApp()
            if (selectedApp == null) {
                // Show selection UI
                setContent {
                    val installedApps = getInstalledApps(this@MainActivity)
                    AppSelectionScreen(
                        installedApps = installedApps,
                        onAppSelected = { packageName ->
                            lifecycleScope.launch {
                                repository.setSelectedApp(packageName)
                                launchApp(packageName)
                            }
                        }
                    )
                }
            } else {
                // We have a selected app, so launch it right away
                launchApp(selectedApp.packageName)
                // Optionally finish this activity so user doesn’t return here
                finish()
            }
        }
    }

    private fun launchApp(packageName: String) {
        try {
            val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
            if (launchIntent != null) {
                startActivity(launchIntent)
            } else {
                // Show fallback UI or message if the app can’t be launched
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}