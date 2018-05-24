package com.ar.sphinx.notelite.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ar.sphinx.notelite.R;
import com.ar.sphinx.notelite.db.model.Note;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sphinx on 21/05/18.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

	private Context context;
	private List<Note> notes;

	public NotesAdapter(Context context, List<Note> notes) {
		this.context = context;
		this.notes = notes;
	}

	@NonNull
	@Override
	public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View holder = LayoutInflater.from(context).inflate(R.layout.layout_note_row,parent,false);
		return new NoteViewHolder(holder);
	}

	@Override
	public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
		Note note = notes.get(position);
		holder.note.setText(note.getNote());
		holder.timestamp.setText(getFormattedDate(note.getTimestamp()));
	}

	@Override
	public int getItemCount() {
		return 0;
	}

	class NoteViewHolder extends RecyclerView.ViewHolder {

		private TextView dot, note ,timestamp;
		public NoteViewHolder(View itemView) {
			super(itemView);
			dot = itemView.findViewById(R.id.dot);
			note = itemView.findViewById(R.id.note);
			timestamp = itemView.findViewById(R.id.timestamp);
		}
	}

	private String getFormattedDate(String date){
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = format.parse(date);
			SimpleDateFormat format2 = new SimpleDateFormat("MMM d");
			return format2.format(date1);
		} catch(ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
}
