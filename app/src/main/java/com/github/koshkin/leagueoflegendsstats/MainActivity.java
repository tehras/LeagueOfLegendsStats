package com.github.koshkin.leagueoflegendsstats;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.github.koshkin.leagueoflegendsstats.fragments.HomeFragment;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.FloatingActionButtonViewHelper;
import com.github.koshkin.leagueoflegendsstats.views.RoundedImageView;
import com.github.koshkin.leagueoflegendsstats.views.ToolbarSearchView;

import java.util.Timer;
import java.util.TimerTask;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FirstInitialize.Callback {

    private ContentLoadingProgressBar mProgressBar;
    private RelativeLayout mProgressLayout;
    private View mErrorLayout;
    private FloatingActionButton mFab;
    private View mMainLoadingLayout;
    private TextView mLoadingText;
    private Timer mTimer;
    private PopupWindow mLastPopupWindow;
    private Toolbar mToolbar;

    @Override
    protected void onPause() {
        if (mLastPopupWindow != null)
            mLastPopupWindow.dismiss();
        super.onPause();
    }

    public void showLoading() {
        if (mProgressLayout != null) {
            mProgressLayout.setVisibility(View.VISIBLE);
        }
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideLoading() {
        if (mProgressLayout != null) {
            mProgressLayout.setVisibility(View.GONE);
        }
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public void initializeError(CharSequence title, CharSequence body) {
        TextView titleTV = (TextView) findViewById(R.id.error_message_title);
        TextView bodyTV = (TextView) findViewById(R.id.error_message_body);

        titleTV.setText(title);
        bodyTV.setText(body);

        mErrorLayout.setVisibility(View.VISIBLE);
        mErrorLayout.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    }

    public void hideError() {
        if (mErrorLayout != null)
            mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!BuildConfig.DEBUG)
            Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main); //main content view
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FloatingActionButtonViewHelper.getInstance(this).initialize(); //initialized the fab helper

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mProgressBar = (ContentLoadingProgressBar) findViewById(R.id.loading_bar);
        mProgressLayout = (RelativeLayout) findViewById(R.id.loading_layout);
        mErrorLayout = findViewById(R.id.error_layout_layout);

        mMainLoadingLayout = findViewById(R.id.loading_layout_full_screen);
        mLoadingText = (TextView) findViewById(R.id.loading_text);
        RoundedImageView loadingImage = (RoundedImageView) findViewById(R.id.main_rounded_image);
        loadingImage.setRadius(this.getResources().getDimensionPixelSize(R.dimen.main_loading_rounded_size));

        mTimer = new Timer();
        mTimer.schedule(new MyTimerTask(), 0, 2000);

        //Has to go last
        if (savedInstanceState == null) {
            mMainLoadingLayout.setVisibility(View.VISIBLE);
            new Runnable() {
                @Override
                public void run() {
                    new FirstInitialize(MainActivity.this, MainActivity.this).initialize();
                }
            }.run();
        }
    }

    int imageI = 0;

    private void changeName() {
        if (imageI >= loadingText.length) {
            imageI = 0;
        }
        Log.e(getClass().getSimpleName(), "imageI - " + imageI);
        mLoadingText.setText(loadingText[imageI]);
        imageI++;
    }

    public void setLastPopupWindow(PopupWindow lastPopupWindow) {
        mLastPopupWindow = lastPopupWindow;
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    changeName();
                }
            });
        }
    }

    public void startFragment(Class<? extends BaseFragment> fragmentClass) {
        try {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.fragment_container, fragmentClass.newInstance(), fragmentClass.getSimpleName())
                    .addToBackStack(fragmentClass.getSimpleName())
                    .commit();

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void startFragment(BaseFragment fragment) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ToolbarSearchView toolbarSearchView = (ToolbarSearchView) findViewById(R.id.toolbar_search_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (toolbarSearchView.getVisibility() == View.VISIBLE) {
            toolbarSearchView.backPressed();
        } else if (getFragmentManager() != null) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //blank by default
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void openKeyboard(EditText editText) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    public void hideKeyboard(EditText editText) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void showFab() {
        if (mFab != null)
            mFab.show();
    }

    @Override
    public void completed() {
        mTimer.cancel();
        Log.e(getClass().getSimpleName(), "Main Loading Layout is GONE");
        mMainLoadingLayout.setVisibility(View.GONE);
        startFragment(HomeFragment.class);
    }

    public static String[] loadingText = new String[]{"Loading...", "Please Wait...", "Getting all the images...", "Clearing the table...", "Doing the dishes...", "Back to getting all the images...", "That image is cute...", "Oh wait.. no it's not...", "Please wait..."};

}
