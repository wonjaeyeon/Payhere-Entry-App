package com.teclast_korea.payhere_entry.ui

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.teclast_korea.payhere_entry.data.repository.SelectedAppRepository
import com.teclast_korea.payhere_entry.data.utils.getInstalledApps
import com.teclast_korea.payhere_entry.ui.viewmodel.MainViewModel


@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // Observe the DB in real-time
    val selectedApp = viewModel.selectedAppFlow.collectAsState().value

    if (selectedApp == null) {
        // Show selection UI
        val installedApps = getInstalledApps(context)
        AppSelectionScreen(
            installedApps = installedApps,
            onAppSelected = { packageName ->
                viewModel.selectApp(packageName)
            }
        )
    } else {
        // We have a selected app in the DB, so launch it

        LaunchedEffect(selectedApp.packageName) {
            val launchIntent = context.packageManager.getLaunchIntentForPackage(selectedApp.packageName)
            if (launchIntent != null) {
                context.startActivity(launchIntent)
                // Optionally finish your Activity so the user can't go 'back' here
                (context as? Activity)?.finish()
            }
        }

        // Placeholder UI while launching
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Launching ${selectedApp.packageName}...")
        }
    }
}
