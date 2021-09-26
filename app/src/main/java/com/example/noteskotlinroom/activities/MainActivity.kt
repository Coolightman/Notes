package com.example.noteskotlinroom.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.noteskotlinroom.R
import com.example.noteskotlinroom.adapters.NotesAdapter
import com.example.noteskotlinroom.databases.NotesDatabase
import com.example.noteskotlinroom.models.Note
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val notes = mutableListOf<Note>()
    private lateinit var db: NotesDatabase
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        getDatabase()

        getNotesFromDb()
        createNotesAdapter()
        listeners()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getNotesFromDb() {
        Executors.newSingleThreadExecutor().execute {
            val notesFromDb = db.noteDao().getAll()
            Handler(Looper.getMainLooper()).post {
                notes.clear()
                notes.addAll(notesFromDb)
                notesAdapter.notifyDataSetChanged()
            }
        }

    }

    private fun getDatabase() {
        db = Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java, getString(R.string.database_notes_name)
        ).build()
    }

    private fun swipeListener() {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.END) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                removeNote(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes)
    }

    private fun removeNote(position: Int) {
        val note = notes[position]
        Executors.newSingleThreadExecutor().execute {
            db.noteDao().deleteAll(note)
            Handler(Looper.getMainLooper()).post {
                getNotesFromDb()
            }
        }
    }

    private fun listeners() {
        swipeListener()
        buttonAddNote.setOnClickListener {
            startActivity(Intent(applicationContext, CreateNoteActivity::class.java))
        }
    }

    private fun createNotesAdapter() {
        notesAdapter = NotesAdapter(notes) {
            onItemClick(it)
        }
        recyclerViewNotes.layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewNotes.adapter = notesAdapter
    }

    private fun onItemClick(note: Note) {
        val intent = Intent(applicationContext, EditNoteActivity::class.java)
        intent.putExtra("noteId", note.id)
        intent.putExtra("noteTitle", note.title)
        intent.putExtra("noteDescription", note.description)
        intent.putExtra("noteDayOfWeek", note.dayOfWeek)
        intent.putExtra("notePriority", note.priority)
        startActivity(intent)
    }
}