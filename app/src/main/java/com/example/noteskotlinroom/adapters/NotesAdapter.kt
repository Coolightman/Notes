package com.example.noteskotlinroom.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.noteskotlinroom.R
import com.example.noteskotlinroom.entities.Note
import com.example.noteskotlinroom.enums.DayOfWeek

class NotesAdapter(
    private var notes: List<Note>,
    private val clickListener: (Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[position]
        val resources = holder.itemView.context.resources
        holder.title.text = note.title
        holder.description.text = note.description
        holder.dayOfWeek.text = when (note.dayOfWeek) {
            0 -> resources.getString(DayOfWeek.ANY_DAY.nameRes)
            1 -> resources.getString(DayOfWeek.MONDAY.nameRes)
            2 -> resources.getString(DayOfWeek.TUESDAY.nameRes)
            3 -> resources.getString(DayOfWeek.WEDNESDAY.nameRes)
            4 -> resources.getString(DayOfWeek.THURSDAY.nameRes)
            5 -> resources.getString(DayOfWeek.FRIDAY.nameRes)
            6 -> resources.getString(DayOfWeek.SATURDAY.nameRes)
            7 -> resources.getString(DayOfWeek.SUNDAY.nameRes)
            else -> "Wrong day of week number"
        }
        if (note.dayOfWeek == 0) {
            holder.dayOfWeek.isVisible = false
        }

        val context = holder.itemView.context
        val color = when (note.priority) {
            1 -> ContextCompat.getColor(context, android.R.color.holo_red_dark)
            2 -> ContextCompat.getColor(context, android.R.color.holo_orange_dark)
            else -> ContextCompat.getColor(context, android.R.color.holo_green_dark)
        }
        holder.title.setBackgroundColor(color)
        holder.itemView.setOnClickListener { clickListener(note) }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textViewTitle)
        val description: TextView = itemView.findViewById(R.id.textViewDescription)
        val dayOfWeek: TextView = itemView.findViewById(R.id.textViewDayOfWeek)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNotes() = notes
}