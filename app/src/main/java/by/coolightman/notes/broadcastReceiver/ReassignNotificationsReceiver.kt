package by.coolightman.notes.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import by.coolightman.notes.domain.usecase.tasks.CreateNotificationUseCase
import by.coolightman.notes.domain.usecase.tasks.GetAllNotificatedTasksUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReassignNotificationsReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getAllNotificatedTasksUseCase: GetAllNotificatedTasksUseCase

    @Inject
    lateinit var createNotificationUseCase: CreateNotificationUseCase

    override fun onReceive(context: Context, intent: Intent) {
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
            getAllNotificatedTasksUseCase().forEach {
                createNotificationUseCase(
                    id = it.id,
                    text = it.text,
                    time = it.notificationTime
                )
            }
        }
    }
}