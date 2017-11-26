package com.kingdatasolutions.sintraba.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingdatasolutions.sintraba.MainActivity;
import com.kingdatasolutions.sintraba.R;
import com.kingdatasolutions.sintraba.SinTrabaApp;
import com.kingdatasolutions.sintraba.activity.InformationActivity;
import com.kingdatasolutions.sintraba.activity.ProfileActivity;
import com.kingdatasolutions.sintraba.activity.SettingActivity;
import com.kingdatasolutions.sintraba.adapter.MenuAdapter;
import com.kingdatasolutions.sintraba.data.SinTrabaDBAdapter;
import com.kingdatasolutions.sintraba.datamodel.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class MenuFragment extends Fragment implements MenuAdapter.ClickListener {

    private RecyclerView mRecyclerView;
    public static final String PREF_FILE_NAME = "TEST_PREF";
    public static final String KEY_USER_LEARNED_DRAWER = "USER_LEARNED_DRAWER";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private MenuAdapter mAdapter;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View mContainerView;
    private SinTrabaDBAdapter dbAdapter;

    public MenuFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
        dbAdapter = new SinTrabaDBAdapter(SinTrabaApp.getAppContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_menu, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.menu_recycler);
        mAdapter = new MenuAdapter(getActivity(), getData());
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public List<MenuItem> getData() {
        List<MenuItem> data = new ArrayList<>();
        int[] icons = {
                R.drawable.ic_menu_job,
                R.drawable.ic_menu_event,
                R.drawable.ic_menu_workshop,
                R.drawable.ic_menu_setting
        };
        String[] titles = {
                getString(R.string.menu_job),
                getString(R.string.menu_event),
                getString(R.string.menu_workshop),
                getString(R.string.menu_setting)
        };
        for (int i = 0; i < icons.length && i < titles.length; i++) {
            MenuItem current = new MenuItem();
            current.id_icon = icons[i];
            current.title = titles[i];
            data.add(current);
        }
        return data;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mContainerView);
        }
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
            case 0:
                startActivity(new Intent(getActivity(), MainActivity.class));
                if (!(getActivity().getClass().getSimpleName().equals("MainActivity"))) {
                    getActivity().finish();
                } else {
                    mDrawerLayout.closeDrawer(mContainerView);
                }
                break;
            case 1:
                startActivity(new Intent(getActivity(), InformationActivity.class));
                if (!(getActivity().getClass().getSimpleName().equals("MainActivity"))) {
                    getActivity().finish();
                } else {
                    mDrawerLayout.closeDrawer(mContainerView);
                }
                break;
            case 2:
                if (!(getActivity().getClass().getSimpleName().equals("MainActivity"))) {
                    getActivity().finish();
                } else {
                    mDrawerLayout.closeDrawer(mContainerView);
                }
                break;
            case 3:
                startActivity(new Intent(getActivity(), ProfileActivity.class));
                if (!(getActivity().getClass().getSimpleName().equals("MainActivity"))) {
                    getActivity().finish();
                } else {
                    mDrawerLayout.closeDrawer(mContainerView);
                }
                break;
            case 4:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                if (!(getActivity().getClass().getSimpleName().equals("MainActivity"))) {
                    getActivity().finish();
                } else {
                    mDrawerLayout.closeDrawer(mContainerView);
                }
                break;
            default:
                break;
        }
    }
}
