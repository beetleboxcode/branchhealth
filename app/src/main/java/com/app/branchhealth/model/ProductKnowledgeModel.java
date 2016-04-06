package com.app.branchhealth.model;

import com.app.branchhealth.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by eReFeRHa on 17/3/16.
 */
public class ProductKnowledgeModel extends BranchHealthModel implements Serializable {

    private int defaultImage = R.drawable.default_item;
    private String url = "";
    private String description;
    private String id;
    private String productName;
    private String updatedDate;
    private ArrayList<ItemNewsModel> itemNewModels;

    public int getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(int defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public ArrayList<ItemNewsModel> getItemNewModels() {
        return itemNewModels;
    }

    public void setItemNewModels(ArrayList<ItemNewsModel> itemNewModels) {
        this.itemNewModels = itemNewModels;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        itemNewModels = new ArrayList<>();
        if(jsonObject != null){
            if(jsonObject.has(KEY_ID)){
                id = jsonObject.getString(KEY_ID);
            }
            if(jsonObject.has(KEY_PRODUCT_NAME)){
                productName = jsonObject.getString(KEY_PRODUCT_NAME);
            }
            if(jsonObject.has(KEY_PRODUCT_DESCRIPTION)){
                description = jsonObject.getString((KEY_PRODUCT_DESCRIPTION));
            }
            if(jsonObject.has(KEY_UPDATED_DATE)){
                updatedDate = jsonObject.getString(KEY_UPDATED_DATE);
            }
            if(jsonObject.has(KEY_PRODUCT_ASSET)){
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_PRODUCT_ASSET);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject itemObject = jsonArray.getJSONObject(i);
                    ItemProductModel itemNewsModel = new ItemProductModel();
                    itemNewsModel.fromJSON(itemObject);
                    itemNewModels.add(itemNewsModel);

                    if(i == 0){
                        url = itemNewsModel.getUrl();
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
