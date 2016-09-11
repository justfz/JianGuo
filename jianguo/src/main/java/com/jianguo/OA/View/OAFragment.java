package com.jianguo.OA.View;

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

import com.jianguo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifane on 2016/8/26 0026.
 */
public class OAFragment extends Fragment implements OAView{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oa,null);
        tabLayout = (TabLayout) view.findViewById(R.id.oa_tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.oa_viewpager);
        tabLayout.addTab(tabLayout.newTab().setText("最新公文"));
        tabLayout.addTab(tabLayout.newTab().setText("公告通知"));
        tabLayout.addTab(tabLayout.newTab().setText("信息快递"));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OAFragment_official(),"最新公文");
        adapter.addFragment(new OAFragment_information(),"公告通知");
        adapter.addFragment(new OAFragment_notice(),"信息快递");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
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
    }
}
