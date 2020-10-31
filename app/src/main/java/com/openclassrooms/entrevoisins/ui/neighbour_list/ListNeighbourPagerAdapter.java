package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return NeighbourFragment.newInstance(position);             //passage de la position tab pour gérer une instancicition différente du RV dans le fragment en fct de la position de tab
    }

    /**
     * get the number of pages
     * @return
     */
    @Override
    public int getCount() {
        return 2;
    }
}