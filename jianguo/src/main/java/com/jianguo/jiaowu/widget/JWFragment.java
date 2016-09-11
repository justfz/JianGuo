package com.jianguo.jiaowu.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jianguo.DB.JianGuoDB;
import com.jianguo.R;
import com.jianguo.jiaowu.JWUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifane on 2016/5/28 0028.
 */
public class JWFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyAdapter myAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jw, null);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_jw);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_jw);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText("课表"));
        tabLayout.addTab(tabLayout.newTab().setText("成绩"));
        tabLayout.addTab(tabLayout.newTab().setText("考试安排"));
        myAdapter = new MyAdapter(getChildFragmentManager());
        myAdapter.addFragment(new ScheduleFragment(), "课表");
        myAdapter.addFragment(new ScoreFragment(), "成绩");
        myAdapter.addFragment(new TestFragment(), "考试安排");
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


    public class MyAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }
    }

}
