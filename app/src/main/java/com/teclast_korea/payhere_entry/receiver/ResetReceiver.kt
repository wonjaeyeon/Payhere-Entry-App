package com.teclast_korea.payhere_entry.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.teclast_korea.payhere_entry.data.repository.SelectedAppRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//@AndroidEntryPoint
//class ResetReceiver : BroadcastReceiver() {
//
//    @Inject
//    lateinit var repository: SelectedAppRepository
//
//    override fun onReceive(context: Context, intent: Intent) {
//        if (intent.action == "com.example.yourapp.ACTION_RESET") {
//            // Clear the DB entry on an IO thread
//            CoroutineScope(Dispatchers.IO).launch {
//                repository.clearSelectedApp()
//                // Optionally log/Toast
//                Log.i("ResetReceiver", "Cleared selected app")
//            }
//        }
//    }
//}

@AndroidEntryPoint
class ResetReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: SelectedAppRepository

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_RESET -> {
                // Clear the DB entry on an IO thread
                CoroutineScope(Dispatchers.IO).launch {
                    repository.clearSelectedApp()
                    Log.i("ResetReceiver", "Cleared selected app")
                }
            }

            ACTION_SET_HOME_APP -> {
                // We'll read the package name from an extra. Example: "packageName"
                val packageName = intent.getStringExtra(EXTRA_PACKAGE_NAME)
                if (!packageName.isNullOrEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        repository.setSelectedApp(packageName)
                        Log.i("ResetReceiver", "Set selected app to $packageName")
                    }
                } else {
                    Log.w("ResetReceiver", "No packageName extra found in ACTION_SET_HOME_APP broadcast.")
                }
            }
        }
    }

    companion object {
        // Unique actions
        const val ACTION_RESET = "ACTION_RESET"
        const val ACTION_SET_HOME_APP = "ACTION_SET_HOME_APP"

        // Key for the package name extra
        const val EXTRA_PACKAGE_NAME = "packageName"
    }
}