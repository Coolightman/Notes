package com.example.noteskotlinroom.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteskotlinroom.adapters.NotesAdapter
import com.example.noteskotlinroom.databinding.ActivityMainBinding
import com.example.noteskotlinroom.entities.Note
import com.example.noteskotlinroom.viewModels.NoteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var notesAdapter: NotesAdapter
    private val notes = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        getNotesFromDb()
        createNotesAdapter()
        listeners()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getNotesFromDb() {
        val notesFromDb = noteViewModel.getAllNotes()
        notesFromDb.observe(this, {
            notes.clear()
            notes.addAll(it)
            notesAdapter.notifyDataSetChanged()
        })
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
                removeNote(notes[viewHolder.adapterPosition])
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewNotes)
    }

    private fun removeNote(note: Note) {
        noteViewModel.deleteNote(note)
    }

    private fun listeners() {
        swipeListener()
        binding.buttonAddNote.setOnClickListener {
            startActivity(Intent(applicationContext, CreateNoteActivity::class.java))
        }
    }

    private fun createNotesAdapter() {
        notesAdapter = NotesAdapter(notes) {
            onItemClick(it)
        }
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerViewNotes.adapter = notesAdapter
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