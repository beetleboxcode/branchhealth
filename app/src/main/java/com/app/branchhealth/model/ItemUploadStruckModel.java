package com.app.branchhealth.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 2/3/16.
 */
public class ItemUploadStruckModel extends BranchHealthModel {

    private String id; //sku id
    private int qty;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {

    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_ID, id);
        jsonObject.put(KEY_QTY, qty);
        return jsonObject;
    }
}
