package com.example.tasklist_kotlindemo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist_kotlindemo.R
import com.example.tasklist_kotlindemo.data.Note
import com.example.tasklist_kotlindemo.viewmodel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), NoteAdapter.OnItemClickListener {

    val ADD_NOTE_REQUEST = 1
    val EDIT_NOTE_REQUEST = 2
    lateinit var parentLayout: CoordinatorLayout
    lateinit var noteViewModel: NoteViewModel
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NoteAdapter()
        adapter.clickListener = this
        parentLayout = findViewById(R.id.main_layout)

        val addNoteButton: FloatingActionButton = findViewById(R.id.add_button)
        addNoteButton.setOnClickListener { v: View ->
            startActivityForResult(Intent(v.context, AddEditActivity::class.java), ADD_NOTE_REQUEST)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, Observer<List<Note>> { notes ->
            adapter.notes = notes
            adapter.notifyDataSetChanged()
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.notes[viewHolder.adapterPosition])
                Snackbar.make(parentLayout, "Note deleted!", Snackbar.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == ADD_NOTE_REQUEST || requestCode == EDIT_NOTE_REQUEST)
            && resultCode == RESULT_OK
        ) {
            val title = data?.getStringExtra(AddEditActivity.EXTRA_TITLE)
            val description = data?.getStringExtra(AddEditActivity.EXTRA_DESC)
            val priority = data?.getIntExtra(AddEditActivity.EXTRA_PRIORITY, 1)

            val newNote = Note(title, description, priority)

            if (requestCode == EDIT_NOTE_REQUEST) {
                val id = data?.getIntExtra(AddEditActivity.EXTRA_ID, -1)
                if (id == -1)
                    Snackbar.make(
                        parentLayout,
                        "Something went wrong. Try Again!",
                        Snackbar.LENGTH_LONG
                    ).show()
                newNote.id = id?: -1
                noteViewModel.update(newNote)
                Snackbar.make(parentLayout, "Note edited!", Snackbar.LENGTH_SHORT).show()
            } else if (requestCode == ADD_NOTE_REQUEST) {
                noteViewModel.insert(newNote)
                Snackbar.make(parentLayout, "Note added!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all_notes) {
            noteViewModel.deleteAllNotes()
            Snackbar.make(parentLayout, "All notes deleted!", Snackbar.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    override fun onItemClick(note: Note) {
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra(AddEditActivity.EXTRA_TITLE, note.title)
        intent.putExtra(AddEditActivity.EXTRA_DESC, note.description)
        intent.putExtra(AddEditActivity.EXTRA_PRIORITY, note.priority)
        intent.putExtra(AddEditActivity.EXTRA_ID, note.id)

        startActivityForResult(intent, EDIT_NOTE_REQUEST)
    }
}
