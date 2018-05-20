package com.ar.sphinx.notelite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ar.sphinx.notelite.db.model.Note;

/**
 * Created by sphinx on 21/05/18.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "notes_db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "notes";
	private static final String COLOUMN_ID = "id";
	private static final String COLOUMN_NOTE = "note";
	private static final String COLOUMN_TIMESTAMP = "timestamp";

	//create table
	private static final String CREATE_TABLE =
			"CREATE TABLE " + TABLE_NAME + "(" +
					COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
					COLOUMN_NOTE + " TEXT," +
					COLOUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
			+ ")";



	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(sqLiteDatabase);
	}

	public long insertNote(String note){
		//get DB
		SQLiteDatabase db = this.getWritableDatabase();

		//create content values which are basically hash maps
		ContentValues values = new ContentValues();
		values.put(COLOUMN_NOTE,note);

		long id = db.insert(TABLE_NAME,null,values);
		db.close();
		return id;
	}

	public Note getNote(long id){

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME,
				new String[]{COLOUMN_ID,COLOUMN_NOTE,COLOUMN_TIMESTAMP},
				COLOUMN_ID + " =?",
				new String[]{String.valueOf(id)},null,null,null,null);

		if(cursor!=null){
			cursor.moveToFirst();
		}
		Note note = new Note(cursor.getInt(cursor.getColumnIndex(COLOUMN_ID)),
				cursor.getString(cursor.getColumnIndex(COLOUMN_NOTE)),
				cursor.getString(cursor.getColumnIndex(COLOUMN_TIMESTAMP)));

		cursor.close();
		return note;
	}
}
