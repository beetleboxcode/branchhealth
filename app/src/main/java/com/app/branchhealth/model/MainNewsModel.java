package com.app.branchhealth.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by eReFeRHa on 22/3/16.
 */
public class MainNewsModel extends BranchHealthModel {

    private ArrayList<NewsModel> newsModels;

    public ArrayList<NewsModel> getNewsModels() {
        return newsModels;
    }

    public void setNewsModels(ArrayList<NewsModel> newsModels) {
        this.newsModels = newsModels;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        newsModels = new ArrayList<>();
        if(jsonObject != null){
            if(jsonObject.has(KEY_NEWS)){
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_NEWS);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject news = jsonArray.getJSONObject(i);
                    NewsModel newsModel = new NewsModel();
                    newsModel.fromJSON(news);
                    newsModels.add(newsModel);
                }
            }
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }
}
