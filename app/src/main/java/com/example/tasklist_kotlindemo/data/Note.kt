package com.example.tasklist_kotlindemo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
class Note(var title: String?, var description: String?, var priority: Int?) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}