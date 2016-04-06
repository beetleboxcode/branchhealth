package com.app.branchhealth.listener;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.model.MainNewsModel;
import com.app.branchhealth.util.HTTPUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 15/3/16.
 */
public class NewsListener extends HTTPListener {

    private CommonPostRequest commonPostRequest;

    public void setCommonPostRequest(CommonPostRequest commonPostRequest){
        this.commonPostRequest = commonPostRequest;
    }

    @Override
    protected String getKeyCache() {
        return AppUserPreference.KEY_RESPONSE_NEWS;
    }

    @Override
    public Request<?> getStringRequest() {
        JSONObject jsonObject = null;
        try {
            jsonObject = commonPostRequest.toJSON();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return new JsonObjectRequest(Request.Method.POST, HTTPUrl.HOST + HTTPUrl.PARAM_NEWS, jsonObject,
                getResponseListener(), getErrorListener());
    }

    @Override
    protected void onGetResponse(JSONObject response) throws JSONException {
        MainNewsModel mainNewsModel = new MainNewsModel();
        mainNewsModel.fromJSON(response);
        if(onHTTPListener != null)
            onHTTPListener.onGetResponse(mainNewsModel);
    }

    @Override
    protected void onGetErrorResponse(VolleyError error) {
        processCommonOnGetErrorResponse(error);
    }
}
