package com.app.branchhealth.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by eReFeRHa on 8/9/15.
 */
public class FAQModel extends BranchHealthModel {
    private String question;
    private ArrayList<String> answers;

    public void setQuestion(String question) {
        this.question = question;
    }
    public String getQuestion() {
        return question;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
    public ArrayList<String> getAnswers() {
        return answers;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        answers = new ArrayList<>();
        if(jsonObject != null){
            if(jsonObject.has(KEY_QUESTION)){
                question = jsonObject.getString(KEY_QUESTION);
            }
            if(jsonObject.has(KEY_ANSWERS)){
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_ANSWERS);
                for(int i = 0; i < jsonArray.length(); i++){
                    answers.add(jsonArray.getString(i));
                }
            }
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }
}
