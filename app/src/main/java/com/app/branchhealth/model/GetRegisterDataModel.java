package com.app.branchhealth.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eReFeRHa on 22/3/16.
 */
public class GetRegisterDataModel extends BranchHealthModel {

    private ArrayList<CityModel> cityModels;
    private ArrayList<ApotikGroupModel> apotikGroupModels;
    private ArrayList<PositionModel> positionModels;

    public ArrayList<CityModel> getCityModels() {
        return cityModels;
    }

    public void setCityModels(ArrayList<CityModel> cityModels) {
        this.cityModels = cityModels;
    }

    public ArrayList<ApotikGroupModel> getApotikGroupModels() {
        return apotikGroupModels;
    }

    public void setApotikGroupModels(ArrayList<ApotikGroupModel> apotikGroupModels) {
        this.apotikGroupModels = apotikGroupModels;
    }

    public ArrayList<PositionModel> getPositionModels() {
        return positionModels;
    }

    public void setPositionModels(ArrayList<PositionModel> positionModels) {
        this.positionModels = positionModels;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        cityModels = new ArrayList<>();
        apotikGroupModels = new ArrayList<>();
        positionModels = new ArrayList<>();
        if(jsonObject != null){
            HashMap<String, ArrayList<BranchModel>> branchMap = new HashMap<>();
            if(jsonObject.has(KEY_APOTEK_GROUP)){
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_APOTEK_GROUP);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject apotekGroupObject = jsonArray.getJSONObject(i);
                    ApotikGroupModel apotikGroupModel = new ApotikGroupModel();
                    apotikGroupModel.fromJSON(apotekGroupObject);
                    apotikGroupModels.add(apotikGroupModel);
                }
            }
            if(jsonObject.has(KEY_BRANCH)){
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_BRANCH);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject branchObject = jsonArray.getJSONObject(i);
                    BranchModel branchModel = new BranchModel();
                    branchModel.fromJSON(branchObject);

                    String idCity = branchModel.getIdCity();
                    if(branchMap.containsKey(idCity)){
                        branchMap.get(idCity).add(branchModel);
                    }
                    else{
                        ArrayList<BranchModel> branchModels = new ArrayList<>();
                        branchModels.add(branchModel);
                        branchMap.put(idCity, branchModels);
                    }
                }
            }
            if(jsonObject.has(KEY_CITY)){
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_CITY);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject cityObject = jsonArray.getJSONObject(i);
                    CityModel cityModel = new CityModel();
                    cityModel.fromJSON(cityObject);
                    cityModel.setBranchModels(branchMap.get(cityModel.getId()));
                    cityModels.add(cityModel);
                }
            }
            if(jsonObject.has(KEY_POSITION)){
                JSONArray jsonArray = jsonObject.getJSONArray(KEY_POSITION);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject positionObject = jsonArray.getJSONObject(i);
                    PositionModel positionModel = new PositionModel();
                    positionModel.fromJSON(positionObject);
                    positionModels.add(positionModel);
                }
            }
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return null;
    }
}
