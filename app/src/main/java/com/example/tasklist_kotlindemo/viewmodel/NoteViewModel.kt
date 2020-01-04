package com.example.tasklist_kotlindemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.tasklist_kotlindemo.data.Note
import com.example.tasklist_kotlindemo.data.NoteRepository

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val noteRepository: NoteRepository = NoteRepository(application)
    val allNotes: LiveData<List<Note>> = noteRepository.allNotes

    fun insert(note: Note) = noteRepository.insert(note)
    fun update(note: Note) = noteRepository.update(note)
    fun delete(note: Note) = noteRepository.delete(note)
    fun deleteAllNotes() = noteRepository.deleteAllNotes()
}