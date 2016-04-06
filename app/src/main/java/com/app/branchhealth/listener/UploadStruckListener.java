package com.app.branchhealth.listener;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.model.MainNewsModel;
import com.app.branchhealth.model.UploadStruckModel;
import com.app.branchhealth.util.HTTPUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 15/3/16.
 */
public class UploadStruckListener extends HTTPListener {

    private UploadStruckModel uploadStruckModel;

    public void setUploadStruckModel(UploadStruckModel uploadStruckModel){
        this.uploadStruckModel = uploadStruckModel;
    }

    @Override
    public Request<?> getStringRequest() {
        JSONObject jsonObject = null;
        try {
            jsonObject = uploadStruckModel.toJSON();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return new JsonObjectRequest(Request.Method.POST, HTTPUrl.HOST + HTTPUrl.PARAM_UPLOAD_STRUCK, jsonObject,
                getResponseListener(), getErrorListener());
    }

    @Override
    protected void onGetResponse(JSONObject response) throws JSONException {
        CommonPostRequest commonPostRequest = new CommonPostRequest();
        commonPostRequest.fromJSON(response);
        if(onHTTPListener != null)
            onHTTPListener.onGetResponse(commonPostRequest);
    }

    @Override
    protected void onGetErrorResponse(VolleyError error) {
        if(onHTTPListener != null)
            onHTTPListener.onGetErrorResponse(error);
    }
}
