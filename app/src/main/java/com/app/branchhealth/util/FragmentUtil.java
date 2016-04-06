package com.app.branchhealth.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.app.branchhealth.R;

/**
 * Created by eReFeRHa on 14/8/15.
 */
public class FragmentUtil {

    private FragmentManager fragmentManager;
    private int containerBody;
    private static FragmentUtil fragmentUtil;

    public static FragmentUtil getInstance(FragmentManager fragmentManager, int containerBody){
        if(fragmentUtil == null){
            fragmentUtil = new FragmentUtil();
        }
        fragmentUtil.setFragmentManager(fragmentManager);
        fragmentUtil.setContainerBody(containerBody);
        return fragmentUtil;
    }

    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    public void setContainerBody(int containerBody){
        this.containerBody = containerBody;
    }

    public void swap(Fragment fragment, boolean isAddToBackStack, boolean isUseAnim){
        if (fragment != null && fragmentManager != null && containerBody > 0) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(isUseAnim)
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right);
            fragmentTransaction.replace(containerBody, fragment);
            if(isAddToBackStack)
                fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void swap(Fragment fragment, boolean isAddToBackStack) {
        swap(fragment, isAddToBackStack, true);
    }
}
