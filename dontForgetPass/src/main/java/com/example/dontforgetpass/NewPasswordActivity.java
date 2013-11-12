package com.example.dontforgetpass;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NewPasswordActivity extends ActionBarActivity {
    private EditText title;
    private EditText username;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        this.title = (EditText) findViewById(R.id.edit_text_title);
        this.username = (EditText) findViewById(R.id.edit_text_username);
        this.password = (EditText) findViewById(R.id.edit_text_password);
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
                Password pw = new Password(
                       this.title.getText().toString(),this.username.getText().toString(),
                       this.password.getText().toString());
                new WritableDbTask(getBaseContext()).execute(pw);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
