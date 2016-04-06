package com.app.branchhealth.fragment;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.activity.BaseFragmentActivity;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.listener.UploadStruckListener;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.model.UploadStruckModel;

/**
 * Created by eReFeRHa on 1/3/16.
 */
public class UploadStructConfirmFragment extends Fragment implements View.OnClickListener, HTTPListener.OnHTTPListener {

    // UI
    protected AlertDialog.Builder alertDialog;

    //DATA
    private String description;
    private Bitmap imageBitmap;
    private UploadStruckModel uploadStruckModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_upload_struct_confirm, container, false);

        ImageView imgTakeAPhoto = (ImageView) rootView.findViewById(R.id.imgTakeAPhoto);
        imgTakeAPhoto.setImageBitmap(imageBitmap);

        TextView txtUploadStructConfirm = (TextView) rootView.findViewById(R.id.txtUploadStructConfirm);
        txtUploadStructConfirm.setText(description);

        rootView.findViewById(R.id.btnSend).setOnClickListener(this);

        alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Info")
                .setMessage("")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(getActivity() != null && getActivity() instanceof BaseFragmentActivity){
                            BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
                            UploadStructFormFragment.REQUEST_CODE_UPLOAD_SUCCESS = 1;
                            activity.onBackPressed();
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
            case R.id.btnSend:
                if(uploadStruckModel != null){
                    UploadStruckListener uploadStruckListener = new UploadStruckListener();
                    uploadStruckListener.setUploadStruckModel(uploadStruckModel);
                    uploadStruckListener.setOnHTTPListener(this);
                    uploadStruckListener.callRequest(getContext());
                }
                break;
        }
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public void setUploadStruckModel(UploadStruckModel uploadStruckModel) {
        this.uploadStruckModel = uploadStruckModel;
    }
}
