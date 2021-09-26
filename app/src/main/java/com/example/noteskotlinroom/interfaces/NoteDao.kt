package com.example.noteskotlinroom.interfaces

import androidx.room.*
import com.example.noteskotlinroom.models.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg notes: Note)

    @Query("SELECT * FROM notes ORDER BY priority")
    fun getAll(): List<Note>

    @Update
    fun updateAll(vararg notes: Note)

    @Delete
    fun deleteAll(vararg notes: Note)

    @Query("DELETE FROM notes")
    fun clearBase()
}