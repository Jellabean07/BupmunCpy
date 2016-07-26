package com.wonbuddism.bupmun.Rank;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Common.NavigationDrawerMenu;

import java.util.ArrayList;
import java.util.List;

public class RankMainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_main);
        setLayout();
    }

    private void setLayout() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.rank_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.rank_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.rank_nav_view);
        if (navigationView != null) {
            new NavigationDrawerMenu(this,mDrawerLayout).setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.rank_viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.rank_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private RankTodayFragment newFragment(String title){
        RankTodayFragment frament = new RankTodayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        frament.setArguments(bundle);
        return frament;
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new RankTodayFragment(), "오늘");
/*        adapter.addFragment(new RankTodayFragment(), "교당내");
        adapter.addFragment(new RankTodayFragment(), "교구내");*/
        adapter.addFragment(new RankVillageFragemnet(), "동네방네");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //빽(취소)키가 눌렸을때 종료여부를 묻는 다이얼로그 띄움
        if((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                mDrawerLayout.closeDrawers();
                return false;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
