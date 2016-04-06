package com.app.branchhealth.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 16/9/15.
 */
public class ContactUsModel extends BranchHealthModel {
    private String addressTitle;
    private String addressContent;
    private String phone;
    private String fax;
    private String website;

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressContent(String addressContent) {
        this.addressContent = addressContent;
    }

    public String getAddressContent() {
        return addressContent;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        if(jsonObject != null){
            if(jsonObject.has(KEY_CONTACT_US)){
                JSONObject contactUsObject = jsonObject.getJSONObject(KEY_CONTACT_US);
                if(contactUsObject.has(KEY_ADDRESS_TITLE)){
                    addressTitle = contactUsObject.getString(KEY_ADDRESS_TITLE);
                }
                if(contactUsObject.has(KEY_ADDRESS_CONTENT)){
                    addressContent = contactUsObject.getString(KEY_ADDRESS_CONTENT);
                }
                if(contactUsObject.has(KEY_PHONE)){
                    phone = contactUsObject.getString(KEY_PHONE);
                }
                if(contactUsObject.has(KEY_FAX)){
                    fax = contactUsObject.getString(KEY_FAX);
                }
                if(contactUsObject.has(KEY_WEBSITE)){
                    website = contactUsObject.getString(KEY_WEBSITE);
                }
            }
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }
}
