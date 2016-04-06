package com.app.branchhealth.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eReFeRHa on 2/3/16.
 */
public class RegisterModel extends BranchHealthModel {

    // for request
    private String firstName;
    private String lastName;
    private String position;
    private String email;
    private String password;
    private String phone1;
    private String phone2;
    private String homeAddress;
    private String apotekAddress;
    private String city;
    private String branch;
    private String apotekName;

    private String idPosition;
    private String idCity;
    private String idBranch;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getApotekAddress() {
        return apotekAddress;
    }

    public void setApotekAddress(String apotekAddress) {
        this.apotekAddress = apotekAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getApotekName() {
        return apotekName;
    }

    public void setApotekName(String apotekName) {
        this.apotekName = apotekName;
    }

    public String getIdPosition() {
        return idPosition;
    }

    public void setIdPosition(String idPosition) {
        this.idPosition = idPosition;
    }

    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(String idCity) {
        this.idCity = idCity;
    }

    public String getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(String idBranch) {
        this.idBranch = idBranch;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {

    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_FIRSTNAME, firstName);
        jsonObject.put(KEY_LASTNAME, lastName);
        jsonObject.put(KEY_POSITION, idPosition);
        jsonObject.put(KEY_EMAIL, email);
        jsonObject.put(KEY_PASSWORD, password);
        jsonObject.put(KEY_PHONE1, phone1);
        jsonObject.put(KEY_PHONE2, phone2);
        jsonObject.put(KEY_HOME_ADDRESS, homeAddress);
        jsonObject.put(KEY_APOTEK_ADDRESS, apotekAddress);
        jsonObject.put(KEY_CITY, idCity);
        jsonObject.put(KEY_BRANCH, idBranch);
        jsonObject.put(KEY_APOTEK_NAME, apotekName);
        return jsonObject;
    }
}
