package by.coolightman.notes.broadcastReceiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import by.coolightman.notes.util.NOTIFICATION_ID_EXTRA

class OkBtNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = intent.getIntExtra(NOTIFICATION_ID_EXTRA, 0)
        notificationManager.cancel(notificationId)
    }
}