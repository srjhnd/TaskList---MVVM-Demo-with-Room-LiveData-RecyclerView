package com.example.tasklist_kotlindemo.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDAO(): NoteDAO

    private class PopulateDBAsyncTask(private val db: NoteDatabase) : AsyncTask<Any, Any, Any?>() {
        override fun doInBackground(vararg params: Any?): Any? {
            val noteDAO = db.noteDAO()
            noteDAO.insert(Note("Welcome!", "Use this app to save notes ", 1))
            noteDAO.insert(Note("Buy Apples!", "Remember to buy apples today.", 1))
            noteDAO.insert(
                Note(
                    "Feed pets.",
                    "Feed the pets or else they may spend the day starving. " + "Also remember to delete this later.",
                    2
                )
            )
            return null
        }
    }

    companion object {
        private var instance: NoteDatabase? = null

        @Synchronized
        fun getInstance(context: Context): NoteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance!!
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDBAsyncTask(instance!!).execute()
            }
        }
    }
}