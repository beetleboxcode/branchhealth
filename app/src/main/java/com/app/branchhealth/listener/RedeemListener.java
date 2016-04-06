package com.app.branchhealth.listener;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.branchhealth.listener.RegisterEventListener;
import com.app.branchhealth.util.HTTPUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 24/3/16.
 */
public class RedeemListener extends RegisterEventListener {

    @Override
    public Request<?> getStringRequest() {
        JSONObject jsonObject = null;
        try {
            jsonObject = registerEventModel.toJSON();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return new JsonObjectRequest(Request.Method.POST, HTTPUrl.HOST + HTTPUrl.PARAM_REDEEM, jsonObject,
                getResponseListener(), getErrorListener());
    }
}
