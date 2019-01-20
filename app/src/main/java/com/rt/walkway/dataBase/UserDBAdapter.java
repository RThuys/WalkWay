package com.rt.walkway.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDBAdapter {
    private static final String TAG = "UserDBAdapter";
    UserDBHelper dbHelper;

    public UserDBAdapter(Context context) {
        dbHelper = new UserDBHelper(context);
    }

    public long insertData(String firstName, String lastName, String email, String username, String password) {
        SQLiteDatabase dbb = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.USER_FIRST_NAME, firstName);
        contentValues.put(dbHelper.USER_LAST_NAME, lastName);
        contentValues.put(dbHelper.USER_EMAIL, email);
        contentValues.put(dbHelper.USER_USERNAME, username);
        contentValues.put(dbHelper.USER_PASSWORD, password);
        long id = dbb.insert(dbHelper.TABLE_NAME, null, contentValues);
        Log.i(TAG, "insertData: at the end of the insertData");
        return id;

    }

    public String getData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {dbHelper.USER_ID, dbHelper.USER_FIRST_NAME, dbHelper.USER_LAST_NAME, dbHelper.USER_EMAIL, dbHelper.USER_USERNAME, dbHelper.USER_PASSWORD};
        Cursor cursor = db.query(UserDBHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int cid = cursor.getInt(cursor.getColumnIndex(dbHelper.USER_ID));
            String firstName = cursor.getString(cursor.getColumnIndex(dbHelper.USER_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndex(dbHelper.USER_LAST_NAME));
            String email = cursor.getString(cursor.getColumnIndex(dbHelper.USER_EMAIL));
            String username = cursor.getString(cursor.getColumnIndex(dbHelper.USER_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(dbHelper.USER_PASSWORD));
            buffer.append(cid + " " + firstName + " " + lastName + " " + email + " " + username + " " + password);
        }
        return buffer.toString();
    }

    private class UserDBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "userDB";
        private static final String TABLE_NAME = "users";
        private static final int DATABASE_VERSION = 1;
        private static final String USER_ID = "userId";
        private static final String USER_FIRST_NAME = "firstName";
        private static final String USER_LAST_NAME = "lastName";
        private static final String USER_EMAIL = "email";
        private static final String USER_USERNAME = "username";
        private static final String USER_PASSWORD = "password";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_FIRST_NAME + " VARCHAR(25) ," + USER_LAST_NAME + " VARCHAR(25)," + USER_EMAIL + " VARCHAR(35)," + USER_USERNAME + " VARCHAR(25)," + USER_PASSWORD + " VARCHAR(25));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;


        public UserDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Log.i(this.toString(), "onCreate: " + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Log.i(TAG, "onUpgrade: " + context);
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
                Log.i(TAG, "onUpgrade: " + context + " " + e);
            }
        }
    }
}
