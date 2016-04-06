package com.app.branchhealth.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 26/3/16.
 */
public class PointModel extends BranchHealthModel {

    private String id;
    private String idUser;
    private String point;
    private String updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
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
            if(jsonObject.has(KEY_POINT)) {
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_POINT);
                if(jsonArray.length() > 0) {
                    JSONObject pointObject = jsonArray.getJSONObject(0);
                    if(pointObject != null) {
                        if (pointObject.has(KEY_ID)) {
                            id = pointObject.getString(KEY_ID);
                        }
                        if (pointObject.has(KEY_ID_USER)) {
                            idUser = pointObject.getString(KEY_ID_USER);
                        }
                        if (pointObject.has(KEY_POINT)) {
                            point = pointObject.getString(KEY_POINT);
                        }
                        if (pointObject.has(KEY_UPDATED_DATE)) {
                            updatedDate = pointObject.getString(KEY_UPDATED_DATE);
                        }
                    }
                }
            }
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }
}
