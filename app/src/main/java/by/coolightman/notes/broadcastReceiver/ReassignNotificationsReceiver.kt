package by.coolightman.notes.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import by.coolightman.notes.domain.repository.NotificationRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReassignNotificationsReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NotesTAG", "ReassignNotificationsReceiver")
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                reassignNotifications()
            }
            Intent.ACTION_MY_PACKAGE_REPLACED -> {
                reassignNotifications()
            }
        }
    }

    private fun reassignNotifications() {
        CoroutineScope(Dispatchers.Main).launch {
            val list = notificationRepository.getAll()
            list.forEach { notificationRepository.update(it) }
        }
    }
}