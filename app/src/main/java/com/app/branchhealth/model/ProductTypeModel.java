package com.app.branchhealth.model;

/**
 * Created by eReFeRHa on 17/3/16.
 */
public class ProductTypeModel {

    private String id;
    private String name;

    public ProductTypeModel(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
