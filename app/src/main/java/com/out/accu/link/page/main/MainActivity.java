package com.out.accu.link.page.main;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.cyou17173.android.arch.base.page.SmartActivity;
import com.cyou17173.android.component.common.util.fragment.FragmentInstanceManager;
import com.out.accu.link.R;

public class MainActivity extends SmartActivity<MainContract.Presenter> implements
        NavigationView.OnNavigationItemSelectedListener, MainContract.View {

    TextView mTvUsername;

    FragmentInstanceManager mInstanceManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        mTvUsername = drawer.findViewById(R.id.et_username);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_device);

        mInstanceManager = new FragmentInstanceManager(getSupportFragmentManager(), R.id.content);
    }

    @Override
    public void registerEvent() {
        getPresenter().onItemSelected(R.id.nav_device);
    }

    @Override
    public void unregisterEvent() {

    }

    @Override
    public MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        getPresenter().onItemSelected(item.getItemId());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean isCachedFragment(@NonNull String tag) {
        return mInstanceManager.isCached(tag);
    }

    @Override
    public void showFragmentAndCache(@NonNull String tag, @NonNull Fragment fragment) {
        mInstanceManager.showFragmentAndCache(tag, fragment);
    }

    @Override
    public void showCacheFragment(@NonNull String tag) {
        mInstanceManager.showCacheFragment(tag);
    }

    @Override
    public void showUsername(String username) {
        mTvUsername.setText(username);
    }
}
