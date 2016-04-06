package com.app.branchhealth.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.listener.GetRegisterDataListener;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.listener.RegisterListener;
import com.app.branchhealth.model.ApotikGroupModel;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.BranchModel;
import com.app.branchhealth.model.CityModel;
import com.app.branchhealth.model.CommonPostRequest;
import com.app.branchhealth.model.GetRegisterDataModel;
import com.app.branchhealth.model.PositionModel;
import com.app.branchhealth.model.RegisterModel;
import com.app.branchhealth.util.EncryptionUtils;
import com.app.branchhealth.util.StringUtils;

import java.util.ArrayList;

/**
 * Created by eReFeRHa on 26/2/16.
 */
public class RegisterActivity extends BaseFragmentActivity implements View.OnClickListener, HTTPListener.OnHTTPListener {

    //UI
    private EditText edtNamaDepan, edtNamaBelakang, edtEmail, edtPassword, edtConfirmPassword,
            edtNomorTelpon1, edtNomorTelpon2, edtAlamatRumah, edtAlamatApotik, edtOther;
    private TextView txtJabatan, txtKota, txtCabang, txtNamaApotik;
    private PopupMenu cityPopupMenu, branchPopupMenu, apotikGroupPopupMenu, positionPopupMenu;
    private String idPosition, idCity, idBranch, idApotekGroup = "";
    protected AlertDialog.Builder alertDialog;

