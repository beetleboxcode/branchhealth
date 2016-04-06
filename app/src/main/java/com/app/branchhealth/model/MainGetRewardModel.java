package com.app.branchhealth.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by eReFeRHa on 23/3/16.
 */
public class MainGetRewardModel extends BranchHealthModel {

    private ArrayList<NewsModel> rewardsModels;

    public ArrayList<NewsModel> getRewardsModels() {
        return rewardsModels;
    }

    public void setRewardsModels(ArrayList<NewsModel> rewardsModels) {
        this.rewardsModels = rewardsModels;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        rewardsModels = new ArrayList<>();
        if(jsonObject != null){
            if(jsonObject.has(KEY_REWARD)){
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_REWARD);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject news = jsonArray.getJSONObject(i);
                    GetRewardModel getRewardModel = new GetRewardModel();
                    getRewardModel.fromJSON(news);
                    rewardsModels.add(getRewardModel);
                }
            }
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }
}
