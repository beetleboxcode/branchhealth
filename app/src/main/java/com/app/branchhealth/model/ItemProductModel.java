package com.app.branchhealth.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by eReFeRHa on 23/3/16.
 */
public class ItemProductModel extends ItemNewsModel implements Serializable {

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        if(jsonObject != null){
            if(jsonObject.has(KEY_ID)){
                setId(jsonObject.getString(KEY_ID));
            }
            if(jsonObject.has(KEY_PRODUCT_ID)){
                setIdNews(jsonObject.getString(KEY_PRODUCT_ID));;
            }
            if(jsonObject.has(KEY_TYPE)){
                setType(jsonObject.getString(KEY_TYPE));;
            }
            if(jsonObject.has(KEY_FILE_PATH)){
                setUrl(jsonObject.getString(KEY_FILE_PATH));
            }
            if(jsonObject.has(KEY_UPDATED_DATE)){
                setUpdatedDate(jsonObject.getString(KEY_UPDATED_DATE));;
            }
        }
    }
}
