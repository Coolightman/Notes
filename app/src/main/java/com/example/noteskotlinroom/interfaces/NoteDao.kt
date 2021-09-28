package com.example.noteskotlinroom.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noteskotlinroom.entities.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg notes: Note)

    @Query("select * from notes order by priority")
    fun getAll(): LiveData<List<Note>>

    @Update
    fun updateAll(vararg notes: Note)

    @Delete
    fun deleteAll(vararg notes: Note)

    @Query("delete from notes")
    fun clearBase()
}