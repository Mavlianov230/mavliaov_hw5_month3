package com.example.hw5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "attractions.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ATTRACTIONS = "attractions";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ATTRACTIONS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTRACTIONS);
        onCreate(db);
    }

    public void addAttraction(Attraction attraction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", attraction.getName());
        values.put("description", attraction.getDescription());
        db.insert(TABLE_ATTRACTIONS, null, values);
        db.close();
    }

    public List<Attraction> getAllAttractions() {
        List<Attraction> attractions = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ATTRACTIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Attraction attraction = new Attraction();
                attraction.setId(cursor.getInt(0));
                attraction.setName(cursor.getString(1));
                attraction.setDescription(cursor.getString(2));
                attractions.add(attraction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return attractions;
    }

    public void deleteAttraction(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ATTRACTIONS, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}

