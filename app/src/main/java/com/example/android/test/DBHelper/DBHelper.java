package com.example.android.test.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.test.notes.Daily_Notes;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME_DAILY = "notes_db";

    public DBHelper(Context context) {
        super(context, DB_NAME_DAILY, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Daily_Notes.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Daily_Notes.TABLE_DAILY_NAME);

        // Create tables again
        onCreate(db);
    }


    public long addDailyNote(String note) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Daily_Notes.COLUMN_NOTE, note);

        // insert row
        long id = db.insert(Daily_Notes.TABLE_DAILY_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Daily_Notes getDailyNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Daily_Notes.TABLE_DAILY_NAME,
                new String[]{Daily_Notes.COLUMN_ID, Daily_Notes.COLUMN_NOTE, Daily_Notes.COLUMN_TIME},
                Daily_Notes.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Daily_Notes note = new Daily_Notes(
                cursor.getInt(cursor.getColumnIndex(Daily_Notes.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Daily_Notes.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(Daily_Notes.COLUMN_TIME)));

        // close the db connection
        cursor.close();

        return note;
    }

    public List<Daily_Notes> getAllDailyNotes() {
        List<Daily_Notes> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Daily_Notes.TABLE_DAILY_NAME + " ORDER BY " +
                Daily_Notes.COLUMN_TIME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Daily_Notes note = new Daily_Notes();
                note.setId(cursor.getInt(cursor.getColumnIndex(Daily_Notes.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Daily_Notes.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Daily_Notes.COLUMN_TIME)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getDailyNotesCount() {
        String countQuery = "SELECT  * FROM " + Daily_Notes.TABLE_DAILY_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateDailyNote(Daily_Notes note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Daily_Notes.COLUMN_NOTE, note.getNote());

        // updating row
        return db.update(Daily_Notes.TABLE_DAILY_NAME, values, Daily_Notes.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteDailyNote(Daily_Notes note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Daily_Notes.TABLE_DAILY_NAME, Daily_Notes.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

}
