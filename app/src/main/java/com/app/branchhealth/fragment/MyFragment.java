package com.app.branchhealth.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.toolbox.NetworkImageView;
import com.app.branchhealth.R;
import com.app.branchhealth.activity.BaseFragmentActivity;
import com.app.branchhealth.adapter.MyPagerAdapter;
import com.app.branchhealth.model.NewsModel;
import com.app.branchhealth.model.ProductKnowledgeModel;
import com.app.branchhealth.services.SingletonHTTPRequest;
import com.app.branchhealth.view.MyLinearLayout;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by eReFeRHa on 27/3/16.
 */
public class MyFragment extends Fragment implements View.OnClickListener {

    public static Fragment newInstance(Context context, int pos, float scale, boolean IsBlured, ProductKnowledgeModel productKnowledgeModel)
    {

        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);
        b.putBoolean("IsBlured", IsBlured);
        b.putSerializable("data", productKnowledgeModel);
        return Fragment.instantiate(context, MyFragment.class.getName(), b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout l = (LinearLayout) inflater.inflate(R.layout.item_carousel, container, false);
        MyLinearLayout root = (MyLinearLayout) l.findViewById(R.id.root);
        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);
        boolean isBlured=this.getArguments().getBoolean("IsBlured");
        if(isBlured) {
            ViewHelper.setAlpha(root,MyPagerAdapter.getMinAlpha());
            ViewHelper.setRotationY(root, MyPagerAdapter.getMinDegree());
        }

        productKnowledgeModel = (ProductKnowledgeModel) this.getArguments().getSerializable("data");
        NetworkImageView itemImage = (NetworkImageView) l.findViewById(R.id.content);
        itemImage.setDefaultImageResId(productKnowledgeModel.getDefaultImage());
        itemImage.setImageUrl(productKnowledgeModel.getUrl(), SingletonHTTPRequest.getInstance(getContext()).getImageLoader());

        root.setOnClickListener(this);

        return l;
    }

    private ProductKnowledgeModel productKnowledgeModel;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.root:
                selectedData();
                break;
        }
    }

    private void selectedData(){
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
}