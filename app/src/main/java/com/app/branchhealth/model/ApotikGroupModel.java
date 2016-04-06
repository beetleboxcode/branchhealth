package com.app.branchhealth.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 17/3/16.
 */
public class ApotikGroupModel extends BranchHealthModel {

    private String id;
    private String name;
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
            if(jsonObject.has(KEY_APOTEK_NAME2)){
                name = jsonObject.getString(KEY_APOTEK_NAME2);
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
