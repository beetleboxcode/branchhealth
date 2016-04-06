package com.app.branchhealth.model;

import com.app.branchhealth.util.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by eReFeRHa on 26/3/16.
 */
public class HistoryPointModel extends BranchHealthModel {

    private String id;
    private String idUser;
    private String type;
    private String point;
    private String updatedDate;
    private String message;
    private String date;

    private final String OLD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";//2016-03-23 10:04:19
    private final String NEW_DATE_FORMAT = "dd MMMM yyyy";
    private final DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        if (jsonObject != null) {
            if (jsonObject.has(KEY_ID)) {
                id = jsonObject.getString(KEY_ID);
            }
            if (jsonObject.has(KEY_ID_USER)) {
                idUser = jsonObject.getString(KEY_ID_USER);
            }
            if (jsonObject.has(KEY_TYPE)) {
                type = jsonObject.getString(KEY_TYPE);
            }
            if (jsonObject.has(KEY_POINT)) {
                point = jsonObject.getString(KEY_POINT);
            }
            if (jsonObject.has(KEY_UPDATED_DATE)) {
                updatedDate = jsonObject.getString(KEY_UPDATED_DATE);
                date = convertDate(updatedDate);
            }
            if (jsonObject.has(KEY_MESSAGE)) {
                message = jsonObject.getString(KEY_MESSAGE);
            }
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }

    private String convertDate(String stringOldDate){
        String newDate = DateUtils.formatDate(stringOldDate, OLD_DATE_FORMAT, NEW_DATE_FORMAT);
        return (newDate != null)?newDate:stringOldDate;
    }
}
