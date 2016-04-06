package com.app.branchhealth.model;

import com.app.branchhealth.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by eReFeRHa on 16/3/16.
 */
public class ItemNewsModel extends BranchHealthModel implements Serializable {

    private String id;
    private String idNews;
    private String type;
    private String url;
    private String updatedDate;
    private int defaultImage = R.drawable.default_item;

    public int getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(int defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdNews() {
        return idNews;
    }

    public void setIdNews(String idNews) {
        this.idNews = idNews;
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
            if(jsonObject.has(KEY_ID_NEWS)){
                idNews = jsonObject.getString(KEY_ID_NEWS);
            }
            if(jsonObject.has(KEY_TYPE)){
                type = jsonObject.getString(KEY_TYPE);
            }
            if(jsonObject.has(KEY_FILE_PATH)){
                url = jsonObject.getString(KEY_FILE_PATH);
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
