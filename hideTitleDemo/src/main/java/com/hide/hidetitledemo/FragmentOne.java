package com.hide.hidetitledemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;

public class FragmentOne extends BaseFragment implements AnimatorListener {
    
    private View mLayout;
    private View mSearchlayout;
    private MainActivity.MyOnTouchListener myOnTouchListener;
    private boolean mIsTitleHide = false;
    private boolean mIsAnim = false;
    private float lastX = 0;
    private float lastY = 0;

    public FragmentOne() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLayout = getActivity().findViewById(R.id.layout);
        mSearchlayout = getActivity().findViewById(R.id.search_layout);
        initListener();
    }


    private void initListener() {
        myOnTouchListener = new MainActivity.MyOnTouchListener() {
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                return dispathTouchEvent(ev);
            }
        };
        ((MainActivity) getActivity()).registerMyOnTouchListener(myOnTouchListener);
    }
    
    private boolean isDown = false;
    private boolean isUp = false;
    private boolean dispathTouchEvent(MotionEvent event){
        if (mIsAnim) {  
            return false;  
        }  
        final int action = event.getAction();  
        
        float x = event.getX();  
        float y = event.getY();  
        
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY ? true : false;
                lastY = y;
                lastX = x;
                isUp = dX < 8 && dY > 8 && !mIsTitleHide && !down ;
                isDown = dX < 8 && dY > 8 && mIsTitleHide && down;
                if (isUp) {
                    View view = this.mLayout;
                    float[] f = new float[2];
                    f[0] = 0.0F;
                    f[1] = -mSearchlayout.getHeight();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.setDuration(200);
                    animator1.start();
                    animator1.addListener(this);
                    setMarginTop(mLayout.getHeight() - mSearchlayout.getHeight());
                } else if (isDown) {
                    View view = this.mLayout;
                    float[] f = new float[2];
                    f[0] = -mSearchlayout.getHeight();
                    f[1] = 0F;
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setDuration(200);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.start();
                    animator1.addListener(this);
                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
                mIsAnim = true;
                break;
            default:
                return false;
            }
        return false;
        
    }

    @Override
    public void onAnimationCancel(Animator arg0) {
        
    }


    @Override
    public void onAnimationEnd(Animator arg0) {
        if(isDown){
            setMarginTop(mLayout.getHeight());
        }
         mIsAnim = false;               
    }


    @Override
    public void onAnimationRepeat(Animator arg0) {
        
    }


    @Override
    public void onAnimationStart(Animator arg0) {
        
    }
    
    
}
