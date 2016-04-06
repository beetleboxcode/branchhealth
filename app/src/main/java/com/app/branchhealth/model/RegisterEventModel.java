package com.app.branchhealth.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 23/3/16.
 */
public class RegisterEventModel extends CommonPostRequest {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {

    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(KEY_ID, id);
        return jsonObject;
    }
}
