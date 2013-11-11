package com.example.dontforgetpass;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class UserPasswordContract {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    //these strings are for create or delete the tables of database
    private final String SQL_CREATE_PASSWORDS =
            "CREATE TABLE " + UserPasswordContract.PasswordEntry.TABLE_NAME + " (" +
                    PasswordEntry._ID + " INTEGER PRIMARY KEY," +
                    PasswordEntry.COLUMN_NAME_SITE + TEXT_TYPE + COMMA_SEP +
                    PasswordEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                    PasswordEntry.COLUMN_NAME_PASSWORD +" )";
    private static final String SQL_DELETE_PASSWORD =
            "DROP TABLE IF EXISTS " + PasswordEntry.TABLE_NAME;


    //Helper for the SQLite Data Base
    public class PasswordsDBHelper extends SQLiteOpenHelper  {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Passwords.db";

        public PasswordsDBHelper (Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_PASSWORDS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_PASSWORD);
            onCreate(db);

        }
    }

    public UserPasswordContract() {}

    // The inners class for tables
    public static abstract class PasswordEntry implements BaseColumns {
        public static final String TABLE_NAME = "passwords";
        public static final String COLUMN_NAME_SITE = "site";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }
}
