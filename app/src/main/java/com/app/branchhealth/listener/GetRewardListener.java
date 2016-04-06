package com.app.branchhealth.listener;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.model.MainGetRewardModel;
import com.app.branchhealth.util.HTTPUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 15/3/16.
 */
public class GetRewardListener extends HTTPListener {

    private CommonPostRequest commonPostRequest;

    public void setCommonPostRequest(CommonPostRequest commonPostRequest){
        this.commonPostRequest = commonPostRequest;
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
        return new JsonObjectRequest(Request.Method.POST, HTTPUrl.HOST + HTTPUrl.PARAM_GET_REWARD, jsonObject,
                getResponseListener(), getErrorListener());
    }

    @Override
    protected void onGetResponse(JSONObject response) throws JSONException {
        MainGetRewardModel mainGetRewardModel = new MainGetRewardModel();
        mainGetRewardModel.fromJSON(response);
        if(onHTTPListener != null)
            onHTTPListener.onGetResponse(mainGetRewardModel);
    }

    @Override
    protected void onGetErrorResponse(VolleyError error) {
        if(onHTTPListener != null)
            onHTTPListener.onGetErrorResponse(error);
    }
}
