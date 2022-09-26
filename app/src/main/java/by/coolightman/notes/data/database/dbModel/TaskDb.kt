package by.coolightman.notes.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val color: Long,
    val isImportant: Boolean,
    val createdAt: Long,
    val editedAt: Long,
    val isEdited: Boolean,
    val isActive: Boolean,
    val isHidden: Boolean
)
