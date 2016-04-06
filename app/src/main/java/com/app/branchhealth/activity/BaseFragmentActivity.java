package com.app.branchhealth.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.app.branchhealth.util.FragmentUtil;

/**
 * Created by eReFeRHa on 29/2/16.
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        configureView();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onAttachedToWindow() {
        getWindow().setFormat(PixelFormat.RGBA_8888);
        super.onAttachedToWindow();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    public void goToNextPage(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected abstract int getLayout();
    protected abstract void configureView();
    protected abstract int getContainerBody();

    // SWAP ANTAR FRAGMENT
    public void swap(int containerBody, Fragment fragment, boolean isAddToBackStack){
        swap(containerBody, fragment, isAddToBackStack, isAddToBackStack);
    }

    public void swap(int containerBody, Fragment fragment, boolean isAddToBackStack, boolean isUsingAnimation){
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentUtil.getInstance(fragmentManager, containerBody).swap(fragment, isAddToBackStack, isUsingAnimation);
        }
    }

    public void swap(Fragment fragment, boolean isAddToBackStack){
        swap(getContainerBody(), fragment, isAddToBackStack);
    }

    public void clearStack(){
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
