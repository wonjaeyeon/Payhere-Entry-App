package com.teclast_korea.payhere_entry.ui

import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.teclast_korea.payhere_entry.data.repository.SelectedAppRepository
import com.teclast_korea.payhere_entry.data.utils.getInstalledApps
@Composable
fun MainScreen(repository: SelectedAppRepository) {
    val context = LocalContext.current
    var showSelectionScreen by remember { mutableStateOf(false) }
    // 1) Keep track of the newly selected package
    var newlySelectedPackage by remember { mutableStateOf<String?>(null) }

    // 2) When newlySelectedPackage changes, do the side-effect
    LaunchedEffect(newlySelectedPackage) {
        newlySelectedPackage?.let { packageName ->
            repository.setSelectedApp(packageName)
            launchApp(context, packageName)
            // You could also reset newlySelectedPackage = null if you only want it done once
        }
    }

    if (showSelectionScreen) {
        val installedApps = getInstalledApps(context)
        AppSelectionScreen(
            installedApps = installedApps,
            onAppSelected = { packageName ->
                // Instead of calling LaunchedEffect here, just store the package
                newlySelectedPackage = packageName
            }
        )
    } else {
        Button(onClick = { showSelectionScreen = true }) {
            Text("Switch the entering app")
        }
    }
}

// Normal function, no composable calls here
fun launchApp(context: Context, packageName: String) {
    val pm = context.packageManager
    val launchIntent = pm.getLaunchIntentForPackage(packageName)
    launchIntent?.let { context.startActivity(it) }
}