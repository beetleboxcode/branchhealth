package com.app.branchhealth.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 2/3/16.
 */
public interface BaseModel {

    void fromJSON(JSONObject jsonObject) throws JSONException;
    JSONObject toJSON() throws JSONException;
}
