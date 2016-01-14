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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.github.koshkin.leagueoflegendsstats.fragments.HomeFragment;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.viewhelpers.FloatingActionButtonViewHelper;
import com.github.koshkin.leagueoflegendsstats.views.ToolbarSearchView;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ContentLoadingProgressBar mProgressBar;
    private RelativeLayout mProgressLayout;
    private View mErrorLayout;
    private FloatingActionButton mFab;

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
        mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main); //main content view
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //toolbar
        setSupportActionBar(toolbar);

        FloatingActionButtonViewHelper.getInstance(this).initialize(); //initialized the fab helper

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            startFragment(HomeFragment.class);
        }

        mFab = (FloatingActionButton) findViewById(R.id.fab);

        initStaticData();

        mProgressBar = (ContentLoadingProgressBar) findViewById(R.id.loading_bar);
        mProgressLayout = (RelativeLayout) findViewById(R.id.loading_layout);
        mErrorLayout = findViewById(R.id.error_layout_layout);
    }

    private void initStaticData() {
        //TODO some cool checking
        new Runnable() {
            @Override
            public void run() {
                StaticDataHolder.getInstance(MainActivity.this).init();
            }
        }.run();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        return (Toolbar) findViewById(R.id.toolbar);
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
}
