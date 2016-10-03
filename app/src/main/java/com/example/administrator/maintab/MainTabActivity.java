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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainTabActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    int[]btIds={R.id.bt,R.id.bt2,R.id.bt3,R.id.bt4};
    ArrayList<ImageButton> ivList;
    ArrayList<Fragment> mainTabFgList;
    private PagerAdapter pagetAdapter;
    private LinearLayout nav;//底部导航布局
    private LinearLayout vpContainer;

    //加入上滑隐藏底部导航nav下滑显示nav
    private boolean mIsNavHide=false;
    private boolean mIsAnim=false;
    private float lastX=0;
    private float lastY=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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

    private void initView() {
        //底部布局
        nav= (LinearLayout) findViewById(R.id.nav);
        vpContainer=(LinearLayout)findViewById(R.id.vpContainer);
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



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        if(mIsAnim){
            return false;
        }
        final int action=ev.getAction();
        float x=ev.getX();
        float y=ev.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                lastX=x;
                lastY=y;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dx=Math.abs(x-lastX);
                float dy=Math.abs(y-lastY);
                boolean down=y>lastY?true:false;
                lastX=x;
                lastY=y;
                if (dx<8&&dy>8&&!mIsNavHide&&!down){
                    Animation anim= AnimationUtils.loadAnimation(MainTabActivity.this,R.anim.push_top_in);
                    anim.setAnimationListener(myAnimatListener());
                    nav.startAnimation(anim);
                }else if (dx<8&&dy>8&&mIsNavHide&&down){
                    Animation anim= AnimationUtils.loadAnimation(MainTabActivity.this,R.anim.push_top_out);
                    anim.setAnimationListener(myAnimatListener());
                    nav.startAnimation(anim);
                }else{
                    return false;
                }
                mIsNavHide=!mIsNavHide;
                mIsAnim=true;
            default:
                return false;
        }
    }

    private Animation.AnimationListener myAnimatListener() {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if(mIsNavHide){
                    LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) vpContainer.getLayoutParams();
                    lp.setMargins(0,0,0,0);
                    vpContainer.setLayoutParams(lp);
                }else{
                    LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) nav.getLayoutParams();
                    lp.setMargins(0,-getResources().getDimensionPixelSize(R.dimen.nav_height),0,0);
                    nav.setLayoutParams(lp);

                    LinearLayout.LayoutParams lp2= (LinearLayout.LayoutParams) vpContainer.getLayoutParams();
                    lp2.setMargins(0,0,0,0);
                    vpContainer.setLayoutParams(lp2);

                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mIsNavHide){
                    nav.setVisibility(View.GONE);
                }else {
                    nav.setVisibility(View.VISIBLE);
                }
                mIsAnim=false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    //向子界面提供隐藏nav和显示nav导航的方法
    public void toggleNav(){

    }

}
