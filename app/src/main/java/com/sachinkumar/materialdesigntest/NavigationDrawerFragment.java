package com.sachinkumar.materialdesigntest;

/**
 * Created by sachinkumar on 09/11/15.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

/*
* Implement the NavAdapter's interface ClickListener to implement onClick on Navigation Drawer Recycler View
* We set ClickListener variable in NavAdapter to use this inside the activity
* Our navigation drawer fragment need to implement this interface, so we can directly set this with a method in NavAdapter,
* with a clickListener attribute.
* */
public class NavigationDrawerFragment extends Fragment implements NavAdapter.ClickListener{

    private RecyclerView recyclerView;

    public static final String PREF_FILE_NAME = "textpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private boolean mUserLearnDrawer;
    private boolean mFromSavedInstanceState;

    private View containerView;

    private NavAdapter adapter;


    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState!=null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
//        mDrawerLayout.setFitsSystemWindows(true);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawer_list);
        adapter = new NavAdapter(getActivity(), getData());
        adapter.setClickListener(this); //'this' simply indicates that the fragment is the one implementing clickListener
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<Information> getData() {
        List<Information> data= new ArrayList<>();
        int[] icons = {R.drawable.ic_home_black_24dp, R.drawable.ic_notifications_black_24dp, R.drawable.ic_group_add_black_24dp, R.drawable.ic_local_dining_black_24dp, R.drawable.ic_info_black_24dp, R.drawable.ic_help_black_24dp, R.drawable.ic_feedback_black_24dp, R.drawable.ic_share_black_24dp, R.drawable.ic_settings_black_24dp, R.drawable.ic_exit_to_app_black_24dp};
        String[] titles = {"Home", "Notifications", "Find Friends", "Suggest New Place", "About", "Help", "Feedback", "Share This App", "Settings", "Logout"};

        for (int i=0; i<icons.length && i<titles.length; i++) {
            Information current = new Information();
            current.iconId = icons[i];
            current.title = titles[i];
            data.add(current);
        }

        return data;
    }


    public void setUp(int fragmentID, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentID);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnDrawer) {
                    mUserLearnDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnDrawer + "");
                }

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
////                super.onDrawerSlide(drawerView, slideOffset);
//                if (slideOffset<0.6) {
//                    toolbar.setAlpha(1 - slideOffset);
//                }
//            }
        };

//        if (!mUserLearnDrawer && !mFromSavedInstanceState) {
//            mDrawerLayout.openDrawer(containerView);
//        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    @Override
    public void itemClicked(View view, int position) {

        switch (position) {
            case 0: startActivity(new Intent(getActivity(), MainActivity.class));
                view.setSelected(true);
                break;
            case 3: startActivity(new Intent(getActivity(), SubActivity.class));
                view.setSelected(true);
                break;
        }

    }
}