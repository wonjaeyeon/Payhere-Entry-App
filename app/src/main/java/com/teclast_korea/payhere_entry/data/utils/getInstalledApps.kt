package com.teclast_korea.payhere_entry.data.utils

import android.content.Context
import android.content.pm.ApplicationInfo

fun getInstalledApps(context: Context): List<String> {
    val pm = context.packageManager
    val installedApps = pm.getInstalledApplications(0)
    return installedApps.map { it.packageName }
}