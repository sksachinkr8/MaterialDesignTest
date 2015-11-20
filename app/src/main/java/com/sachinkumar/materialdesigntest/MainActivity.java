package com.sachinkumar.materialdesigntest;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sachinkumar.materialdesigntest.fragments.Exclusive;
import com.sachinkumar.materialdesigntest.fragments.MyFragment;
import com.sachinkumar.materialdesigntest.fragments.SearchFragment;
import com.sachinkumar.materialdesigntest.fragments.Trending;
import com.sachinkumar.materialdesigntest.tabs.SlidingTabLayout;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);  //Giving equal space to all tabs
//        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return getResources().getColor(R.color.colorAccent);
//            }
//        });

        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        mTabs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTabs.setSelectedIndicatorColors(getResources().getColor(android.R.color.white));
        mTabs.setViewPager(mPager);
        //Before we set ViewPager we have to make sure that our ViewPager is fully constructed.
// Our ViewPager displays several fragments with the help of an adapter. So we gotta make an adapter (MyPagerAdapter) first.
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id==R.id.action_settings) {
            Toast.makeText(this, "Hey you just hit "+item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id==R.id.navigate) {
            Intent intent = new Intent(this, SubActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    //Adapter to get fragment for setViewPager

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        int[] icons = {R.drawable.ic_search_white_24dp, R.drawable.ic_local_activity_white_24dp, R.drawable.ic_trending_up_white_24dp };
//        int[] icons = {R.drawable.ic_search_white_24dp, R.drawable.ic_local_activity_white_24dp, R.drawable.ic_location_on_white_24dp, R.drawable.ic_trending_up_white_24dp, R.drawable.ic_view_stream_white_24dp };

        private String[] tabs = getResources().getStringArray(R.array.tabs);


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            MyFragment myFragment = MyFragment.getInstance(position);
//            return myFragment;
            Fragment fragment = null;
            switch (position) {
                case 0: fragment = new SearchFragment();
                    break;
                case 1: fragment = new Exclusive();
                    break;
//                case 2: fragment = new Nearby();
//                    break;
                case 2: fragment = new Trending();
                    break;
//                case 4: fragment = new Feeds();
//                    break;
            }
            return fragment;
        }


        @Override
        public int getCount() {
            return 3;
        }


        //Use spannable to style normal text along with images or icons
        @Override
        public CharSequence getPageTitle(int position) {

            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0, 0, 36, 36);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }


    }

}
