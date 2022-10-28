package by.coolightman.notes.data.local.dbModel.relations

import androidx.room.Embedded
import androidx.room.Relation
import by.coolightman.notes.data.local.dbModel.NotificationDb
import by.coolightman.notes.data.local.dbModel.TaskDb

data class TaskWithNotificationsDb(
    @Embedded val taskDb: TaskDb,
    @Relation(
        parentColumn = "id",
        entityColumn = "task_id"
    )
    val notificationsDb: List<NotificationDb>
)
