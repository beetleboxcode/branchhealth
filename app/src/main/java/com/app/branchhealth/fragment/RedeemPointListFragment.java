package com.app.branchhealth.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.activity.BaseFragmentActivity;
import com.app.branchhealth.adapter.ItemListCommonAdapter;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.listener.GetRewardListener;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.model.MainGetRewardModel;
import com.app.branchhealth.model.NewsModel;

import java.util.ArrayList;

/**
 * Created by eReFeRHa on 1/3/16.
 */
public class RedeemPointListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, HTTPListener.OnHTTPListener {

    //UI
    private ListView lstNews;
    private SwipeRefreshLayout swipeNewsList;
    private ItemListCommonAdapter itemListCommonAdapter;

    //DATA
    private ArrayList<NewsModel> newsModels = new ArrayList<>();
    private boolean useCache = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_news_list, container, false);
        lstNews = (ListView) rootView.findViewById(R.id.lstNews);
        lstNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemListCommonAdapter adapter = (ItemListCommonAdapter) adapterView.getAdapter();
                NewsModel newsModel = adapter.getItem(i);
                openNewsDetailPage(newsModel);
            }
        });

        swipeNewsList = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeNewsList);
        swipeNewsList.setOnRefreshListener(this);

        itemListCommonAdapter = new ItemListCommonAdapter(getContext(), newsModels);
        lstNews.setAdapter(itemListCommonAdapter);

        useCache = true;
        if(newsModels == null || newsModels.size() == 0){
            callGetRewardListener();
        }
        return rootView;
    }

    private void openNewsDetailPage(NewsModel newsModel){
        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
        newsDetailFragment.setNewsModel(newsModel);

        if(getActivity() != null && getActivity() instanceof BaseFragmentActivity) {
            BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
            activity.swap(R.id.containerFragmentRedeem, newsDetailFragment, true);
        }
    }

    private void callGetRewardListener(){
        GetRewardListener getRewardListener = new GetRewardListener();
        CommonPostRequest commonPostRequest = new CommonPostRequest();
        commonPostRequest.setUsername(AppUserPreference.getStringData(getContext(), AppUserPreference.KEY_USERNAME));
        getRewardListener.setCommonPostRequest(commonPostRequest);
        getRewardListener.setOnHTTPListener(this);
        getRewardListener.callRequest(getContext(), useCache);
    }

    @Override
    public void onRefresh() {
        swipeNewsList.setRefreshing(false);
        useCache = false;
        callGetRewardListener();
    }

    @Override
    public void onGetResponse(BranchHealthModel branchHealthModel) {
        if(branchHealthModel != null && branchHealthModel instanceof MainGetRewardModel) {
            MainGetRewardModel mainGetRewardModel = (MainGetRewardModel) branchHealthModel;
            newsModels = mainGetRewardModel.getRewardsModels();
            itemListCommonAdapter.setNewsModels(newsModels);
        }
        useCache = true;
    }

    @Override
    public void onGetErrorResponse(VolleyError error) {
        useCache = true;
    }
}
