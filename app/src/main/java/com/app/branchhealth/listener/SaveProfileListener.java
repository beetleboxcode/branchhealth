package com.app.branchhealth.listener;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.model.ProfileModel;
import com.app.branchhealth.model.RegisterModel;
import com.app.branchhealth.util.HTTPUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 15/3/16.
 */
public class SaveProfileListener extends HTTPListener {

    private RegisterModel registerModel;
    private String profileStringObject;

    public void setRegisterModel(RegisterModel registerModel){
        this.registerModel = registerModel;
    }

    @Override
    public Request<?> getStringRequest() {
        JSONObject jsonObject = null;
        try {
            jsonObject = registerModel.toJSON();
            if(registerModel instanceof ProfileModel) {
                profileStringObject = ((ProfileModel)registerModel).getProfileJSON();
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return new JsonObjectRequest(Request.Method.POST, HTTPUrl.HOST + HTTPUrl.PARAM_SAVE_PROFILE, jsonObject,
                getResponseListener(), getErrorListener());
    }

    @Override
    protected void onGetResponse(JSONObject response) throws JSONException{
        CommonPostRequest commonPostRequest = new CommonPostRequest();
        commonPostRequest.fromJSON(response);

        AppUserPreference.putData(context, AppUserPreference.KEY_RESPONSE_GET_PROFILE, profileStringObject);

        if(onHTTPListener != null)
            onHTTPListener.onGetResponse(commonPostRequest);
    }

    @Override
    protected void onGetErrorResponse(VolleyError error) {
        if(onHTTPListener != null)
            onHTTPListener.onGetErrorResponse(error);
    }
}
