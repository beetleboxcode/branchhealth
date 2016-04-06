package com.app.branchhealth.listener;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.model.ContactUsModel;
import com.app.branchhealth.model.MainSKUModel;
import com.app.branchhealth.util.HTTPUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 15/3/16.
 */
public class GetSKUListener extends HTTPListener {

    @Override
    protected String getKeyCache() {
        return AppUserPreference.KEY_RESPONSE_GET_SKU;
    }

    @Override
    public Request<?> getStringRequest() {
        JSONObject jsonObject = new JSONObject();
        return new JsonObjectRequest(Request.Method.GET, HTTPUrl.HOST + HTTPUrl.PARAM_GET_SKU, jsonObject,
                getResponseListener(), getErrorListener());
    }

    @Override
    protected void onGetResponse(JSONObject response) throws JSONException{
        MainSKUModel mainSKUModel = new MainSKUModel();
        mainSKUModel.fromJSON(response);
        if(onHTTPListener != null)
            onHTTPListener.onGetResponse(mainSKUModel);
    }

    @Override
    protected void onGetErrorResponse(VolleyError error) {
        processCommonOnGetErrorResponse(error);
    }
}
