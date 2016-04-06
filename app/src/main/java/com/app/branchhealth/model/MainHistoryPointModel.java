package com.app.branchhealth.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by eReFeRHa on 23/3/16.
 */
public class MainHistoryPointModel extends BranchHealthModel {

    private ArrayList<HistoryPointModel> historyPointModels;

    public ArrayList<HistoryPointModel> getHistoryPointModels() {
        return historyPointModels;
    }

    public void setFaqModels(ArrayList<HistoryPointModel> historyPointModels) {
        this.historyPointModels = historyPointModels;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        historyPointModels = new ArrayList<>();
        if(jsonObject != null){
            if(jsonObject.has(KEY_POINT_HISTORY)){
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_POINT_HISTORY);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject faqObject = jsonArray.getJSONObject(i);
                    HistoryPointModel faqModel = new HistoryPointModel();
                    faqModel.fromJSON(faqObject);
                    historyPointModels.add(faqModel);
                }
            }
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }
}
