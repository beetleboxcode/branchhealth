package com.app.branchhealth.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.app.branchhealth.R;
import com.app.branchhealth.fragment.NewsFragment;
import com.app.branchhealth.fragment.PointRewardFragment;
import com.app.branchhealth.fragment.ProductKnowledgeFragment;
import com.app.branchhealth.fragment.SettingFragment;

/**
 * Created by eReFeRHa on 1/3/16.
 */
public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {

    //UI
    private ImageButton tabNews, tabProductKnowledge, tabPointReward, tabSetting, btnBack;
    private ImageButton[] tabArray;
    private int[] tabArrayDrawableDefault = new int[]{R.drawable.tab_news, R.drawable.tab_prod_knowledge,
            R.drawable.tab_point, R.drawable.tab_setting};
    private int[] tabArrayDrawableSelected = new int[]{R.drawable.icon_tab_news_selected, R.drawable.icon_tab_pk_selected,
            R.drawable.icon_tab_point_selected, R.drawable.icon_tab_setting_selected};
    protected AlertDialog.Builder alertDialog;

    //DATA
    private int selectedTab = 0;
    public final static String KEY_SELECTED_TAB = "keySelectedTab";

    @Override
    protected int getLayout() {
        return R.layout.act_main_tab;
    }

    @Override
    protected void configureView() {
        tabNews = (ImageButton) findViewById(R.id.tabNews);
        tabProductKnowledge = (ImageButton) findViewById(R.id.tabProductKnowledge);
        tabPointReward = (ImageButton) findViewById(R.id.tabPointReward);
        tabSetting = (ImageButton) findViewById(R.id.tabSetting);
        tabArray = new ImageButton[]{tabNews, tabProductKnowledge, tabPointReward, tabSetting};

        btnBack = (ImageButton) findViewById(R.id.btnBack);

        tabNews.setOnClickListener(this);
        tabProductKnowledge.setOnClickListener(this);
        tabPointReward.setOnClickListener(this);
        tabSetting.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        Intent intent = getIntent();
        if(intent.hasExtra(KEY_SELECTED_TAB)){
            selectedTab = intent.getIntExtra(KEY_SELECTED_TAB, 0);
        }

        Fragment fragment = null;
        switch (selectedTab){
            case 0:
                fragment = new NewsFragment();
                break;
            case 2:
                fragment = new PointRewardFragment();
                break;
        }
        clearStackTab(fragment);
        clearTab();

        alertDialog = new AlertDialog.Builder(this)
                .setTitle("Info")
                .setMessage("Apakah anda ingin keluar aplikasi ?")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("Tidak", null);
        alertDialog.setCancelable(false);
    }

    @Override
    protected int getContainerBody() {
        return R.id.containerFragmentDefaultBody;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.tabNews:
                selectedTab = 0;
                clearStackTab(new NewsFragment());
                break;
            case R.id.tabProductKnowledge:
                selectedTab = 1;
                clearStackTab(new ProductKnowledgeFragment());
                break;
            case R.id.tabPointReward:
                selectedTab = 2;
                clearStackTab(new PointRewardFragment());
                break;
            case R.id.tabSetting:
                selectedTab = 3;
                clearStackTab(new SettingFragment());
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
        }

        clearTab();
    }

    private void setVisibilityBackButton(int visibility){
        btnBack.setVisibility(visibility);
    }

    private void clearStackTab(Fragment fragment){
        setVisibilityBackButton(View.INVISIBLE);
        clearStack();
        swap(fragment, false);
    }

    private void clearTab(){
        for(int i = 0; i < tabArray.length; i++){
            ImageButton tab = tabArray[i];
            if(tab != null){
                //set tab ke awal
                if(selectedTab == i){
                    tab.setImageResource(tabArrayDrawableSelected[i]);
                }
                else{
                    tab.setImageResource(tabArrayDrawableDefault[i]);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if(count == 0){
            alertDialog.show();
        }
        else {
            super.onBackPressed();
        }
    }
}
