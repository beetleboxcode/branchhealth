package com.app.branchhealth.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.adapter.PointHistoryAdapter;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.listener.GetPointHistoryListener;
import com.app.branchhealth.listener.GetPointListener;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.model.HistoryPointModel;
import com.app.branchhealth.model.MainHistoryPointModel;
import com.app.branchhealth.model.PointModel;

import java.util.ArrayList;

/**
 * Created by eReFeRHa on 1/3/16.
 */
public class PointRewardHistoryFragment extends Fragment implements HTTPListener.OnHTTPListener, View.OnClickListener {

    // UI
    private TextView[] txtPoints = new TextView[5];

    // DATA
    private PointModel pointModel;
    public static boolean HISTORY_USE_CACHE = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_point_reward, container, false);
        txtPoints[0] = (TextView) rootView.findViewById(R.id.txtFirstPoint);
        txtPoints[1] = (TextView) rootView.findViewById(R.id.txtSecondPoint);
        txtPoints[2] = (TextView) rootView.findViewById(R.id.txtThirdPoint);
        txtPoints[3] = (TextView) rootView.findViewById(R.id.txtFourthPoint);
        txtPoints[4] = (TextView) rootView.findViewById(R.id.txtFifthPoint);

        for(TextView txt : txtPoints){
            txt.setText(R.string.txt_default_point);
        }

        rootView.findViewById(R.id.btnRefresh).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //HISTORY_USE_CACHE = true;
        callGetPointListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnRefresh:
                HISTORY_USE_CACHE = false;
                callGetPointListener();
                break;
        }
    }

    private void callGetPointListener(){
        GetPointListener getPointListener = new GetPointListener();
        CommonPostRequest commonPostRequest = new CommonPostRequest();
        commonPostRequest.setUsername(AppUserPreference.getStringData(getContext(), AppUserPreference.KEY_USERNAME));
        getPointListener.setCommonPostRequest(commonPostRequest);
        getPointListener.setOnHTTPListener(this);
        getPointListener.callRequest(getContext(), HISTORY_USE_CACHE);
    }

    @Override
    public void onGetResponse(BranchHealthModel branchHealthModel) {
        if(branchHealthModel != null){
            if(branchHealthModel instanceof PointModel){
                pointModel = (PointModel) branchHealthModel;
                configurePointModel();

                GetPointHistoryListener getPointHistoryListener = new GetPointHistoryListener();
                CommonPostRequest commonPostRequest = new CommonPostRequest();
                commonPostRequest.setUsername(AppUserPreference.getStringData(getContext(), AppUserPreference.KEY_USERNAME));
                getPointHistoryListener.setCommonPostRequest(commonPostRequest);
                getPointHistoryListener.setOnHTTPListener(this);
                getPointHistoryListener.callRequest(getContext(), HISTORY_USE_CACHE);
            }
            else if(branchHealthModel instanceof MainHistoryPointModel){
                MainHistoryPointModel mainHistoryPointModel = (MainHistoryPointModel) branchHealthModel;
                ArrayList<HistoryPointModel> historyPointModels = mainHistoryPointModel.getHistoryPointModels();
                configureList(historyPointModels);
            }

            HISTORY_USE_CACHE = true;
        }
    }

    @Override
    public void onGetErrorResponse(VolleyError error) {
        HISTORY_USE_CACHE = true;
    }

    private void configureList(ArrayList<HistoryPointModel> historyPointModels){
        ArrayList<Integer> headerPosition = new ArrayList<Integer>();
        for(int i = 0; i < historyPointModels.size(); i++){
            HistoryPointModel currAcc = historyPointModels.get(i);
            if(headerPosition.size() == 0){
                headerPosition.add(i);
            }
            else {
                HistoryPointModel prevAcc = historyPointModels.get(i - 1);
                if(!currAcc.getDate().equals(prevAcc.getDate())){
                    headerPosition.add(i);
                }
            }
        }

        PointHistoryAdapter pointHistoryAdapter = new PointHistoryAdapter(getContext(), historyPointModels, headerPosition);

        LinearLayout lstHistoryPoint = (LinearLayout) getView().findViewById(R.id.lstHistoryPoint);
        lstHistoryPoint.removeAllViews();

        for(int i = 0; i < historyPointModels.size(); i++){
            if(headerPosition.contains(i)){
                lstHistoryPoint.addView(pointHistoryAdapter.getHeaderView(i, null, null));
            }
            lstHistoryPoint.addView(pointHistoryAdapter.getView(i, null, null));
        }
    }

    private void configurePointModel() {
        if (pointModel != null) {
            String point = pointModel.getPoint();
            if(point != null){
                int length = point.length();
                if(length > 0){
                    for(TextView txt : txtPoints){
                        if(length > 0){
                            int start = (length - 1);
                            txt.setText(point.substring(start, start + 1));
                        }
                        else{
                            txt.setText(R.string.txt_default_point);
                        }
                        length -= 1;
                    }
                }
            }
        }
    }
}
