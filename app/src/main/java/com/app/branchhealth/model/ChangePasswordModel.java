package com.app.branchhealth.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 2/3/16.
 */
public class ChangePasswordModel extends CommonPostRequest {

    private String oldPassword;
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        super.fromJSON(jsonObject);
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(KEY_OLD_PASSWORD, oldPassword);
        jsonObject.put(KEY_NEW_PASSWORD, newPassword);
        return jsonObject;
    }
}
