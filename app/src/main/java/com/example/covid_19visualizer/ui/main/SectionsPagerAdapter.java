package com.example.covid_19visualizer.ui.main;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.covid_19visualizer.R;

import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int[] TAB_TITLES = new int[] {R.string.tab_text_1,
                                                       R.string.tab_text_2,
                                                       R.string.tab_text_3};
    private final Context mContext;

    public List<String> fragmentClassNames = new ArrayList<String>();

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        fragmentClassNames.add(0, MapsFragment.class.getName());
        fragmentClassNames.add(1, InfoFragment.class.getName());
        fragmentClassNames.add(2, NewsFragment.class.getName());

//        fragmentClassNames.add(0, ".ui.main.MapsFragment");
//        fragmentClassNames.add(1, ".ui.main.InfoFragment");
//        fragmentClassNames.add(2, ".ui.main.NewsFragment");
    }

    public Fragment getItem(int position) {
        return Fragment.instantiate(mContext, fragmentClassNames.get(position));
    }

    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    public int getCount() {
        return 3;
    }
}
