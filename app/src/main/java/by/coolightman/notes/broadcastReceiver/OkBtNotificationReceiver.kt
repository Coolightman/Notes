package by.coolightman.notes.broadcastReceiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import by.coolightman.notes.util.TASK_ID_EXTRA
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OkBtNotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getIntExtra(TASK_ID_EXTRA, -1)
        if (taskId != -1) {
            notificationManager.cancel(taskId)
        }
    }
}