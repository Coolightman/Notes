package by.coolightman.notes.broadcastReceiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import by.coolightman.notes.util.NOTIFICATION_ID_EXTRA
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OkBtNotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(NOTIFICATION_ID_EXTRA, 0)
        notificationManager.cancel(notificationId)
    }
}