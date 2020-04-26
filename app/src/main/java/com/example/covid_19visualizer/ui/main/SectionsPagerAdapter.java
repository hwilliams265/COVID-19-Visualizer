package com.example.covid_19visualizer.ui.main;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.covid_19visualizer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * File created by: Harry
 *
 * This class allows us to link the fragments (InfoFragment, MapsFragment, etc.) to the tabs (Map
 * Screen, Info, etc.).
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int[] TAB_TITLES = new int[] {R.string.tab_text_1,
                                                       R.string.tab_text_2,
                                                       R.string.tab_text_3};

    //  Context is basically a catch-all class for providing info about the current app such as the
    //  type of device the app is running on, the default language of the user, etc. When an object
    //  of this class is instantiated by MainActivity.onCreate(), MainActivity itself is inputted
    //  as Context.
    private final Context context;

    public List<String> fragmentClassNames = new ArrayList<String>();

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        // Instantiate the parent class with the FragmentManager object.
        super(fm);
        this.context = context;
        // Populate the string list with the names of the classes.
        fragmentClassNames.add(0, MapsFragment.class.getName());
        fragmentClassNames.add(1, InfoFragment.class.getName());
        fragmentClassNames.add(2, NewsFragment.class.getName());
    }

    // When the user switches between tabs, this method is automatically called.
    public Fragment getItem(int position) {
        // Fragment.instantiate() will instantiate our Fragment children as Fragments. If we just
        // instantiated them the normal way, we would get a type-casting error, because InfoFragment
        //  is not the same type as Fragment (for example).
        return Fragment.instantiate(context, fragmentClassNames.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

    public int getCount() {
        return 3;
    }
}
