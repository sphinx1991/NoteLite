package com.ar.sphinx.notelite;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.sphinx.notelite.db.DatabaseHelper;
import com.ar.sphinx.notelite.db.model.Note;
import com.ar.sphinx.notelite.utils.MyDividerItemDecoration;
import com.ar.sphinx.notelite.utils.NotesAdapter;
import com.ar.sphinx.notelite.utils.RecyclerTouchListener;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private RecyclerView recyclerView;
	private FloatingActionButton fab;
	private DatabaseHelper db;
	private List<Note> noteList = new ArrayList<>();
	private NotesAdapter notesAdapter;
	private TextView emptyNotesView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		emptyNotesView = findViewById(R.id.empty_notes_view);
		recyclerView = findViewById(R.id.recycler_view);
		fab = findViewById(R.id.fab);
		db = new DatabaseHelper(this);
		noteList.addAll(db.getAllNotes());
		notesAdapter = new NotesAdapter(this,noteList);
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
		recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
		recyclerView.setAdapter(notesAdapter);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showNotesDialog(false, null, -1);
			}
		});
		toggleEmptyNotes();

		recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
				recyclerView, new RecyclerTouchListener.ClickListener() {
			@Override
			public void onClick(View view, final int position) {
			}

			@Override
			public void onLongClick(View view, int position) {
				showActionDialog(position);
			}
		}));
	}

	public void createNote(String note){
		long id = db.insertNote(note);
		Note dbNote = db.getNote(id);
		if(dbNote!=null){
			noteList.add(0,dbNote);
			notesAdapter.notifyDataSetChanged();
			toggleEmptyNotes();
		}
	}

	public void updateNote(String note,int pos){
		Note dbNote = noteList.get(pos);
		dbNote.setNote(note);
		db.updateNote(dbNote);
		noteList.set(pos,dbNote);
		notesAdapter.notifyItemChanged(pos);
		toggleEmptyNotes();
	}

	public void deleteNote(int position){
		db.deleteNote(noteList.get(position));
		noteList.remove(position);
		notesAdapter.notifyItemRemoved(position);
		toggleEmptyNotes();
	}

	public void showActionDialog(final int pos){
		CharSequence options[] = new CharSequence[]{"Edit","Delete"};
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Choose Option");
		alertDialog.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				if(i==0){
					showNotesDialog(true,noteList.get(pos),pos);
				}else {
					deleteNote(pos);
				}
			}
		});
		alertDialog.show();
	}

	public void showNotesDialog(final boolean shouldUpdate, final Note note, final int position){
		LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
		View view = layoutInflaterAndroid.inflate(R.layout.note_dialog, null);

		AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
		alertDialogBuilderUserInput.setView(view);

		final EditText inputNote = view.findViewById(R.id.dialog_note);
		TextView dialogTitle = view.findViewById(R.id.dialog_title);
		dialogTitle.setText(!shouldUpdate ? "New Note" : "Update Note");

		if (shouldUpdate && note != null) {
			inputNote.setText(note.getNote());
		}
		alertDialogBuilderUserInput
				.setCancelable(false)
				.setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogBox, int id) {

					}
				})
				.setNegativeButton("cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialogBox, int id) {
								dialogBox.cancel();
							}
						});

		final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
		alertDialog.show();

		alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Show toast message when no text is entered
				if (TextUtils.isEmpty(inputNote.getText().toString())) {
					Toast.makeText(MainActivity.this, "Enter note!", Toast.LENGTH_SHORT).show();
					return;
				} else {
					alertDialog.dismiss();
				}

				// check if user updating note
				if (shouldUpdate && note != null) {
					// update note by it's id
					updateNote(inputNote.getText().toString(), position);
				} else {
					// create new note
					createNote(inputNote.getText().toString());
				}
			}
		});
	}
	private void toggleEmptyNotes() {
		if (db.getNotesCount() > 0) {
			emptyNotesView.setVisibility(View.GONE);
		} else {
			emptyNotesView.setVisibility(View.VISIBLE);
		}
	}
}
