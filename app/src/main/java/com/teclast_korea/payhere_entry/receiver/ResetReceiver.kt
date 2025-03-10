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
        when (intent.action) {
            ACTION_RESET -> {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.clearSelectedApp()
                    Log.i(TAG, "Cleared selected app")
                }
            }

            ACTION_SET_HOME_APP -> {
                val packageName = intent.getStringExtra(EXTRA_PACKAGE_NAME)
                if (!packageName.isNullOrEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        repository.setSelectedApp(packageName)
                        Log.i(TAG, "Set selected app to $packageName")
                    }
                } else {
                    Log.w(TAG, "No packageName provided for ACTION_SET_HOME_APP.")
                }
            }

            ACTION_TURN_OFF -> {
                // 여기서 "Entry App" 프로세스를 자발적 종료하는 로직
                Log.i(TAG, "Received ACTION_TURN_OFF - attempting to exit Entry App")
                android.os.Process.killProcess(android.os.Process.myPid())
            }
        }
    }

    companion object {
        const val ACTION_RESET = "ACTION_RESET"
        const val ACTION_SET_HOME_APP = "ACTION_SET_HOME_APP"
        const val ACTION_TURN_OFF = "ACTION_TURN_OFF"

        const val EXTRA_PACKAGE_NAME = "packageName"

        private const val TAG = "ResetReceiver"
    }
}