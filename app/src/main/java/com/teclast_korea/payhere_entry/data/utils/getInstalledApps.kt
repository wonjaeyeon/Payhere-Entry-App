package com.teclast_korea.payhere_entry.data.utils

import android.content.Context
import com.teclast_korea.payhere_entry.data.model.InstalledApp

//fun getInstalledApps(context: Context): List<String> {
//    val pm = context.packageManager
//    val installedApps = pm.getInstalledApplications(0)
//    return installedApps.map { it.packageName }
//}

fun getInstalledApps(context: Context): List<InstalledApp> {
    val pm = context.packageManager
    val allApps = pm.getInstalledApplications(0)

    // Filter: only apps whose package name contains "teclast korea" (case-insensitive).
    // Adjust the substring as needed (e.g., "teclast_korea" if that’s the actual substring).
    val filtered = allApps.filter { appInfo ->
        appInfo.packageName.contains("teclast", ignoreCase = true)
    }

    // For each filtered app, build our data class (packageName + icon).
    return filtered.map { appInfo ->
        InstalledApp(
            packageName = appInfo.packageName,
            icon = pm.getApplicationIcon(appInfo)
        )
    }
}