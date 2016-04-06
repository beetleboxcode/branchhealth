package com.app.branchhealth.listener;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.services.SingletonHTTPRequest;
import com.app.branchhealth.view.MyProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 3/2/16.
 */
public abstract class HTTPListener {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final int STATUS_SUCCESSS = 1;

    protected Request<?> stringRequest;
    protected MyProgressDialog progressDialog;
    protected AlertDialog.Builder alertDialog;
    protected OnHTTPListener onHTTPListener;
    protected Context context;
    protected boolean isShowDialog = true;

    public interface OnHTTPListener {
        void onGetResponse(BranchHealthModel branchHealthModel);
        void onGetErrorResponse(VolleyError error);
    }

    public void setOnHTTPListener(OnHTTPListener onHTTPListener){
        this.onHTTPListener = onHTTPListener;
    }

    public void setIsShowDialog(boolean isShowDialog) {
        this.isShowDialog = isShowDialog;
    }

    public void callRequest(Context context) {
        callRequest(context, true);
    }

    public void callRequest(Context context, boolean useCache) {
        this.context = context;
        boolean isGetResponseFromCache = false;
        showProgressDialog(context);
        createErrorMessage(context);

        if(getKeyCache() != null && getKeyCache().trim().length() > 0 && useCache){
            String responseCache = AppUserPreference.getStringData(context, getKeyCache());
            if(responseCache != null && responseCache.trim().length() > 0){
                try {
                    JSONObject jsonObject = new JSONObject(responseCache);
                    // Do something with the response
                    hideProgressDialog();
                    onGetResponse(jsonObject);
                    isGetResponseFromCache = true;
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }

        if(!isGetResponseFromCache) {
            SingletonHTTPRequest.getInstance(context).addToRequestQueue(getStringRequest());
        }
    }

    protected JSONObject getCacheResponse(){
        JSONObject jsonObject = null;
        if(getKeyCache() != null && getKeyCache().trim().length() > 0){
            String responseCache = AppUserPreference.getStringData(context, getKeyCache());
            if(responseCache != null && responseCache.trim().length() > 0){
                try {
                    jsonObject = new JSONObject(responseCache);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }

        return jsonObject;
    }

    protected Response.Listener<JSONObject> getResponseListener() {
        return new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // Do something with the response
                hideProgressDialog();

                boolean isSuccess = false;
                String message = "Silahkan periksa koneksi internet anda";

                try {
                    if (response != null) {
                        if(response.has(KEY_MESSAGE) && !response.getString(KEY_MESSAGE).equals("")){
                            message = response.getString(KEY_MESSAGE);
                        }
                        if(response.has(KEY_STATUS) && response.getInt(KEY_STATUS) == STATUS_SUCCESSS) {
                            isSuccess = true;
                            if(getKeyCache() != null && getKeyCache().trim().length() > 0){
                                AppUserPreference.putData(context, getKeyCache(), response.toString());
                            }
                            onGetResponse(response);
                        }
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                finally {
                    if(!isSuccess) {
                        VolleyError error = new VolleyError(message);

                        putTempToCurrentRequest();
                        //show dialog
                        alertDialog.setMessage(error.getMessage());
                        if(isShowDialog)
                            alertDialog.show();

                        onGetErrorResponse(error);
                    }
                }
            }
        };
    }

    protected Response.ErrorListener getErrorListener(){
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                hideProgressDialog();
                String message = "Silahkan periksa koneksi internet anda atau server sedang mengalami gangguan";

                putTempToCurrentRequest();
                //show dialog
                alertDialog.setMessage(message);
                if(isShowDialog)
                    alertDialog.show();

                onGetErrorResponse(error);
            }
        };
    }

    public abstract Request<?> getStringRequest();
    protected abstract void onGetResponse(JSONObject response) throws JSONException;
    protected abstract void onGetErrorResponse(VolleyError error);

    public void showProgressDialog(Context context) {
        try {
            if (progressDialog == null) {
                progressDialog = MyProgressDialog.show(context, "", "", true, true);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
            } else if(isShowDialog) {
                progressDialog.show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        if(progressDialog != null)
            progressDialog.dismiss();
    }

    public void createErrorMessage(Context context){
        if(alertDialog == null) {
            alertDialog = new AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage("")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("OK", null);
            alertDialog.setCancelable(false);
        }
    }

    protected String getKeyCache() {
        return null;
    }

    protected void processCommonOnGetErrorResponse(VolleyError error){
        JSONObject cacheObject = getCacheResponse();
        if(cacheObject != null){
            try {
                onGetResponse(cacheObject);
            }
            catch (JSONException e){
                e.printStackTrace();
                cacheObject = null;
            }
        }

        if(cacheObject == null && onHTTPListener != null) {
            onHTTPListener.onGetErrorResponse(error);
        }
    }

    private void putTempToCurrentRequest(){
        if(getKeyCache() != null && getKeyCache().trim().length() > 0){
            String keyCache = getKeyCache();
            String tempKeyCache = AppUserPreference.getTempKey(keyCache);

            String responseCache = AppUserPreference.getStringData(context, tempKeyCache);
            if(responseCache != null && responseCache.trim().length() > 0){
                AppUserPreference.putData(context, keyCache, responseCache);
            }
        }
    }
}
