package com.app.branchhealth.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.activity.BaseFragmentActivity;
import com.app.branchhealth.adapter.ProductKnowledgeListAdapter;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.listener.ProductListener;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.model.MainProductKnowledgeModel;
import com.app.branchhealth.model.NewsModel;
import com.app.branchhealth.model.ProductKnowledgeModel;

import java.util.ArrayList;

import fr.rolandl.carousel.Carousel;
import fr.rolandl.carousel.CarouselAdapter;
import fr.rolandl.carousel.CarouselBaseAdapter;

/**
 * Created by eReFeRHa on 1/3/16.
 */
public class ProductKnowledgeListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, HTTPListener.OnHTTPListener {

    //UI
    private SwipeRefreshLayout swipeProdKnowledgeList;
    private Carousel carouselProdKnowledge;

    //DATA
    private CarouselAdapter adapter;
    private ArrayList<ProductKnowledgeModel> productKnowledgeModels;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_product_knwoledge_list, container, false);
        carouselProdKnowledge = (Carousel) rootView.findViewById(R.id.carouselProdKnowledge);

        carouselProdKnowledge.setOnItemClickListener(new CarouselBaseAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(CarouselBaseAdapter<?> carouselBaseAdapter, View view, int position, long id) {
                selectedData(position);
                carouselProdKnowledge.scrollToChild(position);
            }

        });

        swipeProdKnowledgeList = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeProdKnowledgeList);
        swipeProdKnowledgeList.setOnRefreshListener(this);

        rootView.findViewById(R.id.txtProdDetail).setOnClickListener(this);

        if(productKnowledgeModels == null){
            ProductListener productListener = new ProductListener();
            CommonPostRequest commonPostRequest = new CommonPostRequest();
            commonPostRequest.setUsername(AppUserPreference.getStringData(getContext(), AppUserPreference.KEY_USERNAME));
            productListener.setCommonPostRequest(commonPostRequest);
            productListener.setOnHTTPListener(this);
            productListener.callRequest(getContext());
        }
        else{
            adapter = new ProductKnowledgeListAdapter(getContext(), productKnowledgeModels);
            carouselProdKnowledge.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onRefresh() {
        swipeProdKnowledgeList.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.txtProdDetail:
                int position = carouselProdKnowledge.getSelectedItemPosition();
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
            adapter = new ProductKnowledgeListAdapter(getContext(), productKnowledgeModels);
            carouselProdKnowledge.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetErrorResponse(VolleyError error) {

    }
}
