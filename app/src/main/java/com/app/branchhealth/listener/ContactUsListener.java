package com.app.branchhealth.listener;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.model.ContactUsModel;
import com.app.branchhealth.util.HTTPUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 15/3/16.
 */
public class ContactUsListener extends HTTPListener {

    @Override
    protected String getKeyCache() {
        return AppUserPreference.KEY_RESPONSE_CONTACT_US;
    }

    @Override
    public Request<?> getStringRequest() {
        JSONObject jsonObject = new JSONObject();
        return new JsonObjectRequest(Request.Method.GET, HTTPUrl.HOST + HTTPUrl.PARAM_CONTACT_US, jsonObject,
                getResponseListener(), getErrorListener());
    }

    @Override
    protected void onGetResponse(JSONObject response) throws JSONException{
        ContactUsModel contactUsModel = new ContactUsModel();
        contactUsModel.fromJSON(response);
        if(onHTTPListener != null)
            onHTTPListener.onGetResponse(contactUsModel);
    }

    @Override
    protected void onGetErrorResponse(VolleyError error) {
        processCommonOnGetErrorResponse(error);
    }
}
