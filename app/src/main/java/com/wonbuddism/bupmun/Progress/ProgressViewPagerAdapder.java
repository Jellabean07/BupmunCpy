package com.wonbuddism.bupmun.Progress;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by VuHung on 1/23/2015.
 */
public class ProgressViewPagerAdapder extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public ProgressViewPagerAdapder(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
