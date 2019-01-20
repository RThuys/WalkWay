package com.rt.walkway.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pathsDB.db";
    private static final String TABLE_NAME = "paths";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CITY_NAME = "cityName";
    private static final String COLUMN_DISTANCE = "distance";
    private static final String COLULM_DESCRIPTION = "description";
    private static final String COLUMN_DIFFICULTY = "difficulty";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_NAME +
                        "(id integer primary key AUTOINCREMENT,cityName text, distance real, description text, difficulty integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int prev, int curr) {
    }

    public ArrayList<String> getAllPaths() {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from paths", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            arrayList.add(res.getString(res.getColumnIndex(COLUMN_CITY_NAME)));
            res.moveToNext();
        }
        return arrayList;
    }
}
