package com.example.administrator.maintab;


import android.graphics.Color;
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


import java.util.ArrayList;

/**
 * A fragment with a Google +1 button.
 */
public class OneFragment extends Fragment {

    private ViewPager pager;
    ArrayList<Fragment> fgTabFgList;
    private View view;
    private InnerPagerAdapter adapter;
    String[]titles={"首页","前端","Android","ios"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_one, container, false);
        intView();
        return view;
    }

    private void intView() {
        pager= (ViewPager) view.findViewById(R.id.pager);
        fgTabFgList=new ArrayList<Fragment>();
        for (int i=0;i<4;i++){
            fgTabFgList.add(new MyFragment());
        }
        adapter=new InnerPagerAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);

        TabLayout tablayout=(TabLayout)view.findViewById(R.id.tabs);
        tablayout.setTabTextColors(getResources().getColor(R.color.gray),getResources().getColor(R.color.blue));
        tablayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.blue));
        tablayout.setupWithViewPager(pager);
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    class InnerPagerAdapter extends FragmentPagerAdapter {

        public InnerPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fgTabFgList.get(position);
        }

        @Override
        public int getCount() {
            return fgTabFgList.size();
        }
        public CharSequence getPageTitle(int position){
            String title=titles[position];
            return title;
        }
    }

}
