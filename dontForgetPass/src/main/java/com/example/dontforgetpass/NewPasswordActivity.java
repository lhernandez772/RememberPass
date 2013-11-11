package com.example.dontforgetpass;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class NewPasswordActivity extends ActionBarActivity {
    private UserPasswordContract.PasswordsDBHelper mDbhelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbhelper = new UserPasswordContract().new PasswordsDBHelper(getBaseContext());
        setContentView(R.layout.activity_new_password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.new_password_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                SQLiteDatabase db = mDbhelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(UserPasswordContract.PasswordEntry.COLUMN_NAME_SITE, "Titulo");
                values.put(UserPasswordContract.PasswordEntry.COLUMN_NAME_USERNAME, "luish000");
                values.put(UserPasswordContract.PasswordEntry.COLUMN_NAME_PASSWORD, "19419772");
                long newRowId;
                newRowId = db.insert(
                        UserPasswordContract.PasswordEntry.TABLE_NAME,
                        null,
                        values);

                Toast toast = Toast.makeText(getApplicationContext(),Long.toString(newRowId), Toast.LENGTH_LONG);
                toast.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
