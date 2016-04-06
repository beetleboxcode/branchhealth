package com.app.branchhealth.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by eReFeRHa on 2/3/16.
 */
public class NewsModel extends BranchHealthModel {

    private String id;
    private String image;
    private String title;
    private String shortDescription;
    private String content;
    private int type;
    private String idCity;
    private String idBranch;
    private String apotekGroup;
    private String updatedDate;
    protected ArrayList<ItemNewsModel> itemNewModels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<ItemNewsModel> getItemNewModels() {
        return itemNewModels;
    }

    public void setItemNewModels(ArrayList<ItemNewsModel> itemNewModels) {
        this.itemNewModels = itemNewModels;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(String idCity) {
        this.idCity = idCity;
    }

    public String getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(String idBranch) {
        this.idBranch = idBranch;
    }

    public String getApotekGroup() {
        return apotekGroup;
    }

    public void setApotekGroup(String apotekGroup) {
        this.apotekGroup = apotekGroup;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        itemNewModels = new ArrayList<>();
        if(jsonObject != null){
            if(jsonObject.has(KEY_ID)){
                id = jsonObject.getString(KEY_ID);
            }
            if(jsonObject.has(KEY_TITLE)){
                title = jsonObject.getString(KEY_TITLE);
            }
            if(jsonObject.has(KEY_SHORT_DESCRIPTION)){
                shortDescription = jsonObject.getString(KEY_SHORT_DESCRIPTION);
            }
            if(jsonObject.has(KEY_CONTENT)){
                content = jsonObject.getString(KEY_CONTENT);
            }
            if(jsonObject.has(KEY_TYPE)){
                String newsType = jsonObject.getString(KEY_TYPE);
                type = ("event".equalsIgnoreCase(newsType))?1:0;
            }
            if(jsonObject.has(KEY_CITY)){
                idCity = jsonObject.getString(KEY_CITY);
            }
            if(jsonObject.has(KEY_BRANCH)){
                idBranch = jsonObject.getString(KEY_BRANCH);
            }
            if(jsonObject.has(KEY_APOTEK_GROUP)){
                apotekGroup = jsonObject.getString(KEY_APOTEK_GROUP);
            }
            if(jsonObject.has(KEY_UPDATED_DATE)){
                updatedDate = jsonObject.getString(KEY_UPDATED_DATE);
            }
            if(jsonObject.has(KEY_ASSETS)){
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_ASSETS);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    ItemNewsModel itemNewsModel = new ItemNewsModel();
                    itemNewsModel.fromJSON(object);
                    itemNewModels.add(itemNewsModel);

                    if(i == 0){
                        image = itemNewsModel.getUrl();
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
