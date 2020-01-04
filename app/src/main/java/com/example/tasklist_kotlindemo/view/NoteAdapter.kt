package com.example.tasklist_kotlindemo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist_kotlindemo.R
import com.example.tasklist_kotlindemo.data.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    var clickListener: OnItemClickListener? = null
    var notes: List<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currNote = notes[position]
        holder.tvTitle.text = currNote.title
        holder.tvDescription.text = currNote.description
        holder.tvPriority.text = currNote.priority.toString()
    }

    override fun getItemCount(): Int = notes.size

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvPriority: TextView = itemView.findViewById(R.id.tv_priority)

        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (clickListener != null && pos != RecyclerView.NO_POSITION)
                    clickListener!!.onItemClick(notes[pos])
            }
        }
    }

    public interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

}