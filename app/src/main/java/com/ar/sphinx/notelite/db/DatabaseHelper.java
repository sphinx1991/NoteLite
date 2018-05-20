package com.ar.sphinx.notelite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

	}
}
