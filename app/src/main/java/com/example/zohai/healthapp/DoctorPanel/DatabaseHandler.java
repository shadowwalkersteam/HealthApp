package com.example.zohai.healthapp.DoctorPanel;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "DatasourceID";

    private static final String TABLE_PATIENT = "PatientID";

    private static final String KEY_ID = "id";
    private static final String KEY_DATASOURCE = "datasource";
    private static final String KEY_NAME = "name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATASOURCE_TABLE = "CREATE TABLE " + TABLE_PATIENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATASOURCE + " TEXT,"
                + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_DATASOURCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);

        // Create tables again
        onCreate(db);
    }
    // Adding new ID
    void addUnique(UniqueID unique) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATASOURCE, unique.getDatasource()); // Unique Datasource
        values.put(KEY_NAME, unique.getName()); // Name

        // Inserting Row
        db.insert(TABLE_PATIENT, null, values);
        db.close(); // Closing database connection
    }
    UniqueID getUniqueID(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PATIENT, new String[] { KEY_ID,
                        KEY_DATASOURCE, KEY_NAME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        UniqueID unique = new UniqueID(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return unique
        return unique;
    }
    public List<UniqueID> getAllids() {
        List<UniqueID> uniqueList = new ArrayList<UniqueID>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PATIENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UniqueID unique = new UniqueID();
                unique.setID(Integer.parseInt(cursor.getString(0)));
                unique.setDatasource(cursor.getString(1));
                unique.setName(cursor.getString(2));
                // Adding contact to list
                uniqueList.add(unique);
            } while (cursor.moveToNext());
        }

        // return id list
        return uniqueList;
    }
    // Updating single ID
    public int updateUniqueID(UniqueID unique) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATASOURCE, unique.getDatasource());
        values.put(KEY_NAME, unique.getDatasource());

        // updating row
        return db.update(TABLE_PATIENT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(unique.getID()) });
    }

    // Deleting single ID
    public void deleteUniqueID(UniqueID unique) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PATIENT, KEY_ID + " = ?",
                new String[] { String.valueOf(unique.getID()) });
        db.close();
    }
    // Getting ID Count
    public int getIDsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PATIENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
