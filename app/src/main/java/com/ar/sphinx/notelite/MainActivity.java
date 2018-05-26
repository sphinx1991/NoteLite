package com.ar.sphinx.notelite;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ar.sphinx.notelite.db.DatabaseHelper;
import com.ar.sphinx.notelite.db.model.Note;
import com.ar.sphinx.notelite.utils.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private RecyclerView recyclerView;
	private FloatingActionButton fab;
	private DatabaseHelper db;
	private List<Note> noteList = new ArrayList<>();
	private NotesAdapter notesAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		recyclerView = findViewById(R.id.recycler_view);
		fab = findViewById(R.id.fab);
		db = new DatabaseHelper(this);
		noteList.addAll(db.getAllNotes());
		notesAdapter = new NotesAdapter(this,noteList);
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(notesAdapter);

	}

	public void createNote(String note){
		long id = db.insertNote(note);
		Note dbNote = db.getNote(id);
		if(dbNote!=null){
			noteList.add(0,dbNote);
			notesAdapter.notifyDataSetChanged();
		}
	}

	public void updateNote(String note,int pos){
		Note dbNote = noteList.get(pos);
		dbNote.setNote(note);
		db.updateNote(dbNote);
		noteList.set(pos,dbNote);
		notesAdapter.notifyDataSetChanged();
	}

	public void deleteNote(int position){
		db.deleteNote(noteList.get(position));
		noteList.remove(position);
		notesAdapter.notifyItemRemoved(position);
	}
}
