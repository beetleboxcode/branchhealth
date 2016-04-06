package com.app.branchhealth.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by eReFeRHa on 17/3/16.
 */
public class CityModel extends BranchHealthModel {

    private String id;
    private String name;
    private ArrayList<BranchModel> branchModels;
    private String updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<BranchModel> getBranchModels() {
        return branchModels;
    }

    public void setBranchModels(ArrayList<BranchModel> branchModels) {
        this.branchModels = branchModels;
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
            if(jsonObject.has(KEY_CITY)){
                name = jsonObject.getString(KEY_CITY);
            }
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }
}
