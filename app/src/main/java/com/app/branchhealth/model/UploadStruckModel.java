package com.app.branchhealth.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by eReFeRHa on 2/3/16.
 */
public class UploadStruckModel extends BranchHealthModel {

    private String username;
    private long date;
    private String image;
    private ArrayList<ItemUploadStruckModel> itemUploadStruckModels = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<ItemUploadStruckModel> getItemUploadStruckModels() {
        return itemUploadStruckModels;
    }

    public void setItemUploadStruckModels(ArrayList<ItemUploadStruckModel> itemUploadStruckModels) {
        this.itemUploadStruckModels = itemUploadStruckModels;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {

    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_USERNAME, username);
        jsonObject.put(KEY_DATE, date);
        jsonObject.put(KEY_IMAGE, image);

        if(itemUploadStruckModels != null){
            JSONArray jsonArray = new JSONArray();
            for (ItemUploadStruckModel itemUploadStruckModel : itemUploadStruckModels){
                jsonArray.put(itemUploadStruckModel.toJSON());
            }
            jsonObject.put(KEY_ITEMS, jsonArray);
        }
        return jsonObject;
    }
}
