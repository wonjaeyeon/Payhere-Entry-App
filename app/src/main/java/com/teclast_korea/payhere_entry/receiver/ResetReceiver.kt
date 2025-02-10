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

@AndroidEntryPoint
class ResetReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: SelectedAppRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "com.example.yourapp.ACTION_RESET") {
            // Clear the DB entry on an IO thread
            CoroutineScope(Dispatchers.IO).launch {
                repository.clearSelectedApp()
                // Optionally log/Toast
                Log.i("ResetReceiver", "Cleared selected app")
            }
        }
    }
}