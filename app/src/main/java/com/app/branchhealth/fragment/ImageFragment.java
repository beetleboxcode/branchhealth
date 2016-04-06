package com.app.branchhealth.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.NetworkImageView;
import com.app.branchhealth.R;
import com.app.branchhealth.model.ItemNewsModel;
import com.app.branchhealth.services.SingletonHTTPRequest;

/**
 * Created by eReFeRHa on 5/8/15.
 */
public class ImageFragment extends Fragment {

    private ItemNewsModel itemNewsModel;

    public ImageFragment() {
        super();
    }

    public void setItemNewsModel(ItemNewsModel itemNewsModel) {
        this.itemNewsModel = itemNewsModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NetworkImageView image = new NetworkImageView(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        image.setLayoutParams(params);
        image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        image.setDefaultImageResId(itemNewsModel.getDefaultImage());
        image.setImageUrl(itemNewsModel.getUrl(), SingletonHTTPRequest.getInstance(getContext()).getImageLoader());

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(params);
        layout.setGravity(Gravity.CENTER);
        layout.addView(image);

        return layout;
    }
}
