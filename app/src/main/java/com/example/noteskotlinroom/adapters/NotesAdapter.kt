package com.example.noteskotlinroom.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.noteskotlinroom.R
import com.example.noteskotlinroom.entities.Note

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
        holder.title.text = note.title
        holder.description.text = note.description

        val context = holder.itemView.context
        val color = when (note.priority) {
            1 -> ContextCompat.getColor(context, R.color.priority1)
            2 -> ContextCompat.getColor(context, R.color.priority2)
            else -> ContextCompat.getColor(context, R.color.priority3)
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
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNotes() = notes
}