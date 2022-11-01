package by.coolightman.notes.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import by.coolightman.notes.domain.usecase.notifications.ReassignNotificationsUseCase
import by.coolightman.notes.domain.usecase.preferences.GetBooleanPreferenceUseCase
import by.coolightman.notes.util.SHOW_UPDATE_DIALOG_EXTRA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UpdateAppOrRebootDeviceReceiver : BroadcastReceiver() {

    @Inject
    lateinit var reassignNotificationsUseCase: ReassignNotificationsUseCase

    @Inject
    lateinit var putBooleanPreferenceUseCase: GetBooleanPreferenceUseCase

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NotesTAG", "ReassignNotificationsReceiver")
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                reassignNotifications()
            }
            Intent.ACTION_MY_PACKAGE_REPLACED -> {
                reassignNotifications()
                showUpdateDialog()
            }
        }
    }

    private fun showUpdateDialog() {
        CoroutineScope(Dispatchers.IO).launch {
            putBooleanPreferenceUseCase(SHOW_UPDATE_DIALOG_EXTRA, true)
        }
    }

    private fun reassignNotifications() {
        CoroutineScope(Dispatchers.IO).launch {
            reassignNotificationsUseCase()
        }
    }
}