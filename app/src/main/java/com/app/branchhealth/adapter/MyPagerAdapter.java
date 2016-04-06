package com.app.branchhealth.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.app.branchhealth.R;
import com.app.branchhealth.fragment.MyFragment;
import com.app.branchhealth.fragment.ProductKnowledgeListFragment2;
import com.app.branchhealth.view.MyLinearLayout;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by eReFeRHa on 27/3/16.
 */
public class MyPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {


    private boolean swipedLeft=false;
    private int lastPage=9;
    private MyLinearLayout cur = null;
    private MyLinearLayout next = null;
    private MyLinearLayout prev = null;
    private MyLinearLayout prevprev = null;
    private MyLinearLayout nextnext = null;
    private ProductKnowledgeListFragment2 productKnowledgeListFragment2;
    private FragmentManager fm;
    private float scale;
    private boolean IsBlured;
    private static float minAlpha=0.6f;
    private static float maxAlpha=1f;
    private static float minDegree=60.0f;
    private int counter=0;

    public static float getMinDegree()
    {
        return minDegree;
    }
    public static float getMinAlpha()
    {
        return minAlpha;
    }
    public static float getMaxAlpha()
    {
        return maxAlpha;
    }

    public MyPagerAdapter(ProductKnowledgeListFragment2 productKnowledgeListFragment2, FragmentManager fm) {
        super(fm);
        this.fm = fm;
        this.productKnowledgeListFragment2 = productKnowledgeListFragment2;
    }

    @Override
    public Fragment getItem(int position) {
        // make the first pager bigger than others
        if (position == 0)
            scale = MyLinearLayout.BIG_SCALE;
        else {
            scale = MyLinearLayout.SMALL_SCALE;
            IsBlured=true;
        }

        Fragment curFragment = MyFragment.newInstance(productKnowledgeListFragment2.getContext(), position, scale, IsBlured, productKnowledgeListFragment2.productKnowledgeModels.get(position));
        cur = getRootView(position);
        next = getRootView(position +1);
        prev = getRootView(position -1);

        return curFragment;
    }

    @Override
    public int getCount()
    {
        return productKnowledgeListFragment2.productKnowledgeModels.size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset >= 0f && positionOffset <= 1f) {
            positionOffset=positionOffset*positionOffset;
            cur = getRootView(position);
            next = getRootView(position +1);
            prev = getRootView(position -1);
            nextnext=getRootView(position +2);

            ViewHelper.setAlpha(cur, maxAlpha - 0.5f * positionOffset);
            ViewHelper.setAlpha(next, minAlpha+0.5f*positionOffset);
            ViewHelper.setAlpha(prev, minAlpha+0.5f*positionOffset);


            if(nextnext!=null) {
                ViewHelper.setAlpha(nextnext, minAlpha);
                ViewHelper.setRotationY(nextnext, -minDegree);
            }
            if(cur!=null) {
                cur.setScaleBoth(MyLinearLayout.BIG_SCALE - MyLinearLayout.DIFF_SCALE * positionOffset);
                ViewHelper.setRotationY(cur, 0);
            }

            if(next!=null) {
                next.setScaleBoth(MyLinearLayout.SMALL_SCALE + MyLinearLayout.DIFF_SCALE * positionOffset);
                ViewHelper.setRotationY(next, -minDegree);
            }
            if(prev!=null) {
                ViewHelper.setRotationY(prev, minDegree);
            }

			/*To animate it properly we must understand swipe direction
			 * this code adjusts the rotation according to direction.
			 */
            if(swipedLeft) {
                if(next!=null)
                    ViewHelper.setRotationY(next, -minDegree+minDegree*positionOffset);
                if(cur!=null)
                    ViewHelper.setRotationY(cur, 0+minDegree*positionOffset);
            }
            else {
                if(next!=null)
                    ViewHelper.setRotationY(next, -minDegree+minDegree*positionOffset);
                if(cur!=null) {
                    ViewHelper.setRotationY(cur, 0+minDegree*positionOffset);
                }
            }
        }
        if(positionOffset>=1f) {
            ViewHelper.setAlpha(cur, maxAlpha);
        }
    }

    @Override
    public void onPageSelected(int position) {

/*
 * to get finger swipe direction
 */
        if(lastPage<=position) {
            swipedLeft=true;
        }
        else if(lastPage>position) {
            swipedLeft=false;
        }
        lastPage=position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private MyLinearLayout getRootView(int position) {
        MyLinearLayout ly;
        try {
            ly = (MyLinearLayout) fm.findFragmentByTag(this.getFragmentTag(position)).getView().findViewById(R.id.root);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }
        if(ly!=null)
            return ly;
        return null;
    }

    private String getFragmentTag(int position) {
        return "android:switcher:" + productKnowledgeListFragment2.pager.getId() + ":" + position;
    }
}
