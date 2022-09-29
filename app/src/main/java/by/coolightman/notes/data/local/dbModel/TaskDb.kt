package by.coolightman.notes.data.local.dbModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    @ColumnInfo(name = "color_index") val colorIndex: Int,
    @ColumnInfo(name = "is_important") val isImportant: Boolean,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "edited_at") val editedAt: Long,
    @ColumnInfo(name = "is_edited") val isEdited: Boolean,
    @ColumnInfo(name = "is_active") val isActive: Boolean,
    @ColumnInfo(name = "is_hidden") val isHidden: Boolean
)
