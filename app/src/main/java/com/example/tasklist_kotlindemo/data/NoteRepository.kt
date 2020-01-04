package com.example.tasklist_kotlindemo.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class NoteRepository(val application: Application) {

    val database: NoteDatabase = NoteDatabase.getInstance(application)
    val noteDAO: NoteDAO = database.noteDAO()
    val allNotes: LiveData<List<Note>> = noteDAO.getAllNotes()

    /* public exposed API's */
    fun insert(note: Note) {
        InsertNoteAsyncTask(noteDAO).execute(note)
    }

    fun update(note: Note) {
        UpdateNoteAsyncTask(noteDAO).execute(note)
    }

    fun delete(note: Note) {
        DeleteNoteAsyncTask(noteDAO).execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllNotesAsyncTask(noteDAO).execute()
    }


    class InsertNoteAsyncTask(private val noteDAO: NoteDAO) : AsyncTask<Note, Any, Any?>() {
        override fun doInBackground(vararg params: Note?) {
            noteDAO.insert(params[0])
        }
    }

    class UpdateNoteAsyncTask(private val noteDAO: NoteDAO) : AsyncTask<Note, Any, Any?>() {
        override fun doInBackground(vararg params: Note?) {
            noteDAO.update(params[0])
        }
    }

    class DeleteNoteAsyncTask(private val noteDAO: NoteDAO) : AsyncTask<Note, Any, Any?>() {
        override fun doInBackground(vararg params: Note?) {
            noteDAO.delete(params[0])
        }
    }

    class DeleteAllNotesAsyncTask(private val noteDAO: NoteDAO) : AsyncTask<Note, Any, Any?>() {
        override fun doInBackground(vararg params: Note?) {
            noteDAO.deleteAllNotes()
        }
    }
}