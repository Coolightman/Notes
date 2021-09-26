package com.example.noteskotlinroom.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.noteskotlinroom.R
import com.example.noteskotlinroom.databases.NotesDatabase
import com.example.noteskotlinroom.enums.DayOfWeek
import com.example.noteskotlinroom.models.Note
import kotlinx.android.synthetic.main.activity_create_note.*
import java.util.concurrent.Executors

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var days: List<String>
    private lateinit var db: NotesDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        getDatabase()

        createView()
        listeners()
    }

    private fun getDays() {
        days = listOf(
            getString(DayOfWeek.ANY_DAY.nameRes),
            getString(DayOfWeek.MONDAY.nameRes),
            getString(DayOfWeek.TUESDAY.nameRes),
            getString(DayOfWeek.WEDNESDAY.nameRes),
            getString(DayOfWeek.THURSDAY.nameRes),
            getString(DayOfWeek.FRIDAY.nameRes),
            getString(DayOfWeek.SATURDAY.nameRes),
            getString(DayOfWeek.SUNDAY.nameRes)
        )
    }

    private fun createView() {
        setTitleColor()
        getDays()
        createDaysSpinner()
    }

    private fun getDatabase() {
        db = Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java, getString(R.string.database_notes_name)
        ).build()
    }

    private fun listeners() {
        buttonSaveNote.setOnClickListener {
            if (isFilled()) {
                createNote()
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.fields_not_filled),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        radioGroup.setOnCheckedChangeListener { _, _ ->
            setTitleColor()
        }
    }

    private fun setTitleColor() {
        when (getPriority()) {
            1 -> editTextNoteTitle.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
            2 -> editTextNoteTitle.setBackgroundColor(resources.getColor(android.R.color.holo_orange_light))
            3 -> editTextNoteTitle.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
        }
    }

    private fun createNote() {
        val title = editTextNoteTitle.text.toString().trim()
        val description = editTextNoteDescription.text.toString().trim()
        val dayOfWeek = spinnerDayOfWeek.selectedItemPosition
        val priority = getPriority()
        val note = Note(title, description, dayOfWeek, priority)
        saveNote(note)
    }

    private fun getPriority(): Int {
        val checkedRadioId = radioGroup.checkedRadioButtonId
        return (findViewById<RadioButton>(checkedRadioId)).text.toString().toInt()
    }

    private fun saveNote(note: Note) {
        Executors.newSingleThreadExecutor().execute {
            db.noteDao().insertAll(note)
            Handler(Looper.getMainLooper()).post {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        }
    }

    private fun createDaysSpinner() {
        spinnerDayOfWeek.adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, days)
    }

    private fun isFilled() =
        editTextNoteTitle.text.isNotEmpty() && editTextNoteDescription.text.isNotEmpty()
}