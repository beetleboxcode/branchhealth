package com.app.branchhealth.listener;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.model.MainFAQModel;
import com.app.branchhealth.util.HTTPUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 15/3/16.
 */
public class FAQListener extends HTTPListener {

    @Override
    protected String getKeyCache() {
        return AppUserPreference.KEY_RESPONSE_FAQ;
    }

    @Override
    public Request<?> getStringRequest() {
        JSONObject jsonObject = new JSONObject();
        return new JsonObjectRequest(Request.Method.GET, HTTPUrl.HOST + HTTPUrl.PARAM_FAQ, jsonObject,
                getResponseListener(), getErrorListener());
    }

    @Override
    protected void onGetResponse(JSONObject response) throws JSONException{
        MainFAQModel mainFAQModel = new MainFAQModel();
        mainFAQModel.fromJSON(response);
        if(onHTTPListener != null)
            onHTTPListener.onGetResponse(mainFAQModel);
    }

    @Override
    protected void onGetErrorResponse(VolleyError error) {
        processCommonOnGetErrorResponse(error);
    }
}
