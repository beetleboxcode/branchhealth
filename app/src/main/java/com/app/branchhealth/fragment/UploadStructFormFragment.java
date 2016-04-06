package com.app.branchhealth.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.activity.BaseFragmentActivity;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.listener.GetSKUListener;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.model.ItemUploadStruckModel;
import com.app.branchhealth.model.MainSKUModel;
import com.app.branchhealth.model.ProductKnowledgeModel;
import com.app.branchhealth.model.UploadStruckModel;
import com.app.branchhealth.util.ImageUtils;
import com.app.branchhealth.view.DatePickerFragment;
import com.app.branchhealth.view.ItemProductStructView;
import com.app.branchhealth.view.TimePickerFragment;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by eReFeRHa on 1/3/16.
 */
public class UploadStructFormFragment extends Fragment implements View.OnClickListener, HTTPListener.OnHTTPListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    //UI
    private EditText edtStruckNumber;
    private LinearLayout contentProdukType;
    private TextView txtDate, txtTime;
    private ImageView imgTakeAPhoto;
    private Bitmap imageBitmap;
    protected AlertDialog.Builder alertDialog;

    //DATA
    public static int REQUEST_CODE_UPLOAD_SUCCESS = 0;
    private ArrayList<ProductKnowledgeModel> productKnowledgeModels = new ArrayList<>();
    private ArrayList<ItemProductStructView> itemProductStructViews = new ArrayList<>();
    private PopupMenu productTypePopupMenu;
    private ItemProductStructView selectedItemProductStructView;
    private DialogFragment dateFragment = new DatePickerFragment();
    private DialogFragment timeFragment = new TimePickerFragment();
    private String dateString, timeString;
    public static final int REQ_CODE_PICK_FROM_CAMERA = 1;
    private String mCurrentPhotoPath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DatePickerFragment)dateFragment).setOnDateSetListener(this);
        ((TimePickerFragment)timeFragment).setOnTimeSetListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_upload_struct_form, container, false);

        configureView(rootView);

        if(REQUEST_CODE_UPLOAD_SUCCESS == 1) {
            contentProdukType.removeAllViews();
            itemProductStructViews.clear();
            edtStruckNumber.setText("");
            imageBitmap = null;
            imgTakeAPhoto.setImageBitmap(imageBitmap);
            imgTakeAPhoto.destroyDrawingCache();
            imgTakeAPhoto.setVisibility(View.GONE);
            rootView.findViewById(R.id.btnTakeAPhoto).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.btnTakeAPhoto).setEnabled(true);
        }

        if(productKnowledgeModels == null || productKnowledgeModels.size() == 0){
            GetSKUListener getSKUListener = new GetSKUListener();
            CommonPostRequest commonPostRequest = new CommonPostRequest();
            //commonPostRequest.setUsername(AppUserPreference.getStringData(getContext(), AppUserPreference.KEY_USERNAME));
            //getSKUListener.setCommonPostRequest(commonPostRequest);
            getSKUListener.setOnHTTPListener(this);
            getSKUListener.callRequest(getContext());
        }
        else {
            configureItemProductViews();
        }

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
            case R.id.btnTakeAPhoto:
                // Here, thisActivity is the current activity
                PackageManager pm = getContext().getPackageManager();
                if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(getContext(), "Fitur kamera tidak ada", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnDate:
                dateFragment.show(getChildFragmentManager(), "datePicker");
                break;
            case R.id.btnTime:
                timeFragment.show(getChildFragmentManager(), "timePicker");
                break;
            case R.id.btnPlus:
                createItemProductStructView();
                break;
            case R.id.btnCheckForm:
                UploadStruckModel uploadStruckModel = new UploadStruckModel();
                if(imageBitmap == null){
                    Toast.makeText(getContext(), "Silahkan ambil foto struk terlebih dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(edtStruckNumber.getText().toString().trim().length() == 0){
                    Toast.makeText(getContext(), "Silahkan masukkan nomor struk", Toast.LENGTH_SHORT).show();
                    return;
                }

                dateString = txtDate.getText().toString();
                timeString = txtTime.getText().toString();
                uploadStruckModel.setUsername(AppUserPreference.getStringData(getContext(), AppUserPreference.KEY_USERNAME));
                try{
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
                    Date selectedDate = sdf.parse(dateString + " " + timeString);
                    uploadStruckModel.setDate(selectedDate.getTime());
                }catch(ParseException e){
                    uploadStruckModel.setDate(new Date().getTime());
                }
                uploadStruckModel.setImage(ImageUtils.getBase64FromBitmap(imageBitmap));

                String description = "";
                description = description + "Nomor Struk : " + edtStruckNumber.getText().toString().trim() + "\n";
                description = description + "Tanggal : " + dateString + ", " + timeString + "\n";
                description = description + "Tipe Produk :\n";

                String errorMessage = null;
                for (int i = 0; i < itemProductStructViews.size(); i++) {
                    ItemProductStructView itemProductStructView = itemProductStructViews.get(i);
                    ProductKnowledgeModel productKnowledgeModel = itemProductStructView.getProductKnowledgeModel();
                    String qty = itemProductStructView.getEdtQty().getText().toString().trim();
                    if(productKnowledgeModel == null){
                        errorMessage = "Silahkan pilih jenis produk terlebih dahulu";
                        break;
                    }
                    if(qty.length() == 0){
                        errorMessage = "Silahkan masukkan banyaknya jumlah produk yang terjual";
                        break;
                    }

                    itemProductStructView.setQty(qty);

                    ItemUploadStruckModel itemUploadStruckModel = new ItemUploadStruckModel();
                    itemUploadStruckModel.setId(productKnowledgeModel.getId());
                    itemUploadStruckModel.setQty(Integer.parseInt(qty));
                    uploadStruckModel.getItemUploadStruckModels().add(itemUploadStruckModel);
                    description = description + "  - " + productKnowledgeModel.getProductName() + " : " + qty + " buah\n";
                }

                if(errorMessage != null){
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    return;
                }

                UploadStructConfirmFragment uploadStructConfirmFragment = new UploadStructConfirmFragment();
                uploadStructConfirmFragment.setUploadStruckModel(uploadStruckModel);
                uploadStructConfirmFragment.setImageBitmap(imageBitmap);
                uploadStructConfirmFragment.setDescription(description);
                if(getActivity() != null && getActivity() instanceof BaseFragmentActivity) {
                    BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
                    activity.swap(R.id.containerFragmentUploadStruct, uploadStructConfirmFragment, true);
                }
                break;
        }
    }

    private void configureView(View rootView){
        txtDate = (TextView) rootView.findViewById(R.id.txtDate);
        txtTime = (TextView) rootView.findViewById(R.id.txtTime);
        imgTakeAPhoto = (ImageView) rootView.findViewById(R.id.imgTakeAPhoto);
        edtStruckNumber = (EditText) rootView.findViewById(R.id.edtStruckNumber);
        contentProdukType = (LinearLayout) rootView.findViewById(R.id.contentProdukType);

        rootView.findViewById(R.id.btnDate).setOnClickListener(this);
        rootView.findViewById(R.id.btnTime).setOnClickListener(this);
        rootView.findViewById(R.id.btnPlus).setOnClickListener(this);
        rootView.findViewById(R.id.btnCheckForm).setOnClickListener(this);
        rootView.findViewById(R.id.btnTakeAPhoto).setOnClickListener(this);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        txtDate.setText(addZeroInString(day)+"/"+addZeroInString(month)+"/"+addZeroInString(year));
        txtTime.setText(addZeroInString(hour)+":"+addZeroInString(minute));

        if(dateString != null && REQUEST_CODE_UPLOAD_SUCCESS == 0){
            txtDate.setText(dateString);
        }
        if(timeString != null && REQUEST_CODE_UPLOAD_SUCCESS == 0){
            txtTime.setText(timeString);
        }

        if(imageBitmap != null){
            onTakeAPhoto(imageBitmap);
            rootView.findViewById(R.id.btnTakeAPhoto).setEnabled(false);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        txtDate.setText(addZeroInString(day)+"/"+addZeroInString(month)+"/"+addZeroInString(year));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        txtTime.setText(addZeroInString(hourOfDay) + ":" + addZeroInString(minute));
    }

    private String addZeroInString(int data){
        String dataString = data + "";
        return (dataString.length() == 1)?"0"+dataString:dataString;
    }

    public void onTakeAPhoto(Bitmap bitmap) {
        imageBitmap = bitmap;
        imgTakeAPhoto.setImageBitmap(bitmap);
        imgTakeAPhoto.setVisibility(View.VISIBLE);
        if(getView() != null) {
            getView().findViewById(R.id.btnTakeAPhoto).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onGetResponse(BranchHealthModel branchHealthModel) {
        if(branchHealthModel != null && branchHealthModel instanceof MainSKUModel){
            MainSKUModel mainSKUModel = (MainSKUModel) branchHealthModel;
            productKnowledgeModels = mainSKUModel.getProductKnowledgeModels();

            configureItemProductViews();
        }
    }

    @Override
    public void onGetErrorResponse(VolleyError error) {
    }

    private void createItemProductStructView(){
        if(productKnowledgeModels != null && productKnowledgeModels.size() > 0) {
            ItemProductStructView itemProductStructView = new ItemProductStructView();
            View view = itemProductStructView.getView(getContext());
            itemProductStructViews.add(itemProductStructView);
            contentProdukType.addView(view);
            itemProductStructView.setPosition(itemProductStructViews.size() - 1);
            configureProductType(itemProductStructView, view);
        }
    }

    private void configureProductType(final ItemProductStructView itemProductStructView, final View rootView){
        itemProductStructView.getBtnProductType().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItemProductStructView = itemProductStructView;
                createPopupMenu(view);
            }
        });

        itemProductStructView.getBtnMinus().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemProductStructViews.size() > 1) {
                    ((ViewManager) rootView.getParent()).removeView(rootView);
                    if(itemProductStructView.getPosition() < itemProductStructViews.size())
                        itemProductStructViews.remove(itemProductStructView.getPosition());
                }
            }
        });
    }

    public void createPopupMenu(View view) {
        if(alertDialog == null) {
            alertDialog = new AlertDialog.Builder(getActivity());

            CharSequence [] items = new CharSequence[productKnowledgeModels.size()];
            for (int i = 0; i < productKnowledgeModels.size(); i++) {
                ProductKnowledgeModel productKnowledgeModel = productKnowledgeModels.get(i);
                items[i] = productKnowledgeModel.getProductName();
            }

            alertDialog.setTitle(R.string.edt_product_type).setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int position) {
                    // The 'which' argument contains the index position
                    // of the selected item
                    selectedItemProductStructView.setProductKnowledgeModel(productKnowledgeModels.get(position));
                    selectedItemProductStructView.setTextProductType(productKnowledgeModels.get(position).getProductName().toString());
                }
            });
        }

        alertDialog.show();
    }

    private void configureItemProductViews(){
        if(itemProductStructViews.size() == 0){
            createItemProductStructView();
        }
        else {
            for (int i = 0; i < itemProductStructViews.size(); i++){
                ItemProductStructView itemProductStructView = itemProductStructViews.get(i);
                View view = itemProductStructView.getView(getContext());
                contentProdukType.addView(view);
                itemProductStructView.setPosition(i);
                configureProductType(itemProductStructView, view);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(REQUEST_CODE_UPLOAD_SUCCESS == 1) {
            edtStruckNumber.setText("");
            if(itemProductStructViews != null && itemProductStructViews.size() > 0){
                ItemProductStructView itemProductStructView = itemProductStructViews.get(0);
                itemProductStructView.setTextQty("");

            }
            REQUEST_CODE_UPLOAD_SUCCESS = 0;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK){
            switch(requestCode){
                case REQ_CODE_PICK_FROM_CAMERA:
                    onTakeAPhoto(ImageUtils.compressImage(getContext(), mCurrentPhotoPath, 600, 400));
                    break;
                default:
                    break;
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQ_CODE_PICK_FROM_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
