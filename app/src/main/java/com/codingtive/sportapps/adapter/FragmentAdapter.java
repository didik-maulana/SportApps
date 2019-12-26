package com.codingtive.sportapps.adapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> pages = new ArrayList<>();
    private List<String> pagesTitle = new ArrayList<>();

    public FragmentAdapter(FragmentManager manager) {
        super(manager);
    }

    public void addPage(Fragment page, String title) {
        pages.add(page);
        pagesTitle.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    public String getPageTitle(int position) {
        return pagesTitle.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }
}
