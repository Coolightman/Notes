package com.example.noteskotlinroom.activities

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.noteskotlinroom.R
import com.example.noteskotlinroom.databinding.ActivityCreateNoteBinding
import com.example.noteskotlinroom.entities.Note
import com.example.noteskotlinroom.viewModels.NoteViewModel

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoteBinding
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        createView()
        listeners()
    }

    private fun createView() {
        val title = intent.getStringExtra("noteTitle")
        val description = intent.getStringExtra("noteDescription")
        val priority = intent.getIntExtra("notePriority", -1)

        binding.editTextNoteTitle.setText(title)
        binding.editTextNoteDescription.setText(description)
        when (priority) {
            1 -> binding.radioPriority1.isChecked = true
            2 -> binding.radioPriority2.isChecked = true
            3 -> binding.radioPriority3.isChecked = true
        }
        setTitleColor()
    }

    private fun listeners() {
        binding.buttonSaveNote.setOnClickListener {
            if (isFilled()) {
                updateNote()
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.fields_not_filled),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            setTitleColor()
        }
    }

    private fun updateNote() {
        val title = binding.editTextNoteTitle.text.toString().trim()
        val description = binding.editTextNoteDescription.text.toString().trim()
        val priority = getPriority()
        val noteId = intent.getLongExtra("noteId", -1)
        val note = Note(noteId, title, description, priority)
        saveNote(note)
    }

    private fun saveNote(note: Note) {
        noteViewModel.updateNote(note)
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }

    private fun setTitleColor() {
        val priority1color = ContextCompat.getColor(this, R.color.priority1)
        val priority2color = ContextCompat.getColor(this, R.color.priority2)
        val priority3color = ContextCompat.getColor(this, R.color.priority3)
        when (getPriority()) {
            1 -> binding.editTextNoteTitle.setBackgroundColor(priority1color)
            2 -> binding.editTextNoteTitle.setBackgroundColor(priority2color)
            3 -> binding.editTextNoteTitle.setBackgroundColor(priority3color)
        }
    }

    private fun getPriority(): Int {
        val checkedRadioId = binding.radioGroup.checkedRadioButtonId
        return (findViewById<RadioButton>(checkedRadioId)).text.toString().toInt()
    }

    private fun isFilled() =
        binding.editTextNoteDescription.text.isNotEmpty()
}