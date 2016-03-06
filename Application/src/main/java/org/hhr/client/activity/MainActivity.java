/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.hhr.client.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import org.hhr.client.R;
import org.hhr.client.fragment.AnkiFragment;
import org.hhr.client.fragment.BtSyncFragment;
import org.hhr.client.fragment.VideoFragment;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;
    ActionBar actionBar;
    FragmentPagerAdapter fp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_main);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        actionBar = getSupportActionBar();
        fp = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch(position){
                    case 0:
                        return new VideoFragment();
                    case 1:
                        return new AnkiFragment();
                    case 2:
                        return new BtSyncFragment();
                    default:
                        break;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        mViewPager.setAdapter(fp);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.

                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        TabListener tabListener = new TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        actionBar.addTab(actionBar.newTab().setText("Video").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Anki").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("BtSync").setTabListener(tabListener));
    }

    // BEGIN_INCLUDE(create_menu)
    /**
     * Use this method to instantiate your menu, and add your items to it. You
     * should return true if you have added items to it and want the menu to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate our menu from the resources by using the menu inflater.
        getMenuInflater().inflate(R.menu.main, menu);

        /*
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search_language).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        */
        return true;
    }

    /**
     * This method is called when one of the menu items to selected. These items
     * can be on the Action Bar, the overflow menu, or the standard options menu. You
     * should return true if you handle the selection.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //case R.id.menu_search_language:
                // Associate searchable configuration with the SearchView
        }

        return super.onOptionsItemSelected(item);
    }
    // END_INCLUDE(menu_item_selected)
}
