package com.teclast_korea.payhere_entry.ui

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.teclast_korea.payhere_entry.data.utils.getInstalledNonPayhereApps
import com.teclast_korea.payhere_entry.data.utils.getInstalledPayhereApps
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
        val installedPayhereApps = getInstalledPayhereApps(context)
        val installedOtherApps = getInstalledNonPayhereApps(context)
        AppSelectionScreen(
            payhereApps = installedPayhereApps,
            otherApps = installedOtherApps,
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
                // As Soon as the app is launched, finish this activity. even from Recent Apps.
                (context as? Activity)?.finishAndRemoveTask()
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
