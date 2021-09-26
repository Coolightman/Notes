package com.example.noteskotlinroom.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val title: String,
    val description: String,
    val dayOfWeek: Int,
    val priority: Int
) {
    constructor(title: String, description: String, dayOfWeek: Int, priority: Int) : this(
        0,
        title,
        description,
        dayOfWeek,
        priority
    )

}

