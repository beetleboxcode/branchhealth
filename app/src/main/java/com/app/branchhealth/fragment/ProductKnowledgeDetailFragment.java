package com.app.branchhealth.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.branchhealth.R;
import com.app.branchhealth.activity.BaseFragmentActivity;

/**
 * Created by eReFeRHa on 1/3/16.
 */
public class ProductKnowledgeDetailFragment extends NewsDetailFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_product_knowledge_detail, container, false);
        configureView(rootView);
        return rootView;
    }
}
