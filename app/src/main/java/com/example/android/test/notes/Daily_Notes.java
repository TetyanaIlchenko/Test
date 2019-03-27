package com.example.android.test.notes;

public class Daily_Notes {
    public static final String TABLE_DAILY_NAME = "Daily_Notes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TIME = "time";

    private int id;
    private String note;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_DAILY_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOTE + " TEXT,"
                    + COLUMN_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Daily_Notes() {
    }

    public Daily_Notes(int id, String note, String timestamp) {
        this.id = id;
        this.note = note;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}
