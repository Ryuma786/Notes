package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickDeleteInterface, NoteClickInterface {
    lateinit var RVnotes:RecyclerView
    lateinit var addFAB:FloatingActionButton
    lateinit var viewModel:NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RVnotes=findViewById(R.id.RVnotes)
        addFAB=findViewById(R.id.FABttn)
        RVnotes.layoutManager=LinearLayoutManager(this)

        val notesRVAdapter=NoteRVAdapter(this,this,this)
        RVnotes.adapter=notesRVAdapter
        viewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNote.observe(this, Observer { List->
            List?.let {
                notesRVAdapter.updateList(it)
            }
        })
        addFAB.setOnClickListener{
            val intent=Intent(this@MainActivity,AddEditNoteActivity::class.java)
            startActivity(intent)


        }
    }

    override fun onDeleteIconClick(note: Note) {


        viewModel.deleteNote(note)
        Toast.makeText(this,"${note.noteTitle} Deleted",Toast.LENGTH_LONG).show()
    }

    override fun onNoteClick(note: Note) {
        val intent=Intent(this@MainActivity,AddEditNoteActivity::class.java)
        intent.putExtra("noteType","Edit")
        intent.putExtra("noteTitle",note.noteTitle)
        intent.putExtra("noteDescription",note.noteDescription)
        intent.putExtra("noteID",note.id)
        startActivity(intent)

    }


}