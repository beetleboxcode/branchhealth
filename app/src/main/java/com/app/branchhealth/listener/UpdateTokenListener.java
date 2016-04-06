package com.app.branchhealth.listener;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.branchhealth.model.UpdateTokenModel;
import com.app.branchhealth.util.HTTPUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 15/3/16.
 */
public class UpdateTokenListener extends HTTPListener {

    private UpdateTokenModel updateTokenModel;

    public void setUpdateTokenModel(UpdateTokenModel updateTokenModel){
        this.updateTokenModel = updateTokenModel;
    }

    @Override
    public Request<?> getStringRequest() {
        JSONObject jsonObject = null;
        try {
            jsonObject = updateTokenModel.toJSON();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return new JsonObjectRequest(Request.Method.POST, HTTPUrl.HOST + HTTPUrl.PARAM_UPDATE_TOKEN, jsonObject,
                getResponseListener(), getErrorListener());
    }

    @Override
    protected void onGetResponse(JSONObject response) {
        if(onHTTPListener != null)
            onHTTPListener.onGetResponse(null);
    }

    @Override
    protected void onGetErrorResponse(VolleyError error) {
        if(onHTTPListener != null)
            onHTTPListener.onGetErrorResponse(error);
    }
}
