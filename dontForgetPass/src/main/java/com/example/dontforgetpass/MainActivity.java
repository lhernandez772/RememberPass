package com.example.dontforgetpass;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    private ViewGroup mContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainerView = (ViewGroup) findViewById(R.id.container);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_item:
                addItem();
                findViewById(android.R.id.empty).setVisibility(View.GONE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addItem() {
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.list_item_example, mContainerView, false);
        final Context c = newView.getContext();
        ((TextView) newView.findViewById(android.R.id.text1)).setText(
                COUNTRIES[(int) (Math.random() * COUNTRIES.length)]);
        newView.findViewById(R.id.action_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(c, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.main, popup.getMenu());
                popup.show();
            }
        });
        mContainerView.addView(newView, 0);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    private static final String[] COUNTRIES = new String[]{
            "Banco provincial", "Hotmail", "Gmail", "Banco de Venezuela", "Facebook",
            "Twitter", "Instagram", "Ubuntu one", "Amazon", "SASE",
            "PC",
    };
    public final void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main, popup.getMenu());
        popup.show();
    }

}
