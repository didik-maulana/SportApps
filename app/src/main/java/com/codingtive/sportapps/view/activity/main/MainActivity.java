package com.codingtive.sportapps.view.activity.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.codingtive.sportapps.R;
import com.codingtive.sportapps.adapter.FragmentAdapter;
import com.codingtive.sportapps.view.fragment.favorite.FavoriteFragment;
import com.codingtive.sportapps.view.fragment.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigation;
    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupPageAdapter();
        setupViewPager();
        setupBottomNavigation();
        setToolbarTitle(getString(R.string.title_home));
    }

    private void setupPageAdapter() {
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addPage(new HomeFragment(), getString(R.string.title_home));
        fragmentAdapter.addPage(new FavoriteFragment(), getString(R.string.title_favorites));
    }

    private void setupViewPager() {
        viewPager = findViewById(R.id.view_pager_container);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    private void setupBottomNavigation() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    private void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setElevation(0f);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                viewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_favorites:
                viewPager.setCurrentItem(1);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setToolbarTitle(fragmentAdapter.getPageTitle(position));
        if (position == 0) {
            bottomNavigation.setSelectedItemId(R.id.navigation_home);
        } else {
            bottomNavigation.setSelectedItemId(R.id.navigation_favorites);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