    //DATA
    private ArrayList<CityModel> cityModels = new ArrayList<CityModel>();
    private ArrayList<ApotikGroupModel> apotikGroupModels = new ArrayList<>();
    private ArrayList<PositionModel> positionModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.act_register;
    }

    @Override
    protected int getContainerBody() {
        return 0;
    }

    @Override
    protected void configureView() {
        edtNamaDepan = (EditText) findViewById(R.id.edtNamaDepan);
        edtNamaBelakang = (EditText) findViewById(R.id.edtNamaBelakang);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        edtNomorTelpon1 = (EditText) findViewById(R.id.edtNomorTelpon1);
        edtNomorTelpon2 = (EditText) findViewById(R.id.edtNomorTelpon2);
        edtAlamatRumah = (EditText) findViewById(R.id.edtAlamatRumah);
        edtAlamatApotik = (EditText) findViewById(R.id.edtAlamatApotik);
        edtOther = (EditText) findViewById(R.id.edtOther);

        txtJabatan = (TextView) findViewById(R.id.txtJabatan);
        txtKota = (TextView) findViewById(R.id.txtKota);
        txtCabang = (TextView) findViewById(R.id.txtCabang);
        txtNamaApotik = (TextView) findViewById(R.id.txtNamaApotik);

        findViewById(R.id.btnRegister).setOnClickListener(this);
        findViewById(R.id.btnJabatan).setOnClickListener(this);
        findViewById(R.id.btnCity).setOnClickListener(this);
        findViewById(R.id.btnBranch).setOnClickListener(this);
        findViewById(R.id.btnNamaApotik).setOnClickListener(this);

        GetRegisterDataListener getRegisterDataListener = new GetRegisterDataListener();
        getRegisterDataListener.setOnHTTPListener(this);
        getRegisterDataListener.callRequest(this);

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
            case R.id.btnRegister:
                RegisterModel registerModel = validateData();
                if(registerModel != null){
                    RegisterListener registerListener = new RegisterListener();
                    registerListener.setRegisterModel(registerModel);
                    registerListener.setOnHTTPListener(this);
                    registerListener.callRequest(this);
                }
                break;
        }
    }

    private void createPopupMenu(){
        txtJabatan.setText(positionModels.get(0).getName());
        txtKota.setText(cityModels.get(0).getName());
        txtCabang.setText(cityModels.get(0).getBranchModels().get(0).getName());
        txtNamaApotik.setText(apotikGroupModels.get(0).getName());

        idPosition = positionModels.get(0).getId();
        idCity = cityModels.get(0).getId();
        idBranch = cityModels.get(0).getBranchModels().get(0).getId();

        //POSITION POPUP MENU
        positionPopupMenu = new PopupMenu(this, findViewById(R.id.btnJabatan));
        for (int i = 0; i < positionModels.size(); i++) {
            PositionModel positionModel = positionModels.get(i);
            positionPopupMenu.getMenu().add(Menu.NONE, Integer.valueOf(positionModel.getId()), 0, positionModel.getName());
        }

        //registering popup with OnMenuItemClickListener
        positionPopupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                txtJabatan.setText(item.getTitle());
                idPosition = item.getItemId() + "";
                return true;
            }
        });

        //CITY POPUP MENU
        cityPopupMenu = new PopupMenu(this, findViewById(R.id.btnCity));
        for (int i = 0; i < cityModels.size(); i++) {
            CityModel cityModel = cityModels.get(i);
            cityPopupMenu.getMenu().add(Menu.NONE, i, 0, cityModel.getName());
        }

        //registering popup with OnMenuItemClickListener
        cityPopupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                txtKota.setText(item.getTitle());

                branchPopupMenu.getMenu().clear();
                ArrayList<BranchModel> branchModels = cityModels.get(id).getBranchModels();
                idCity = cityModels.get(id).getId();
                for (int i = 0; i < branchModels.size(); i++) {
                    BranchModel branchModel = branchModels.get(i);
                    if(i == 0){
                        idBranch = branchModel.getId();
                        txtCabang.setText(branchModel.getName());
                    }
                    branchPopupMenu.getMenu().add(Menu.NONE, Integer.valueOf(branchModel.getId()), 0, branchModel.getName());
                }
                return true;
            }
        });

        //BRANCH POPUP MENU
        branchPopupMenu = new PopupMenu(this, findViewById(R.id.btnBranch));
        ArrayList<BranchModel> branchModels = cityModels.get(0).getBranchModels();
        for (int i = 0; i < branchModels.size(); i++) {
            BranchModel branchModel = branchModels.get(i);
            branchPopupMenu.getMenu().add(Menu.NONE, Integer.valueOf(branchModel.getId()), 0, branchModel.getName());
        }

        //registering popup with OnMenuItemClickListener
        branchPopupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                txtCabang.setText(item.getTitle());
                idBranch = item.getItemId() + "";
                return true;
            }
        });

        //APOTIK POPUP MENU
        apotikGroupPopupMenu = new PopupMenu(this, findViewById(R.id.btnNamaApotik));
        for (int i = 0; i < apotikGroupModels.size(); i++) {
            ApotikGroupModel apotikGroupModel = apotikGroupModels.get(i);
            apotikGroupPopupMenu.getMenu().add(Menu.NONE, Integer.valueOf(apotikGroupModel.getId()), 0, apotikGroupModel.getName());
        }

        //registering popup with OnMenuItemClickListener
        apotikGroupPopupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                edtOther.setText("");
                txtNamaApotik.setText(item.getTitle());
                idApotekGroup = item.getItemId() + "";
                edtOther.setEnabled((id == 999));
                edtOther.setVisibility((id == 999) ? View.VISIBLE : View.GONE);
                return true;
            }
        });
    }

    private RegisterModel validateData(){
        String namaDepan = edtNamaDepan.getText().toString().trim();
        String namaBelakang = edtNamaBelakang.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        String nomorTelpon1 = edtNomorTelpon1.getText().toString().trim();
        String nomorTelpon2 = edtNomorTelpon2.getText().toString().trim();
        String alamatRumah = edtAlamatRumah.getText().toString().trim();
        String alamatApotik = edtAlamatApotik.getText().toString().trim();
        String other = edtOther.getText().toString().trim();
        String namaApotik = txtNamaApotik.getText().toString();

        RegisterModel registerModel = null;
        // cek data kosong
        if(namaDepan.length() == 0 || namaBelakang.length() == 0 || email.length() == 0 ||
                password.length() == 0 || confirmPassword.length() == 0 || nomorTelpon1.length() == 0 ||
                nomorTelpon2.length() == 0 || alamatRumah.length() == 0 || alamatApotik.length() == 0){
            Toast.makeText(this, "Silahkan isi semua field yang ada", Toast.LENGTH_SHORT).show();
        }
        else if(!StringUtils.checkEmail(email)){
            Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show();
        }
        else if("999".equalsIgnoreCase(idApotekGroup) && other.length() == 0){
            Toast.makeText(this, "Silahkan masukkan nama apotik", Toast.LENGTH_SHORT).show();
        }
        else{
            registerModel = new RegisterModel();
            registerModel.setFirstName(namaDepan);
            registerModel.setLastName(namaBelakang);
            registerModel.setIdPosition(idPosition);
            registerModel.setEmail(email);
            String enc = EncryptionUtils.encrypt(password, this);
            //enc = (enc.length()>24)?enc.substring(0, 24):enc;
            enc = enc.replace("\n", "");
            registerModel.setPassword(enc);
            registerModel.setPhone1(nomorTelpon1);
            registerModel.setPhone2(nomorTelpon2);
            registerModel.setHomeAddress(alamatRumah);
            registerModel.setApotekAddress(alamatApotik);
            registerModel.setIdCity(idCity);
            registerModel.setIdBranch(idBranch);
            registerModel.setApotekName("999".equalsIgnoreCase(idApotekGroup)?other:namaApotik);
        }

        return registerModel;
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

                createPopupMenu();

            }
            else if (branchHealthModel instanceof CommonPostRequest) {
                CommonPostRequest commonPostRequest = (CommonPostRequest) branchHealthModel;
                alertDialog.setMessage(commonPostRequest.getMessage());
                alertDialog.show();
            }
        }
    }

    @Override
    public void onGetErrorResponse(VolleyError error) {

    }
}
