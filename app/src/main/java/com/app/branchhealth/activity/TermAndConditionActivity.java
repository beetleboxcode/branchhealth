package com.app.branchhealth.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.listener.TermAndConditionListener;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.TermAndConditionModel;

/**
 * Created by eReFeRHa on 26/2/16.
 */
public class TermAndConditionActivity extends BaseFragmentActivity implements View.OnClickListener, HTTPListener.OnHTTPListener {

    //UI
    private WebView tncWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.act_term_condition;
    }

    @Override
    protected int getContainerBody() {
        return 0;
    }

    @Override
    protected void configureView() {
        tncWebView = (WebView) findViewById(R.id.wvTermText);
        tncWebView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        //tncWebView.loadUrl(HTTPUrl.HOST + HTTPUrl.PARAM_TERM_AND_CONDITION);
        //tncWebView.loadUrl("file:///android_asset/html/tnc.html");
        //tncWebView.getSettings().setUseWideViewPort(true);
        tncWebView.getSettings().setJavaScriptEnabled(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            tncWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        final Button btnAggree = (Button) findViewById(R.id.btnAgree);
        btnAggree.setOnClickListener(this);
        findViewById(R.id.btnDisagree).setOnClickListener(this);

        ((CheckBox) findViewById(R.id.chkTncAgree)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                btnAggree.setEnabled(isCheck);
            }
        });

        TermAndConditionListener termAndConditionListener = new TermAndConditionListener();
        termAndConditionListener.setOnHTTPListener(this);
        termAndConditionListener.callRequest(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnAgree:
                AppUserPreference.putData(this, AppUserPreference.KEY_HAS_ALREADY_OPEN_APPS, true);
                goToNextPage(LoginActivity.class);
                finish();
                break;
            case R.id.btnDisagree:
                finish();
                break;
        }
    }

    @Override
    public void onGetResponse(BranchHealthModel branchHealthModel) {
        if(branchHealthModel != null && branchHealthModel instanceof TermAndConditionModel){
            TermAndConditionModel termAndConditionModel = (TermAndConditionModel) branchHealthModel;
            String termAndCondition = termAndConditionModel.getTermAndCondition();
            tncWebView.loadData(termAndCondition, "text/html", "utf-8");
        }
    }

    @Override
    public void onGetErrorResponse(VolleyError error) {

    }
}
