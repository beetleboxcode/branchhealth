package com.app.branchhealth.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.listener.ContactUsListener;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.ContactUsModel;

/**
 * Created by eReFeRHa on 1/3/16.
 */
public class ContactUsFragment extends Fragment implements HTTPListener.OnHTTPListener {

    // UI
    private TextView contactUsPhone, contactUsFax, contactUsWebsite, contactUsAddressTitle, contactUsAddressContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_contactus, container, false);
        configureView(rootView);

        ContactUsListener contactUsListener = new ContactUsListener();
        contactUsListener.setOnHTTPListener(this);
        contactUsListener.callRequest(getContext());

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void configureView(View view){
        LinearLayout llCallOffice = (LinearLayout) view.findViewById(R.id.linearCallOffice);
        contactUsPhone = (TextView) view.findViewById(R.id.contactUsPhone);
        llCallOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callActivity(contactUsPhone.getText().toString().replaceAll("[^0-9]", ""));

            }
        });
        LinearLayout llCallFaxOffice = (LinearLayout) view.findViewById(R.id.linearCallFaxOffice);
        contactUsFax = (TextView) view.findViewById(R.id.contactUsFax);
        llCallFaxOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callActivity(contactUsFax.getText().toString().replaceAll("[^0-9]", ""));

            }
        });
        LinearLayout llWeb = (LinearLayout) view.findViewById(R.id.linearWebOffice);
        contactUsWebsite = (TextView) view.findViewById(R.id.contactUsWebsite);
        llWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPermataWeb(contactUsWebsite.getText().toString());
            }
        });

        contactUsAddressTitle = (TextView) view.findViewById(R.id.contactUsAddressTitle);
        contactUsAddressContent = (TextView) view.findViewById(R.id.contactUsAddressContent);
    }

    private void callActivity(String noTelp){
        String url = "tel:" + noTelp;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
        startActivity(intent);
    }

    private void showPermataWeb(String url){
        final String urlString = "https://" + url;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        startActivity(browserIntent);
    }

    @Override
    public void onGetResponse(BranchHealthModel branchHealthModel) {
        if(branchHealthModel != null && branchHealthModel instanceof ContactUsModel){
            ContactUsModel contactUsModel = (ContactUsModel) branchHealthModel;
            contactUsPhone.setText(contactUsModel.getPhone());
            contactUsFax.setText(contactUsModel.getFax());
            contactUsWebsite.setText(contactUsModel.getWebsite());
            contactUsAddressTitle.setText(contactUsModel.getAddressTitle());
            contactUsAddressContent.setText(contactUsModel.getAddressContent());
        }
    }

    @Override
    public void onGetErrorResponse(VolleyError error) {

    }
}
