package com.example.tasklist_kotlindemo.view


import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.tasklist_kotlindemo.R
import com.google.android.material.snackbar.Snackbar

class AddEditActivity : AppCompatActivity() {

    companion object {
        val EXTRA_ID = "com.example.tasklist_kotlindemo.view.EXTRA_ID"
        val EXTRA_TITLE = "com.example.tasklist_kotlindemo.view.EXTRA_TITLE"
        val EXTRA_DESC = "com.example.tasklist_kotlindemo.view.EXTRA_DESC"
        val EXTRA_PRIORITY = "com.example.tasklist_kotlindemo.view.EXTRA_PRIORITY"
    }

    lateinit var addLayout: CoordinatorLayout
    lateinit var etTitle: EditText
    lateinit var etDescription: EditText
    lateinit var npPriority: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        Log.d("SURAJ", "Content set.")
        addLayout = findViewById(R.id.add_layout)
        etTitle = findViewById(R.id.et_title)
        etDescription = findViewById(R.id.et_description)
        npPriority = findViewById(R.id.np_priority)

        npPriority.minValue = 1
        npPriority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp)
        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            etTitle.setText(intent.getStringExtra(EXTRA_TITLE))
            etDescription.setText(intent.getStringExtra(EXTRA_DESC))
            npPriority.value = intent.getIntExtra(EXTRA_PRIORITY, 1)
        } else
            title = "Add Note"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_note -> saveNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        val title = etTitle.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val priority = npPriority.value

        if (title.isEmpty() || description.isEmpty()) {
            Snackbar.make(
                addLayout,
                "Please insert a title and a description.",
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESC, description)
        data.putExtra(EXTRA_PRIORITY, priority)

        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1)
            data.putExtra(EXTRA_ID, id)

        setResult(RESULT_OK, data)
        finish()
    }
}
