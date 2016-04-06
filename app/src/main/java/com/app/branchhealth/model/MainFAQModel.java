package com.app.branchhealth.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by eReFeRHa on 23/3/16.
 */
public class MainFAQModel extends BranchHealthModel {

    private ArrayList<FAQModel> faqModels;

    public ArrayList<FAQModel> getFaqModels() {
        return faqModels;
    }

    public void setFaqModels(ArrayList<FAQModel> faqModels) {
        this.faqModels = faqModels;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        faqModels = new ArrayList<>();
        if(jsonObject != null){
            if(jsonObject.has(KEY_FAQ)){
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_FAQ);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject faqObject = jsonArray.getJSONObject(i);
                    FAQModel faqModel = new FAQModel();
                    faqModel.fromJSON(faqObject);
                    faqModels.add(faqModel);
                }
            }
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }
}
