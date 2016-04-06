package com.app.branchhealth.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.activity.BaseFragmentActivity;
import com.app.branchhealth.activity.LoginActivity;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.listener.GetProfileListener;
import com.app.branchhealth.listener.GetRegisterDataListener;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.listener.LogoutListener;
import com.app.branchhealth.listener.SaveProfileListener;
import com.app.branchhealth.model.ApotikGroupModel;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.BranchModel;
import com.app.branchhealth.model.CityModel;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.model.GetRegisterDataModel;
import com.app.branchhealth.model.PositionModel;
import com.app.branchhealth.model.ProfileModel;
import com.app.branchhealth.model.RegisterModel;
import com.app.branchhealth.util.ImageUtils;
import com.app.branchhealth.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by eReFeRHa on 1/3/16.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener, HTTPListener.OnHTTPListener {

    //UI
    private EditText edtNamaDepan, edtNamaBelakang, edtEmail, edtPassword, edtConfirmPassword,
            edtNomorTelpon1, edtNomorTelpon2, edtAlamatRumah, edtAlamatApotik, edtOther;
    private TextView txtJabatan, txtKota, txtCabang, txtNamaApotik;
    private PopupMenu cityPopupMenu, branchPopupMenu, apotikGroupPopupMenu, positionPopupMenu, pickProfilePopMenu;
    private Button btnEditProfile;
    private String idPosition, idCity, idBranch, idApotekGroup = "";
    protected AlertDialog.Builder alertDialog;
    private ImageView btnProfile;

    //DATA
    public static final int REQ_CODE_PICK_FROM_CAMERA = 2;
    public static final int REQ_CODE_PICK_FROM_GALLERY = 3;
    private ProfileModel profileModel, tempProfileModel;
    private ArrayList<CityModel> cityModels = new ArrayList<CityModel>();
    private ArrayList<ApotikGroupModel> apotikGroupModels = new ArrayList<>();
    private ArrayList<PositionModel> positionModels = new ArrayList<>();
    private Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_profile, container, false);
        configureView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GetRegisterDataListener getRegisterDataListener = new GetRegisterDataListener();
        getRegisterDataListener.setOnHTTPListener(this);
        getRegisterDataListener.callRequest(getContext());
    }

    private void configureView(View view){
        if(view != null){
            tempProfileModel = new ProfileModel();

            edtNamaDepan = (EditText) view.findViewById(R.id.edtNamaDepan);
            edtNamaBelakang = (EditText) view.findViewById(R.id.edtNamaBelakang);
            edtEmail = (EditText) view.findViewById(R.id.edtEmail);
            edtPassword = (EditText) view.findViewById(R.id.edtPassword);
            edtPassword.setVisibility(View.GONE);
            edtConfirmPassword = (EditText) view.findViewById(R.id.edtConfirmPassword);
            edtConfirmPassword.setVisibility(View.GONE);
            edtNomorTelpon1 = (EditText) view.findViewById(R.id.edtNomorTelpon1);
            edtNomorTelpon2 = (EditText) view.findViewById(R.id.edtNomorTelpon2);
            edtAlamatRumah = (EditText) view.findViewById(R.id.edtAlamatRumah);
            edtAlamatApotik = (EditText) view.findViewById(R.id.edtAlamatApotik);
            edtOther = (EditText) view.findViewById(R.id.edtOther);

            txtJabatan = (TextView) view.findViewById(R.id.txtJabatan);
            txtKota = (TextView) view.findViewById(R.id.txtKota);
            txtCabang = (TextView) view.findViewById(R.id.txtCabang);
            txtNamaApotik = (TextView) view.findViewById(R.id.txtNamaApotik);

            btnEditProfile = (Button) view.findViewById(R.id.btnEditProfile);
            btnEditProfile.setOnClickListener(this);
            view.findViewById(R.id.btnChangePassword).setOnClickListener(this);
            view.findViewById(R.id.btnLogout).setOnClickListener(this);
            view.findViewById(R.id.btnJabatan).setOnClickListener(this);
            view.findViewById(R.id.btnCity).setOnClickListener(this);
            view.findViewById(R.id.btnBranch).setOnClickListener(this);
            view.findViewById(R.id.btnNamaApotik).setOnClickListener(this);

            btnProfile = (ImageView) view.findViewById(R.id.btnProfile);
            btnProfile.setOnClickListener(this);

            setEditModeProfile(0, view);

            alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("Info")
                    .setMessage("")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("OK", null);
            alertDialog.setCancelable(false);

            enableQuickActionSetIcon(view);
        }
    }

    private void setDefaultValue(){
        if(profileModel != null) {
            edtNamaDepan.setText(profileModel.getFirstName());
            edtNamaBelakang.setText(profileModel.getLastName());
            edtEmail.setText(profileModel.getEmail());
            edtNomorTelpon1.setText(profileModel.getPhone1());
            edtNomorTelpon2.setText(profileModel.getPhone2());
            edtAlamatRumah.setText(profileModel.getHomeAddress());
            edtAlamatApotik.setText(profileModel.getApotekAddress());
            edtOther.setText(profileModel.getApotekName());

            txtJabatan.setText(getPosistionName());
            configureApotekName();

            String[] cityAndBranchName = getCityAndBranchName();
            if(cityAndBranchName != null && cityAndBranchName.length > 1) {
                txtKota.setText(cityAndBranchName[0]);
                txtCabang.setText(cityAndBranchName[1]);
            }

            String imageProfile = profileModel.getProfilePict();
            if(imageProfile != null && imageProfile.trim().length() > 0){
                bitmap = ImageUtils.getBitmapFromBase64(imageProfile, 80);
                if(bitmap != null)
                    btnProfile.setImageBitmap(bitmap);
            }
        }
    }

    protected void enableQuickActionSetIcon(View view){
        pickProfilePopMenu = new PopupMenu(getContext(), view.findViewById(R.id.btnProfile));
        pickProfilePopMenu.getMenu().add(Menu.NONE, 0, 0, "Ambil foto");
        pickProfilePopMenu.getMenu().add(Menu.NONE, 1, 0, "Unduh dari Gallery");

        //registering popup with OnMenuItemClickListener
        pickProfilePopMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int index = item.getItemId();
                switch (index) {
                    case 0:
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra("return-data", true);
                        cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        startActivityForResult(cameraIntent, REQ_CODE_PICK_FROM_CAMERA);
                        break;
                    case 1:
                        Intent galleryIntent = new Intent();
                        galleryIntent.setType("image/*");
                        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                        // image chooser
                        startActivityForResult(Intent.createChooser(galleryIntent, "Gallery"), REQ_CODE_PICK_FROM_GALLERY);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.outHeight = 80;
        options.outWidth = 80;
        options.inSampleSize = 4;

        if(resultCode == Activity.RESULT_OK){
            switch(requestCode){
                case REQ_CODE_PICK_FROM_CAMERA:
                    bitmap = (Bitmap) data.getExtras().get("data");
                    bitmap = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    if(bitmap != null)
                        btnProfile.setImageBitmap(bitmap);
                    break;
                case REQ_CODE_PICK_FROM_GALLERY:
                    Uri selectedImageUri = data.getData();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (bitmap != null) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
                        if(bitmap != null)
                            btnProfile.setImageBitmap(bitmap);
                    }
                    else{
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                        // set title
                        alertDialogBuilder.setTitle("Error");
                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Gagal memasukkan gambar")
                                .setCancelable(false)
                                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.dismiss();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }

                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnProfile:
                if(pickProfilePopMenu != null)
                    pickProfilePopMenu.show();
                break;
            case R.id.btnJabatan:
                if(positionPopupMenu != null)
                    positionPopupMenu.show();
                break;
            case R.id.btnCity:
                if(cityPopupMenu != null)
                    cityPopupMenu.show();
                break;
            case R.id.btnBranch:
                if(branchPopupMenu != null)
                    branchPopupMenu.show();
                break;
            case R.id.btnNamaApotik:
                if(apotikGroupPopupMenu != null)
                    apotikGroupPopupMenu.show();
                break;
            case R.id.btnEditProfile:
                int tag = (Integer) btnEditProfile.getTag();
                if(tag == 0) {
                    setEditModeProfile(1, getView());
                }
                else{
                    RegisterModel registerModel = validateData();
                    if(registerModel != null){
                        SaveProfileListener saveProfileListener = new SaveProfileListener();
                        saveProfileListener.setRegisterModel(registerModel);
                        saveProfileListener.setOnHTTPListener(this);
                        saveProfileListener.callRequest(getContext());
                    }
                }
                break;
            case R.id.btnChangePassword:
                if(getActivity() != null && getActivity() instanceof BaseFragmentActivity) {
                    BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
                    activity.swap(R.id.containerFragmentSetting, new ChangePasswordFragment(), true);
                }
                break;
            case R.id.btnLogout:
                LogoutListener logoutListener = new LogoutListener();
                CommonPostRequest commonPostRequest = new CommonPostRequest();
                commonPostRequest.setUsername(AppUserPreference.getStringData(getContext(), AppUserPreference.KEY_USERNAME));
                logoutListener.setCommonPostRequest(commonPostRequest);
                logoutListener.setOnHTTPListener(new HTTPListener.OnHTTPListener() {
                    @Override
                    public void onGetResponse(BranchHealthModel branchHealthModel) {
                        goToLogin();
                    }

                    @Override
                    public void onGetErrorResponse(VolleyError error) {
                        goToLogin();
                    }
                });
                logoutListener.callRequest(getContext());
                break;
        }
    }

    private void goToLogin(){
        if(getActivity() != null && getActivity() instanceof BaseFragmentActivity){
            AppUserPreference.putData(getContext(), AppUserPreference.KEY_IS_LOGIN, false);
            ((BaseFragmentActivity) getActivity()).goToNextPage(LoginActivity.class);
            getActivity().finish();
        }
    }

    private void createPopupMenu(View view){
        if(view != null) {
            //POSITION POPUP MENU
            positionPopupMenu = new PopupMenu(getContext(), view.findViewById(R.id.btnJabatan));
            for (int i = 0; i < positionModels.size(); i++) {
                PositionModel positionModel = positionModels.get(i);
                positionPopupMenu.getMenu().add(Menu.NONE, Integer.valueOf(positionModel.getId()), 0, positionModel.getName());
            }

            //registering popup with OnMenuItemClickListener
            positionPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    txtJabatan.setText(item.getTitle());
                    idPosition = item.getItemId() + "";
                    return true;
                }
            });

            //CITY POPUP MENU
            cityPopupMenu = new PopupMenu(getContext(), view.findViewById(R.id.btnCity));
            for (int i = 0; i < cityModels.size(); i++) {
                CityModel cityModel = cityModels.get(i);
                cityPopupMenu.getMenu().add(Menu.NONE, i, 0, cityModel.getName());
            }

            //registering popup with OnMenuItemClickListener
            cityPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    txtKota.setText(item.getTitle());

                    branchPopupMenu.getMenu().clear();
                    ArrayList<BranchModel> branchModels = cityModels.get(id).getBranchModels();
                    idCity = cityModels.get(id).getId();
                    for (int i = 0; i < branchModels.size(); i++) {
                        BranchModel branchModel = branchModels.get(i);
                        if (i == 0) {
                            idBranch = branchModel.getId();
                            txtCabang.setText(branchModel.getName());
                        }
                        branchPopupMenu.getMenu().add(Menu.NONE, Integer.valueOf(branchModel.getId()), 0, branchModel.getName());
                    }
                    return true;
                }
            });

            //BRANCH POPUP MENU
            branchPopupMenu = new PopupMenu(getContext(), view.findViewById(R.id.btnBranch));
            ArrayList<BranchModel> branchModels = cityModels.get(0).getBranchModels();
            for (int i = 0; i < branchModels.size(); i++) {
                BranchModel branchModel = branchModels.get(i);
                branchPopupMenu.getMenu().add(Menu.NONE, Integer.valueOf(branchModel.getId()), 0, branchModel.getName());
            }

            //registering popup with OnMenuItemClickListener
            branchPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    txtCabang.setText(item.getTitle());
                    idBranch = item.getItemId() + "";
                    return true;
                }
            });

            //APOTIK POPUP MENU
            apotikGroupPopupMenu = new PopupMenu(getContext(), view.findViewById(R.id.btnNamaApotik));
            for (int i = 0; i < apotikGroupModels.size(); i++) {
                ApotikGroupModel apotikGroupModel = apotikGroupModels.get(i);
                apotikGroupPopupMenu.getMenu().add(Menu.NONE, Integer.valueOf(apotikGroupModel.getId()), 0, apotikGroupModel.getName());
            }

            //registering popup with OnMenuItemClickListener
            apotikGroupPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    edtOther.setText("");
                    txtNamaApotik.setText(item.getTitle());
                    idApotekGroup = item.getItemId() + "";
                    edtOther.setEnabled((id == 999));
                    edtOther.setVisibility((id == 999)?View.VISIBLE:View.GONE);
                    return true;
                }
            });
        }
    }

    private RegisterModel validateData(){
        String namaDepan = edtNamaDepan.getText().toString().trim();
        String namaBelakang = edtNamaBelakang.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String nomorTelpon1 = edtNomorTelpon1.getText().toString().trim();
        String nomorTelpon2 = edtNomorTelpon2.getText().toString().trim();
        String alamatRumah = edtAlamatRumah.getText().toString().trim();
        String alamatApotik = edtAlamatApotik.getText().toString().trim();
        String other = edtOther.getText().toString().trim();
        String namaApotik = txtNamaApotik.getText().toString();

        RegisterModel registerModel = null;
        // cek data kosong
        if(namaDepan.length() == 0 || namaBelakang.length() == 0 || email.length() == 0 ||
                nomorTelpon1.length() == 0 || nomorTelpon2.length() == 0 || alamatRumah.length() == 0 || alamatApotik.length() == 0){
            Toast.makeText(getContext(), "Silahkan isi semua field yang ada", Toast.LENGTH_SHORT).show();
        }
        else if(!StringUtils.checkEmail(email)){
            Toast.makeText(getContext(), "Email tidak valid", Toast.LENGTH_SHORT).show();
        }
        else if("999".equalsIgnoreCase(idApotekGroup) && other.length() == 0){
            Toast.makeText(getContext(), "Silahkan masukkan nama apotik", Toast.LENGTH_SHORT).show();
        }
        else{
            tempProfileModel = new ProfileModel();
            tempProfileModel.setId(profileModel.getId());
            tempProfileModel.setUpdatedDate(profileModel.getUpdatedDate());
            tempProfileModel.setFirstName(namaDepan);
            tempProfileModel.setLastName(namaBelakang);
            tempProfileModel.setIdPosition(idPosition);
            tempProfileModel.setEmail(email);
            tempProfileModel.setPhone1(nomorTelpon1);
            tempProfileModel.setPhone2(nomorTelpon2);
            tempProfileModel.setHomeAddress(alamatRumah);
            tempProfileModel.setApotekAddress(alamatApotik);
            tempProfileModel.setIdCity(idCity);
            tempProfileModel.setIdBranch(idBranch);
            tempProfileModel.setApotekName("999".equalsIgnoreCase(idApotekGroup)?other:namaApotik);
            if(bitmap != null){
                tempProfileModel.setProfilePict(ImageUtils.getBase64FromBitmap(bitmap));
            }
        }

        return tempProfileModel;
    }

    private void setEditModeProfile(int id, View view){
        btnEditProfile.setTag(id);

        boolean isEditMode = (id == 1);
        btnEditProfile.setText((isEditMode)?R.string.btn_save_profile:R.string.btn_edit_profile);
        edtNamaDepan.setEnabled(isEditMode);
        edtNamaBelakang.setEnabled(isEditMode);
        edtEmail.setEnabled(isEditMode);
        edtNomorTelpon1.setEnabled(isEditMode);
        edtNomorTelpon2.setEnabled(isEditMode);
        edtAlamatRumah.setEnabled(isEditMode);
        edtAlamatApotik.setEnabled(isEditMode);
        edtOther.setEnabled(isEditMode);

        view.findViewById(R.id.btnJabatan).setEnabled(isEditMode);
        view.findViewById(R.id.btnCity).setEnabled(isEditMode);
        view.findViewById(R.id.btnBranch).setEnabled(isEditMode);
        view.findViewById(R.id.btnNamaApotik).setEnabled(isEditMode);

        btnProfile.setEnabled(isEditMode);
    }

    private String getPosistionName(){
        String positionName = "";
        if(profileModel != null && positionModels != null && positionModels.size() > 0){
            String idPosition = profileModel.getIdPosition();
            for(PositionModel positionModel : positionModels){
                String id = positionModel.getId();
                if(idPosition != null && idPosition.equalsIgnoreCase(id)){
                    this.idPosition = id;
                    positionName = positionModel.getName();
                    break;
                }
            }
        }

        return positionName;
    }

    private String[] getCityAndBranchName(){
        String[] cityAndBranchName = new String[2];
        if(profileModel != null && cityModels != null && cityModels.size() > 0){
            String idCity = profileModel.getIdCity();
            String idBranch = profileModel.getIdBranch();
            for(CityModel cityModel : cityModels){
                String id = cityModel.getId();
                if(idCity != null && idCity.equalsIgnoreCase(id)){
                    this.idCity = id;
                    cityAndBranchName[0] = cityModel.getName();
                    for(BranchModel branchModel : cityModel.getBranchModels()){
                        String idB = branchModel.getId();
                        if(idBranch != null && idBranch.equalsIgnoreCase(idB)){
                            this.idBranch = idB;
                            cityAndBranchName[1] = branchModel.getName();

                            break;
                        }
                    }

                    break;
                }
            }
        }
        return cityAndBranchName;
    }

    private void configureApotekName(){
        if(apotikGroupModels != null && apotikGroupModels.size() > 0 && profileModel != null){
            String apotekName = profileModel.getApotekName();
            String defaultIdApotekGroup = "999";
            for(ApotikGroupModel apotikGroupModel : apotikGroupModels){
                String name = apotikGroupModel.getName();
                if(apotekName != null && apotekName.equalsIgnoreCase(name)){
                    defaultIdApotekGroup = apotikGroupModel.getId();
                    break;
                }
            }

            idApotekGroup = defaultIdApotekGroup;
            if("999".equals(idApotekGroup)){
                edtOther.setEnabled(true);
                edtOther.setText(apotekName);
                txtNamaApotik.setText("LAINNYA");
                edtOther.setVisibility(View.VISIBLE);
            }
            else{
                edtOther.setEnabled(false);
                edtOther.setText("");
                txtNamaApotik.setText(apotekName);
                edtOther.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onGetResponse(BranchHealthModel branchHealthModel) {
        if(branchHealthModel != null) {
            if (branchHealthModel instanceof GetRegisterDataModel) {
                GetRegisterDataModel getRegisterDataModel = (GetRegisterDataModel) branchHealthModel;
                cityModels = getRegisterDataModel.getCityModels();
                apotikGroupModels = getRegisterDataModel.getApotikGroupModels();
                positionModels = getRegisterDataModel.getPositionModels();

                ApotikGroupModel otherApotikGroup = new ApotikGroupModel();
                otherApotikGroup.setId("999");
                otherApotikGroup.setName("LAINNYA");
                apotikGroupModels.add(otherApotikGroup);

                GetProfileListener getProfileListener = new GetProfileListener();
                CommonPostRequest commonPostRequest = new CommonPostRequest();
                commonPostRequest.setUsername(AppUserPreference.getStringData(getContext(), AppUserPreference.KEY_USERNAME));
                getProfileListener.setCommonPostRequest(commonPostRequest);
                getProfileListener.setOnHTTPListener(this);
                getProfileListener.callRequest(getContext());
            }
            else if (branchHealthModel instanceof ProfileModel) {
                profileModel = (ProfileModel) branchHealthModel;
                setDefaultValue();
                createPopupMenu(getView());
            }
            else if (branchHealthModel instanceof CommonPostRequest) {
                CommonPostRequest commonPostRequest = (CommonPostRequest) branchHealthModel;
                String message = commonPostRequest.getMessage();
                alertDialog.setMessage(message);
                alertDialog.show();

                profileModel = tempProfileModel;
                setEditModeProfile(0, getView());
            }
        }
    }

    @Override
    public void onGetErrorResponse(VolleyError error) {

    }
}
