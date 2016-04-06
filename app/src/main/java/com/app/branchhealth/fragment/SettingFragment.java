package com.app.branchhealth.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.app.branchhealth.R;
import com.app.branchhealth.activity.BaseFragmentActivity;
import com.app.branchhealth.util.ImageUtils;

import java.util.HashMap;

/**
 * Created by eReFeRHa on 1/3/16.
 */
public class SettingFragment extends Fragment implements TabHost.OnTabChangeListener {

    //UI
    private TabHost host;

    //DATA
    private String currentTag = "";
    private HashMap<String, Fragment> mapTabFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_setting, container, false);
        configureView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void configureView(View view){
        if(view != null){
            host = (TabHost) view.findViewById(android.R.id.tabhost);
            host.setup();

            final String tagProfile = "tab1";
            TabHost.TabSpec profileSpec = host.newTabSpec(tagProfile);
            //Drawable drawableProfile = ContextCompat.getDrawable(getContext(), R.drawable.tab_profile);
            profileSpec.setIndicator("", ImageUtils.resizeImage(getContext(), 0.4f, R.drawable.tab_profile));
            profileSpec.setContent(new TabFactory(getContext()));
            host.addTab(profileSpec);

            final String tagFAQ = "tab2";
            TabHost.TabSpec faqSpec = host.newTabSpec(tagFAQ);
            //Drawable drawableFaq = ContextCompat.getDrawable(getContext(), R.drawable.tab_faq);
            faqSpec.setIndicator("", ImageUtils.resizeImage(getContext(), 0.4f, R.drawable.tab_faq));
            faqSpec.setContent(new TabFactory(getContext()));
            host.addTab(faqSpec);

            final String tagContactUs = "tab3";
            TabHost.TabSpec contactusSpec = host.newTabSpec(tagContactUs);
            //Drawable drawableContactUs = ContextCompat.getDrawable(getContext(), R.drawable.tab_contactus);
            contactusSpec.setIndicator("", ImageUtils.resizeImage(getContext(), 0.4f, R.drawable.tab_contactus));
            contactusSpec.setContent(new TabFactory(getContext()));
            host.addTab(contactusSpec);

            host.setOnTabChangedListener(this);

            mapTabFragment = new HashMap<>();
            mapTabFragment.put(tagProfile, new ProfileFragment());
            mapTabFragment.put(tagFAQ, new FAQFragment());
            mapTabFragment.put(tagContactUs, new ContactUsFragment());

            onTabChanged(tagProfile);
        }
    }

    @Override
    public void onTabChanged(String tag) {
        if(!tag.equals(currentTag)){
            if(getActivity() != null && getActivity() instanceof BaseFragmentActivity) {
                BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
                activity.clearStack();
                activity.swap(R.id.containerFragmentSetting, mapTabFragment.get(tag), false, false);

                currentTag = tag;
            }

            for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
                host.getTabWidget().getChildAt(i).setBackgroundResource(R.color.colorPrimaryDark); // unselected
            }
            host.getTabWidget().getChildAt(host.getCurrentTab()).setBackgroundResource(R.color.white);

        }
    }

    private class TabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        /**
         * @param context
         */
        public TabFactory(Context context) {
            mContext = context;
        }

        /** (non-Javadoc)
         * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
         */
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }

    }
}
