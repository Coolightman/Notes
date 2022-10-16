package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.repository.NotificationRepository
import java.util.*
import javax.inject.Inject

class CreateNotificationUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    operator fun invoke(id: Long, text: String, time: Calendar) =
        repository.createNotification(id.toInt(), text, time.timeInMillis)
}
