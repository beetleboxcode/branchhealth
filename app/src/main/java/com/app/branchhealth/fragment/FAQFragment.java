package com.app.branchhealth.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.adapter.CustomExpandableListAdapter;
import com.app.branchhealth.listener.FAQListener;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.FAQModel;
import com.app.branchhealth.model.MainFAQModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by eReFeRHa on 1/3/16.
 */
public class FAQFragment extends Fragment implements HTTPListener.OnHTTPListener {

    //UI
    private CustomExpandableListAdapter listAdapter;
    private ExpandableListView expListView;

    //DATA
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_faq, container, false);
        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExpFaq);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listAdapter = new CustomExpandableListAdapter(getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        FAQListener faqListener = new FAQListener();
        faqListener.setOnHTTPListener(this);
        faqListener.callRequest(getContext());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void prepareListData(ArrayList<FAQModel> faqs) {

        //clear previous data
        listDataHeader.clear();
        listDataChild.clear();

        for(FAQModel faq : faqs){
            // Adding parent data
            String question = faq.getQuestion();
            listDataHeader.add(question);

            // Adding child data
            ArrayList<String> answers = faq.getAnswers();
            listDataChild.put(question, answers);
        }

        listAdapter.setExpandableData(listDataHeader, listDataChild);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetResponse(BranchHealthModel branchHealthModel) {
        if(branchHealthModel != null && branchHealthModel instanceof MainFAQModel){
            MainFAQModel mainFAQModel = (MainFAQModel) branchHealthModel;
            prepareListData(mainFAQModel.getFaqModels());
        }
    }

    @Override
    public void onGetErrorResponse(VolleyError error) {

    }
}
