package pers.zhentao.refereeresourceclient.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangZT on 2016/3/16 16:53.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class MyPagerAdapter extends PagerAdapter {

    List<View> mList = new ArrayList<>();
    public MyPagerAdapter(List<View> viewList){
        this.mList = viewList;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(View view,int position,Object o){
        ((ViewPager)view).removeView(mList.get(position));
    }

    @Override
    public Object instantiateItem(View view,int position){
        ((ViewPager)view).addView(mList.get(position), 0);
        return mList.get(position);
    }
}
