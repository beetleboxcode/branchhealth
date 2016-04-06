package com.app.branchhealth.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 23/3/16.
 */
public class ProfileModel extends RegisterModel {

    private String id;
    private String isActive;
    private String updatedDate;
    private String profilePict;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getProfilePict() {
        return profilePict;
    }

    public void setProfilePict(String profilePict) {
        this.profilePict = profilePict;
    }

    public String getProfileJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONObject profileObj = new JSONObject();
        profileObj.put(KEY_ID, id);
        profileObj.put(KEY_EMAIL, getEmail());
        profileObj.put(KEY_FIRST_NAME2, getFirstName());
        profileObj.put(KEY_LAST_NAME2, getLastName());
        profileObj.put(KEY_HOME_ADDRESS2, getHomeAddress());
        profileObj.put(KEY_PHARMACY_ADDRESS, getApotekAddress());
        profileObj.put(KEY_PHONE1, getPhone1());
        profileObj.put(KEY_PHONE2, getPhone2());
        profileObj.put(KEY_APOTEK_GROUP, getApotekName());
        profileObj.put(KEY_BRANCH, getIdBranch());
        profileObj.put(KEY_CITY, getIdCity());
        profileObj.put(KEY_ROLE, getIdPosition());
        profileObj.put(KEY_IS_ACTIVE, isActive);
        profileObj.put(KEY_UPDATED_DATE, updatedDate);
        profileObj.put(KEY_PROFILE_PICT2, profilePict);

        jsonObject.put(KEY_PROFILE, profileObj);
        return jsonObject.toString();
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        if(jsonObject != null) {
            if (jsonObject.has(KEY_PROFILE)) {
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_PROFILE);
                if (jsonArray != null && jsonArray.length() > 0) {
                    JSONObject profileObject = jsonArray.getJSONObject(0);
                    if (profileObject.has(KEY_ID)) {
                        id = profileObject.getString(KEY_ID);
                    }
                    if (profileObject.has(KEY_EMAIL)) {
                        setEmail(profileObject.getString(KEY_EMAIL));
                    }
                    if (profileObject.has(KEY_FIRST_NAME2)) {
                        setFirstName(profileObject.getString(KEY_FIRST_NAME2));
                    }
                    if (profileObject.has(KEY_LAST_NAME2)) {
                        setLastName(profileObject.getString(KEY_LAST_NAME2));
                    }
                    if (profileObject.has(KEY_HOME_ADDRESS2)) {
                        setHomeAddress(profileObject.getString(KEY_HOME_ADDRESS2));
                    }
                    if (profileObject.has(KEY_PHARMACY_ADDRESS)) {
                        setApotekAddress(profileObject.getString(KEY_PHARMACY_ADDRESS));
                    }
                    if (profileObject.has(KEY_PHONE1)) {
                        setPhone1(profileObject.getString(KEY_PHONE1));
                    }
                    if (profileObject.has(KEY_PHONE2)) {
                        setPhone2(profileObject.getString(KEY_PHONE2));
                    }
                    if (profileObject.has(KEY_APOTEK_GROUP)) {
                        setApotekName(profileObject.getString(KEY_APOTEK_GROUP));
                    }
                    if (profileObject.has(KEY_BRANCH)) {
                        setIdBranch(profileObject.getString(KEY_BRANCH));
                    }
                    if (profileObject.has(KEY_CITY)) {
                        setIdCity(profileObject.getString(KEY_CITY));
                    }
                    if (profileObject.has(KEY_ROLE)) {
                        setIdPosition(profileObject.getString(KEY_ROLE));
                    }
                    if (profileObject.has(KEY_IS_ACTIVE)) {
                        isActive = profileObject.getString(KEY_IS_ACTIVE);
                    }
                    if (profileObject.has(KEY_UPDATED_DATE)) {
                        updatedDate = profileObject.getString(KEY_UPDATED_DATE);
                    }
                    if(profileObject.has(KEY_PROFILE_PICT2)){
                        profilePict = profileObject.getString(KEY_PROFILE_PICT2);
                    }
                }
            }
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(KEY_PROFILE_PICT, profilePict);
        return jsonObject;
    }
}
