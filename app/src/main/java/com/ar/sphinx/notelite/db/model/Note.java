package com.ar.sphinx.notelite.db.model;

/**
 * Created by sphinx on 21/05/18.
 */
public class Note {

	private int id;
	private String note;
	private String timestamp;

	public Note(int id, String note, String timestamp) {
		this.id = id;
		this.note = note;
		this.timestamp = timestamp;
	}

	public Note() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
