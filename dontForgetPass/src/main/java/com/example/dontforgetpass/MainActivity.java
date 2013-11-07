package com.example.dontforgetpass;
import android.content.Context;
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
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    private ViewGroup mContainerView;
    private static ScrollView scroll;
    private final int TIME_ANIM = 150;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainerView = (ViewGroup) findViewById(R.id.container);
        scroll = (ScrollView) findViewById(R.id.scroll);
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
        View.OnTouchListener mTouchListener = new View.OnTouchListener() {
            private int mSwipeSlop = -1;
            float mDownX = 0;
            boolean mItemPressed = false, mSwiping = false;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Variables del Ontouch
                if (mSwipeSlop < 0)
                    mSwipeSlop = ViewConfiguration.get(c).getScaledTouchSlop();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mItemPressed)
                            return false;
                        mItemPressed = true;
                        mDownX = event.getX();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        v.setAlpha(1);
                        v.setTranslationX(0);
                        mSwiping = false;
                        mItemPressed = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                    {
                        float x = event.getX() + v.getTranslationX();
                        float deltaX = x - mDownX;
                        float deltaXAbs = Math.abs(deltaX);
                        if (!mSwiping) {
                            if (deltaXAbs > mSwipeSlop) {
                                mSwiping = true;
                                scroll.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                        if (mSwiping) {
                            v.setTranslationX((x - mDownX));
                            v.setAlpha(1 - deltaXAbs / v.getWidth());
                        }
                    }
                    break;

                    case MotionEvent.ACTION_UP:
                    {
                        if (mSwiping) {
                            float x = event.getX() + v.getTranslationX();
                            float deltaX = x - mDownX;
                            float deltaXAbs = Math.abs(deltaX);

                            // Si voy mas alla que lo deseado el elemento se borra
                            if (deltaXAbs > v.getWidth() / 4) {
                                mContainerView.invalidate();
                                v.animate().setDuration(TIME_ANIM).translationX(1000).alphaBy(0);
                                mContainerView.removeView(v);
                            } else {
                                v.animate().setDuration(TIME_ANIM).translationX(0);
                                mSwiping = false;
                            }
                        }
                        mItemPressed = false;
                    }
                    break;
                }

                return true;
            }
        };
        newView.setOnTouchListener(mTouchListener);
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

}
