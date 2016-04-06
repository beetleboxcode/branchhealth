package com.app.branchhealth.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.app.branchhealth.fragment.ImageFragment;
import com.app.branchhealth.fragment.VideoFragment;
import com.app.branchhealth.model.ItemNewsModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eReFeRHa on 4/8/15.
 */
public class ImageSliderAdapter extends FragmentStatePagerAdapter {

    private ArrayList<ItemNewsModel> itemNewsModels;
    private HashMap<Integer, Fragment> imageFragmentHashMap;

    public ImageSliderAdapter(FragmentManager fm, ArrayList<ItemNewsModel> itemNewsModels) {
        super(fm);
        this.itemNewsModels = itemNewsModels;
        imageFragmentHashMap = new HashMap<Integer, Fragment>();
    }

    public void setPromoArrayList(ArrayList<ItemNewsModel> itemNewsModels) {
        this.itemNewsModels = itemNewsModels;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(itemNewsModels != null)
            return itemNewsModels.size();
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        final ItemNewsModel itemNewsModel = itemNewsModels.get(position);
        Fragment fragment = null;
        String type = itemNewsModel.getType();

        /*
        for(int i = 0; i < getCount(); i++){
            if(imageFragmentHashMap.containsKey(i) && imageFragmentHashMap.get(i) instanceof VideoFragment){
                fragment = imageFragmentHashMap.get(i);
                ((VideoFragment) fragment).stopVideo();
            }
        }
        */

        if(imageFragmentHashMap.containsKey(position)){
            fragment = imageFragmentHashMap.get(position);
        }
        else {
            if (type != null && type.equalsIgnoreCase("video")) {
                fragment = new VideoFragment();
                ((VideoFragment) fragment).setItemNewsModel(itemNewsModel);

            } else {
                fragment = new ImageFragment();
                ((ImageFragment) fragment).setItemNewsModel(itemNewsModel);
            }
            imageFragmentHashMap.put(position, fragment);
        }

        return fragment;
    }
}
