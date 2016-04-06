package com.app.branchhealth.fragment;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.adapter.ImageSliderAdapter;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.listener.RedeemListener;
import com.app.branchhealth.listener.RegisterEventListener;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.model.GetRewardModel;
import com.app.branchhealth.model.NewsModel;
import com.app.branchhealth.model.RegisterEventModel;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by eReFeRHa on 1/3/16.
 */
public class NewsDetailFragment extends Fragment implements View.OnClickListener, HTTPListener.OnHTTPListener {

    //UI
    private ViewPager itemContentImage;
    protected AlertDialog.Builder alertDialog;

    //DATA
    private NewsModel newsModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_news_detail, container, false);
        configureView(rootView);

        alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Info")
                .setMessage("")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("OK", null);
        alertDialog.setCancelable(false);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnDaftar:
                if(newsModel != null){
                    RegisterEventListener registerEventListener = null;
                    if(newsModel instanceof GetRewardModel){
                        registerEventListener  = new RedeemListener();
                    }
                    else if(newsModel instanceof NewsModel){
                        registerEventListener  = new RegisterEventListener();
                    }
                    RegisterEventModel registerEventModel = new RegisterEventModel();
                    registerEventModel.setUsername(AppUserPreference.getStringData(getContext(), AppUserPreference.KEY_USERNAME));
                    registerEventModel.setId(newsModel.getId());
                    registerEventListener.setRegisterEventModel(registerEventModel);
                    registerEventListener.setOnHTTPListener(this);
                    registerEventListener.callRequest(getContext());
                }
                break;
        }
    }

    protected void configureView(View rootView){
        TextView itemTitle = (TextView) rootView.findViewById(R.id.itemTitle);
        itemTitle.setText(newsModel.getTitle());

        itemContentImage = (ViewPager) rootView.findViewById(R.id.itemContentImage);
        ImageSliderAdapter newsAdapter = new ImageSliderAdapter(getChildFragmentManager(), newsModel.getItemNewModels());
        itemContentImage.setAdapter(newsAdapter);

        CirclePageIndicator mIndicator = (CirclePageIndicator) rootView.findViewById(R.id.indicatorNews);
        mIndicator.setViewPager(itemContentImage);
        mIndicator.setSnap(true);

        WebView wvDetailText = (WebView) rootView.findViewById(R.id.wvDetailText);
        wvDetailText.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        wvDetailText.loadData(newsModel.getContent(), "text/html", "utf-8");
        //wvDetailText.loadUrl("file:///android_asset/html/item.html");
        //wvDetailText.getSettings().setUseWideViewPort(true);
        wvDetailText.getSettings().setJavaScriptEnabled(true);
        wvDetailText.getSettings().setDefaultTextEncodingName("utf-8");
        wvDetailText.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                goToBrowser(url);
                return true;
            }
        });

        Button btnDaftar = (Button) rootView.findViewById(R.id.btnDaftar);
        btnDaftar.setOnClickListener(this);
        btnDaftar.setVisibility((newsModel.getType() == 1) ? View.VISIBLE : View.GONE);
        if(newsModel != null && newsModel instanceof GetRewardModel){
            btnDaftar.setText(R.string.btn_redeem_points);
        }
    }

    public void setNewsModel(NewsModel newsModel) {
        this.newsModel = newsModel;
    }

    private void goToBrowser(String url){
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
        catch (ActivityNotFoundException e){
        }
    }

    @Override
    public void onGetErrorResponse(VolleyError error) {

    }

    @Override
    public void onGetResponse(BranchHealthModel branchHealthModel) {
        String message = "";
        if(branchHealthModel != null && branchHealthModel instanceof CommonPostRequest){
            CommonPostRequest commonPostRequest = (CommonPostRequest) branchHealthModel;
            message = commonPostRequest.getMessage();
        }
        alertDialog.setMessage(message);
        alertDialog.show();
    }
}
