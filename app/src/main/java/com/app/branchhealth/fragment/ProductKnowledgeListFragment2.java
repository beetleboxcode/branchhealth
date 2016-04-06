package com.app.branchhealth.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.activity.BaseFragmentActivity;
import com.app.branchhealth.adapter.MyPagerAdapter;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.listener.ProductListener;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.model.MainProductKnowledgeModel;
import com.app.branchhealth.model.NewsModel;
import com.app.branchhealth.model.ProductKnowledgeModel;

import java.util.ArrayList;

/**
 * Created by eReFeRHa on 1/3/16.
 */
public class ProductKnowledgeListFragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, HTTPListener.OnHTTPListener {

    // UI
    public ViewPager pager;

    // DATA
    private MyPagerAdapter adapter;
    public ArrayList<ProductKnowledgeModel> productKnowledgeModels;
    private int selectedData = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_product_knwoledge_list_two, container, false);

        rootView.findViewById(R.id.txtProdDetail).setOnClickListener(this);

        pager = (ViewPager) rootView.findViewById(R.id.myviewpager);

        if(productKnowledgeModels == null){
            ProductListener productListener = new ProductListener();
            CommonPostRequest commonPostRequest = new CommonPostRequest();
            commonPostRequest.setUsername(AppUserPreference.getStringData(getContext(), AppUserPreference.KEY_USERNAME));
            productListener.setCommonPostRequest(commonPostRequest);
            productListener.setOnHTTPListener(this);
            productListener.callRequest(getContext());
        }
        else{
            configureCarouselView();
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.txtProdDetail:
                int position = pager.getCurrentItem();
                selectedData = position;
                selectedData(position);
                break;
        }
    }

    private void selectedData(int position){
        ProductKnowledgeModel productKnowledgeModel = productKnowledgeModels.get(position);

        NewsModel newsModel = new NewsModel();
        newsModel.setTitle(productKnowledgeModel.getProductName());
        newsModel.setImage("");
        newsModel.setType(0);
        newsModel.setContent(productKnowledgeModel.getDescription());
        newsModel.setItemNewModels(productKnowledgeModel.getItemNewModels());

        openNewsDetailPage(newsModel);
    }

    private void openNewsDetailPage(NewsModel newsModel){
        NewsDetailFragment newsDetailFragment = new ProductKnowledgeDetailFragment();
        newsDetailFragment.setNewsModel(newsModel);

        if(getActivity() != null && getActivity() instanceof BaseFragmentActivity) {
            BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
            activity.swap(R.id.containerFragmentProdKnowledge, newsDetailFragment, true);
        }
    }

    @Override
    public void onGetResponse(BranchHealthModel branchHealthModel) {
        if(branchHealthModel != null && branchHealthModel instanceof MainProductKnowledgeModel){
            MainProductKnowledgeModel mainProductKnowledgeModel = (MainProductKnowledgeModel) branchHealthModel;
            productKnowledgeModels = mainProductKnowledgeModel.getProductKnowledgeModels();
            configureCarouselView();
        }
    }

    @Override
    public void onGetErrorResponse(VolleyError error) {

    }

    private void configureCarouselView(){
        if(adapter == null)
            adapter = new MyPagerAdapter(this, getChildFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(adapter);

        // Set current item to the middle page so we can fling to both
        // directions left and right
        pager.setCurrentItem(selectedData);

        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        pager.setOffscreenPageLimit(3);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        pager.setPageMargin(Integer.parseInt(getString(R.string.pagermargin)));
    }
}
