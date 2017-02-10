package pers.zhentao.refereeresourceclient.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

import java.util.jar.Attributes;

/**
 * Created by ZhangZT on 2016/3/16 19:22.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class MyViewPager extends ViewPager {

    private boolean canSlide = true;
    public MyViewPager(Context context) {
        super(context);
    }

    public void cancleSlide(){
        canSlide = false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(canSlide)return super.onTouchEvent(event);
        else return false;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        if(canSlide)return super.onInterceptTouchEvent(event);
        else return false;
    }
    @Override
    public void setCurrentItem(int pos){
        super.setCurrentItem(pos);
    }
    @Override
    public int getCurrentItem(){
        return super.getCurrentItem();
    }
}
