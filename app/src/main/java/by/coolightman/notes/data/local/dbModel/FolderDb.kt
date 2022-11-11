package by.coolightman.notes.data.local.dbModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folders")
data class FolderDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    @ColumnInfo(name = "color_index", defaultValue = "0") val colorIndex: Int,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "is_in_trash") val isInTrash: Boolean,
    @ColumnInfo(name = "is_pinned") val isPinned: Boolean,
    @ColumnInfo(name = "external_folder_id", defaultValue = "0") val externalFolderId: Long,
)
