package com.example.arezz.datenightdesmoines;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;
    IYelpList currentFrag;

    public TabPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AddFoodListFragment food = new AddFoodListFragment();
                currentFrag = food;
                return food;
            case 1:
                AddEntertainmentListFragment enter = new AddEntertainmentListFragment();
                currentFrag = enter;
                return enter;
            case 2:
                AddDrinksListFragment drink= new AddDrinksListFragment();
                currentFrag = drink;
                return drink;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    public IYelpList getCurrentItem(){
        return currentFrag;
    }


}
