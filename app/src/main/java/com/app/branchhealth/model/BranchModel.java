package com.app.branchhealth.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 17/3/16.
 */
public class BranchModel extends BranchHealthModel {

    private String id;
    private String idCity;
    private String name;
    private String branchName;
    private String updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(String idCity) {
        this.idCity = idCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        if(jsonObject != null){
            if(jsonObject.has(KEY_ID)){
                id = jsonObject.getString(KEY_ID);
            }
            if(jsonObject.has(KEY_ID_CITY)){
                idCity = jsonObject.getString(KEY_ID_CITY);
            }
            if(jsonObject.has(KEY_BRANCH)){
                name = jsonObject.getString(KEY_BRANCH);
            }
            if(jsonObject.has(KEY_BRANCH_NAME)){
                branchName = jsonObject.getString(KEY_BRANCH_NAME);
            }
            if(jsonObject.has(KEY_UPDATED_DATE)){
                updatedDate = jsonObject.getString(KEY_UPDATED_DATE);
            }
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }
}
