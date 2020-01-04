package com.example.tasklist_kotlindemo.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDAO {

    @Insert
    fun insert(note: Note?)

    @Update
    fun update(note: Note?)

    @Delete
    fun delete(note: Note?)

    @Query("delete from note_table")
    fun deleteAllNotes()

    @Query("select * from note_table")
    fun getAllNotes(): LiveData<List<Note>>

}