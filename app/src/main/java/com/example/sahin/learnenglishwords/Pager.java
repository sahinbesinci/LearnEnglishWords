package com.example.sahin.learnenglishwords;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    TekrarEttigimKelimelerFragment tab1;
    EzberledigimKelimelerFragment tab2;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
        tab1 = new TekrarEttigimKelimelerFragment();
        tab2 = new EzberledigimKelimelerFragment();
    }
    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                //tab1 = new TekrarEttigimKelimelerFragment();
                return tab1;
            case 1:
                //tab2 = new EzberledigimKelimelerFragment();
                return tab2;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }

}