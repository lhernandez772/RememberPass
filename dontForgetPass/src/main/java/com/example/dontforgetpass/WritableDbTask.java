package com.example.dontforgetpass;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

/**
 * Created by lhernandez on 11/11/13.
 */
public class WritableDbTask extends AsyncTask<Password,Integer,Long> {
    private Context context;
    private UserPasswordContract.PasswordsDBHelper mDbhelper = null;
    public WritableDbTask(Context context) {
        this.context = context;
    }
    @Override
    protected Long doInBackground(Password... params) {
        mDbhelper = new UserPasswordContract().new PasswordsDBHelper(context);
        SQLiteDatabase db = mDbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserPasswordContract.PasswordEntry.COLUMN_NAME_SITE, params[0].getTitle());
        values.put(UserPasswordContract.PasswordEntry.COLUMN_NAME_USERNAME, params[0].getUsername());
        values.put(UserPasswordContract.PasswordEntry.COLUMN_NAME_PASSWORD, params[0].getPassword());
        long newRowId;
        newRowId = db.insert(UserPasswordContract.PasswordEntry.TABLE_NAME,null,values);
        return newRowId;
    }
}
