package com.example.dontforgetpass;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

// Actividad para el manejo de los ontouch
public class MainActivity extends ActionBarActivity {
    private ViewGroup mContainerView;
    private static ScrollView scroll;
    private final int TIME_ANIM = 150;
    private String[] colors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainerView = (ViewGroup) findViewById(R.id.container);
        scroll = (ScrollView) findViewById(R.id.scroll);
       colors = new String[]{
                getResources().getString(R.string.purple),getResources().getString(R.string.green),
                getResources().getString(R.string.blue),getResources().getString(R.string.red),
        };
        for (int i = 0; i<30; i++) {
            addItem();
        }
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
                PASS[(int) (Math.random() * PASS.length)]);
        View.OnTouchListener mTouchListener = new View.OnTouchListener() {
            private int swipe = -1;
            float pressX = 0;
            boolean itemPress = false, dragging = false;
            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Variables del Ontouch
                if (swipe < 0)
                    swipe = ViewConfiguration.get(c).getScaledTouchSlop();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (itemPress)
                            return false;
                        itemPress = true;
                        pressX = event.getX();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        v.setAlpha(1);
                        v.setTranslationX(0);
                        dragging = false;
                        itemPress = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                    {
                        float x = event.getX() + v.getTranslationX();
                        float deltaX = x - pressX;
                        if (!dragging) {
                            if (Math.abs(deltaX) > swipe) {
                                dragging = true;
                                scroll.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                        if (dragging) {
                            v.setTranslationX((x - pressX));
                            v.setAlpha(1 - Math.abs(deltaX) / v.getWidth());
                        }
                    }
                    break;

                    case MotionEvent.ACTION_UP:
                    {
                        if (dragging) {
                            float x = event.getX() + v.getTranslationX();
                            float deltaX = x - pressX;
                            // Si voy mas alla que lo deseado el elemento se borra
                            if (Math.abs(deltaX) > v.getWidth() / 4) {
                                mContainerView.invalidate();
                                v.animate().setDuration(TIME_ANIM).translationX(1000).alphaBy(0);
                                mContainerView.removeView(v);
                            } else {
                                v.animate().setDuration(TIME_ANIM).translationX(0);
                                dragging = false;
                            }
                        }
                        itemPress = false;
                    }
                    break;
                }

                return true;
            }
        };
        newView.setOnTouchListener(mTouchListener);
        LinearLayout l = (LinearLayout)newView.findViewById(R.id.item);
        l.setBackgroundColor(Color.parseColor(colors[(int) (Math.random() * colors.length)]));
        mContainerView.addView(newView, 0);
    }

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
    private static final String[] PASS = new String[]{
            "Banco provincial", "Hotmail", "Gmail", "Banco de Venezuela", "Facebook",
            "Twitter", "Instagram", "Ubuntu one", "Amazon", "SASE",
            "PC",
    };

}
