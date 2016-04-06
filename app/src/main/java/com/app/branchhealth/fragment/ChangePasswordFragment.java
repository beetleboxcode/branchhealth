package com.app.branchhealth.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.listener.ChangePasswordListener;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.ChangePasswordModel;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.util.EncryptionUtils;

/**
 * Created by eReFeRHa on 1/3/16.
 */
public class ChangePasswordFragment extends Fragment implements View.OnClickListener, HTTPListener.OnHTTPListener {

    //UI
    private EditText edtOldPassword, edtNewPassword, edtConfirmPassword;
    protected AlertDialog.Builder alertDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_change_password, container, false);
        edtOldPassword = (EditText) rootView.findViewById(R.id.edtOldPassword);
        edtNewPassword = (EditText) rootView.findViewById(R.id.edtNewPassword);
        edtConfirmPassword = (EditText) rootView.findViewById(R.id.edtConfirmPassword);

        rootView.findViewById(R.id.btnChange).setOnClickListener(this);

        alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Info")
                .setMessage("")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(getActivity() != null){
                            getActivity().onBackPressed();
                        }
                    }
                });
        alertDialog.setCancelable(false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnChange:
                ChangePasswordModel changePasswordModel = validateData();
                if(changePasswordModel != null){
                    ChangePasswordListener changePasswordListener = new ChangePasswordListener();
                    changePasswordListener.setChangePasswordModel(changePasswordModel);
                    changePasswordListener.setOnHTTPListener(this);
                    changePasswordListener.callRequest(getContext());
                }
                break;
        }
    }

    private ChangePasswordModel validateData(){
        String oldPassword = edtOldPassword.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        ChangePasswordModel changePasswordModel = null;

        if(oldPassword.length() == 0 || newPassword.length() == 0 || confirmPassword.length() == 0){
            Toast.makeText(getContext(), "Silahkan isi semua field yang ada", Toast.LENGTH_SHORT).show();
        }
        else if(!newPassword.equals(confirmPassword)){
            Toast.makeText(getContext(), "Password tidak sama", Toast.LENGTH_SHORT).show();
        }
        else{
            changePasswordModel = new ChangePasswordModel();
            changePasswordModel.setUsername(AppUserPreference.getStringData(getContext(), AppUserPreference.KEY_USERNAME));
            String encOld = EncryptionUtils.encrypt(oldPassword, getContext());
            //encOld = (encOld.length()>24)?encOld.substring(0, 24):encOld;
            encOld = encOld.replace("\n", "");
            String encNew = EncryptionUtils.encrypt(newPassword, getContext());
            //encNew = (encNew.length()>24)?encNew.substring(0, 24):encNew;
            encNew = encNew.replace("\n", "");
            changePasswordModel.setOldPassword(encOld);
            changePasswordModel.setNewPassword(encNew);
        }

        return changePasswordModel;
    }

    @Override
    public void onGetResponse(BranchHealthModel branchHealthModel) {
        String message = "";
        if(branchHealthModel != null && branchHealthModel instanceof CommonPostRequest){
            CommonPostRequest commonPostRequest = (CommonPostRequest) branchHealthModel;
            message = commonPostRequest.getMessage();

            String newPassword = edtNewPassword.getText().toString().trim();
            boolean isRememberMe = AppUserPreference.getBooleanData(getContext(), AppUserPreference.KEY_IS_REMEMBER_ME);
            if(isRememberMe){
                AppUserPreference.putData(getContext(), AppUserPreference.KEY_PASSWORD, newPassword);
            }
        }
        alertDialog.setMessage(message);
        alertDialog.show();
    }

    @Override
    public void onGetErrorResponse(VolleyError error) {

    }
}
