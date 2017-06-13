package com.agoogler.rafi.w3news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by rafi on 6/12/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter  {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new WSJFragment();
        } else if (position == 1)  {
            fragment = new BBCFragment();
        }
        else if (position == 2)  {
            fragment = new ESPNFragment();
        }
        else if (position == 3)  {
            fragment = new NewYorkTimesFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Wall Street Journel";
        }
        else if (position == 1)
        {
            title = "BBC";
        }
        else if (position == 2)
        {
            title = "ESPN";
        }
        else if (position == 3)
        {
            title = "New York Times";
        }

        return title;
    }
}

