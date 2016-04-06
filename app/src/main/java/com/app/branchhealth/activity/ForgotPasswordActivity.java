package com.app.branchhealth.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.listener.ForgotPasswordListener;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.util.StringUtils;

/**
 * Created by eReFeRHa on 26/2/16.
 */
public class ForgotPasswordActivity extends BaseFragmentActivity implements View.OnClickListener, HTTPListener.OnHTTPListener {

    //UI
    private EditText edtEmail;
    protected AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.act_forgot_password;
    }

    @Override
    protected int getContainerBody() {
        return 0;
    }

    @Override
    protected void configureView() {
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        findViewById(R.id.btnSend).setOnClickListener(this);

        alertDialog = new AlertDialog.Builder(this)
                .setTitle("Info")
                .setMessage("")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        alertDialog.setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnSend:
                CommonPostRequest commonPostRequest = validateData();
                if(commonPostRequest != null){
                    ForgotPasswordListener forgotPasswordListener = new ForgotPasswordListener();
                    forgotPasswordListener.setCommonPostRequest(commonPostRequest);
                    forgotPasswordListener.setOnHTTPListener(this);
                    forgotPasswordListener.callRequest(this);
                }
                break;
        }
    }

    private CommonPostRequest validateData(){
        String email = edtEmail.getText().toString().trim();

        CommonPostRequest commonPostRequest = null;
        // cek data kosong
        if(email.length() == 0){
            Toast.makeText(this, "Silahkan masukkan email anda yang sudah terdaftar", Toast.LENGTH_SHORT).show();
        }
        else if(!StringUtils.checkEmail(email)){
            Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show();
        }
        else{
            commonPostRequest = new CommonPostRequest();
            commonPostRequest.setUsername(email);
        }

        return commonPostRequest;
    }

    @Override
    public void onGetResponse(BranchHealthModel branchHealthModel) {
        String message = "";
        if(branchHealthModel != null && branchHealthModel instanceof CommonPostRequest){
            CommonPostRequest commonPostRequest = (CommonPostRequest) branchHealthModel;
            message = commonPostRequest.getMessage();
        }
        alertDialog.setMessage(message);
        alertDialog.show();
    }

    @Override
    public void onGetErrorResponse(VolleyError error) {

    }
}
