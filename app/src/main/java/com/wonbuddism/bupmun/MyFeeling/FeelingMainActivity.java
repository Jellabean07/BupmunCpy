package com.wonbuddism.bupmun.MyFeeling;

import android.content.Intent;
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
import com.wonbuddism.bupmun.Utility.NavigationDrawerMenu;

import java.util.ArrayList;
import java.util.List;

public class FeelingMainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling_main);

        setLayout();
    }
    private void setLayout() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.feeling_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.feeling_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.feeling_nav_view);
        if (navigationView != null) {
            new NavigationDrawerMenu(this,mDrawerLayout).setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.feeling_viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.feeling_tabs);
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
    private FeelingContentFragment newFragment(String title){
        FeelingContentFragment frament = new FeelingContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        frament.setArguments(bundle);
        return frament;
    }

    private FeelingConentPartFragment newPartFragment(String title){
        FeelingConentPartFragment frament = new FeelingConentPartFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        frament.setArguments(bundle);
        return frament;
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(newFragment("전체"), "전체");
        adapter.addFragment(newPartFragment("정전"), "정전");
        adapter.addFragment(newPartFragment("대종경"), "대종경");
        adapter.addFragment(newPartFragment("원불교교사"), "원불교교사");
        adapter.addFragment(newPartFragment("불조요경"), "불조요경");
        adapter.addFragment(newPartFragment("예전"), "예전");
        adapter.addFragment(newPartFragment("정산종사법어"), "정산종사법어");
        adapter.addFragment(newPartFragment("대산종사법어"), "대산종사법어");

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
