package com.teclast_korea.payhere_entry.data.utils

import android.content.Context
import android.content.Intent
import com.teclast_korea.payhere_entry.data.model.InstalledApp


fun getInstalledNonPayhereApps(context: Context): List<InstalledApp> {
    val pm = context.packageManager

    // 1) Create an Intent to query apps that appear in the launcher (QuickStep)
    val launcherIntent = Intent(Intent.ACTION_MAIN, null).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
    }

    // 2) Query apps that handle this launcher intent
    val resolveInfos = pm.queryIntentActivities(launcherIntent, 0)

    // 3) Extract the distinct package names of those launchable apps
    val distinctLaunchablePackages = resolveInfos.map { it.activityInfo.packageName }.distinct()

    // 4) Filter out any that contain "payhere"
    val filteredPackages = distinctLaunchablePackages.filter {
        !it.contains("payhere", ignoreCase = true)
    }

    // 5) Build InstalledApp list from these packages
    return filteredPackages.mapNotNull { pkg ->
        try {
            val appInfo = pm.getApplicationInfo(pkg, 0)
            val label = pm.getApplicationLabel(appInfo).toString()
            val icon = pm.getApplicationIcon(pkg)
            InstalledApp(
                packageName = pkg,
                appName = label,
                icon = icon
            )
        } catch (e: Exception) {
            // In case of any NameNotFoundException or other errors,
            // just return null to skip it.
            null
        }
    }
}