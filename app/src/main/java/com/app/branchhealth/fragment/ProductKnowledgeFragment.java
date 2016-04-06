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
public class ProductKnowledgeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_product_knwoledge, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        openNewsListPage();
    }

    private void openNewsListPage() {
        Fragment fragment = new ProductKnowledgeListFragment2();
        if (getArguments() != null)
            fragment.setArguments(getArguments());

        if(getActivity() != null && getActivity() instanceof BaseFragmentActivity) {
            BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
            activity.swap(R.id.containerFragmentProdKnowledge, fragment, false, false);
        }
    }
}
