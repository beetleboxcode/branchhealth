package com.app.branchhealth.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 22/3/16.
 */
public class TermAndConditionModel extends BranchHealthModel {

    private String termAndCondition;

    public String getTermAndCondition() {
        return termAndCondition;
    }

    public void setTermAndCondition(String termAndCondition) {
        this.termAndCondition = termAndCondition;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException{
        if(jsonObject != null){
            if(jsonObject.has(KEY_TERM_AND_CONDITION)){
                termAndCondition = jsonObject.getString(KEY_TERM_AND_CONDITION);
            }
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }
}
