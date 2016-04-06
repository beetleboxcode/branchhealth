package com.app.branchhealth.listener;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.branchhealth.model.LoginModel;
import com.app.branchhealth.model.TermAndConditionModel;
import com.app.branchhealth.util.HTTPUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 15/3/16.
 */
public class TermAndConditionListener extends HTTPListener {

    @Override
    public Request<?> getStringRequest() {
        JSONObject jsonObject = new JSONObject();
        return new JsonObjectRequest(Request.Method.GET, HTTPUrl.HOST + HTTPUrl.PARAM_TERM_AND_CONDITION, jsonObject,
                getResponseListener(), getErrorListener());
    }

    @Override
    protected void onGetResponse(JSONObject response) throws JSONException{
        TermAndConditionModel termAndConditionModel = new TermAndConditionModel();
        termAndConditionModel.fromJSON(response);
        if(onHTTPListener != null)
            onHTTPListener.onGetResponse(termAndConditionModel);
    }

    @Override
    protected void onGetErrorResponse(VolleyError error) {
        if(onHTTPListener != null)
            onHTTPListener.onGetErrorResponse(error);
    }
}
