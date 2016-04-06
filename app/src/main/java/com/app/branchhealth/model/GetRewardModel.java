package com.app.branchhealth.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by eReFeRHA on 23/3/16.
 */
public class GetRewardModel extends NewsModel {

    private String point;

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        itemNewModels = new ArrayList<>();
        setType(1);
        if(jsonObject != null){
            if(jsonObject.has(KEY_ID)) {
                setId(jsonObject.getString(KEY_ID));
            }
            if(jsonObject.has(KEY_POINT)) {
                point = jsonObject.getString(KEY_POINT);
            }
            if(jsonObject.has(KEY_DESCRIPTION)){
                setContent(jsonObject.getString(KEY_DESCRIPTION));
            }
            if(jsonObject.has(KEY_REWARD)){
                setTitle(jsonObject.getString(KEY_REWARD));
            }
            if(jsonObject.has(KEY_UPDATED_DATE)){
                setUpdatedDate(jsonObject.getString(KEY_UPDATED_DATE));
            }
            if(jsonObject.has(KEY_PRODUCT_ASSET)){
                //JSONObject jsonArray = jsonObject.getJSONObject(KEY_PRODUCT_ASSET);
                //for(int i = 0; i < jsonArray.length(); i++){
                JSONObject assetObject = jsonObject.getJSONObject(KEY_PRODUCT_ASSET);
                ItemNewsModel itemRewardModel = new ItemRewardModel();
                itemRewardModel.fromJSON(assetObject);
                itemNewModels.add(itemRewardModel);

                //if(i == 0){
                setImage(itemRewardModel.getUrl());
                //}
                //}
            }
        }
    }
}
