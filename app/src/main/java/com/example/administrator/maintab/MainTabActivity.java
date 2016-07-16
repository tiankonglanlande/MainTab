package com.example.administrator.maintab;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainTabActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    int[]btIds={R.id.bt,R.id.bt2,R.id.bt3,R.id.bt4};
    ArrayList<ImageButton> ivList;
    ArrayList<Fragment> mainTabFgList;
    private PagerAdapter pagetAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intView();
        setListener();
    }
    private void setListener() {
        for (int i = 0; i < ivList.size(); i++) {
            final ImageButton iv=ivList.get(i);
            final int index=i;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!iv.isSelected()){
                        resetIvBtnSelect();
                        selectIvBtnSelect(index);
                        mViewPager.setCurrentItem(index);
                    }
                }
            });
        }
    }

    public void resetIvBtnSelect() {
        for (int a=0;a<ivList.size();a++){
            ImageButton ivOther=ivList.get(a);
            ivOther.setSelected(false);
        }
    }

    private void intView() {
        //初始化顶部图片按钮集合
        ivList=new ArrayList<ImageButton>();
        //初始fragment集合
        mainTabFgList=new ArrayList<Fragment>();
        mViewPager= (ViewPager) findViewById(R.id.container);
        for (int i=0;i<btIds.length;i++){
            ImageButton iv=(ImageButton)findViewById(btIds[i]);
            if (i==0){
                iv.setSelected(true);
            }else{
                iv.setSelected(false);
            }
            ivList.add(iv);
        }
        for (int i=0;i<ivList.size();i++){
            mainTabFgList.add(new OneFragment());
        }
        pagetAdapter=new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagetAdapter);
        mViewPager.addOnPageChangeListener(this);
        //重置所有项为0.5
        resetTabAphal();
        //选中项透明度为1
        selectTabAphal(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        resetTabAphal();
        selectTabAphal(position);
        resetIvBtnSelect();
        selectIvBtnSelect(position);
    }

    private void selectIvBtnSelect(int position) {
        ImageButton ivbtn=ivList.get(position);
        ivbtn.setSelected(true);
    }

    private void resetTabAphal() {
        for (ImageButton iv:ivList){
            iv.setAlpha(0.5f);
        }
    }
    private void selectTabAphal(int position) {
        for (int i=0;i<ivList.size();i++){
            if (position==i){
                ivList.get(i).setAlpha(1.0f);

            }
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class PagerAdapter extends FragmentPagerAdapter{

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mainTabFgList.get(position);
        }

        @Override
        public int getCount() {
            return mainTabFgList.size();
        }
    }

}
