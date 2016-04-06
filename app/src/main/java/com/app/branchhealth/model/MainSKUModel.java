package com.app.branchhealth.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by eReFeRHa on 23/3/16.
 */
public class MainSKUModel extends BranchHealthModel {

    private ArrayList<ProductKnowledgeModel> productKnowledgeModels;

    public ArrayList<ProductKnowledgeModel> getProductKnowledgeModels() {
        return productKnowledgeModels;
    }

    public void setProductKnowledgeModels(ArrayList<ProductKnowledgeModel> productKnowledgeModels) {
        this.productKnowledgeModels = productKnowledgeModels;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        productKnowledgeModels = new ArrayList<>();
        if(jsonObject != null){
            if(jsonObject.has(KEY_SKU)){
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_SKU);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    ProductKnowledgeModel productKnowledgeModel = new ProductKnowledgeModel();
                    productKnowledgeModel.fromJSON(productObject);
                    productKnowledgeModels.add(productKnowledgeModel);
                }
            }
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }
}
