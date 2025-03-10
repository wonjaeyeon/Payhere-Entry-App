package com.teclast_korea.payhere_entry.data.utils

import android.content.Context
import com.teclast_korea.payhere_entry.data.model.InstalledApp


fun getInstalledPayhereApps(context: Context): List<InstalledApp> {
    val pm = context.packageManager
    val allApps = pm.getInstalledApplications(0)

    // Filter: only apps whose package name contains "payhere"
    // but exclude "com.teclast_korea.payhere_entry"
    val filtered = allApps.filter { appInfo ->
        appInfo.packageName.contains("payhere", ignoreCase = true) &&
                !appInfo.packageName.equals("com.teclast_korea.payhere_entry", ignoreCase = true)
    }

    return filtered.map { appInfo ->
        val label = pm.getApplicationLabel(appInfo).toString()  // Get the friendly name
        InstalledApp(
            packageName = appInfo.packageName,
            appName = label,
            icon = pm.getApplicationIcon(appInfo)
        )
    }
}