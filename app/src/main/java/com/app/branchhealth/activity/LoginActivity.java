package com.app.branchhealth.activity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.app.branchhealth.R;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.listener.HTTPListener;
import com.app.branchhealth.listener.LoginListener;
import com.app.branchhealth.model.BranchHealthModel;
import com.app.branchhealth.model.LoginModel;
import com.app.branchhealth.receiver.NetworkStateReceiver;
import com.app.branchhealth.util.EncryptionUtils;

/**
 * Created by eReFeRHa on 26/2/16.
 */
public class LoginActivity extends BaseFragmentActivity implements View.OnClickListener, NetworkStateReceiver.UpdateNetworkState, HTTPListener.OnHTTPListener {

    //UI
    private TextView txtNoInternet;
    private EditText edtEmail, edtPassword;
    private CheckBox chkRememberMe;

    //Receiver
    private NetworkStateReceiver networkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // add broadcast check internet connection
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.registerUpdateNetworkState(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, intentFilter);
    }

    @Override
    protected int getLayout() {
        return R.layout.act_login;
    }

    @Override
    protected int getContainerBody() {
        return 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        configureTxtNotInternetConnection(NetworkStateReceiver.IS_INTERNET_CONNECTION);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
    }

    @Override
    public void onUpdateNetworkState(boolean isConnectToInternet) {
        configureTxtNotInternetConnection(isConnectToInternet);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.txtRegister:
                goToNextPage(RegisterActivity.class);
                break;
            case R.id.txtForgotPassword:
                goToNextPage(ForgotPasswordActivity.class);
                break;
            case R.id.btnLogin:
                LoginModel loginModel = validateData();
                if(loginModel != null){
                    LoginListener loginListener = new LoginListener();
                    loginListener.setLoginModel(loginModel);
                    loginListener.setOnHTTPListener(this);
                    loginListener.callRequest(this);
                }
                break;
        }
    }

    @Override
    protected void configureView() {
        txtNoInternet = (TextView) findViewById(R.id.txtNoInternet);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        chkRememberMe = (CheckBox) findViewById(R.id.chkRememberMe);

        boolean isRememberMe = AppUserPreference.getBooleanData(this, AppUserPreference.KEY_IS_REMEMBER_ME);
        if(isRememberMe){
            String userName = AppUserPreference.getStringData(this, AppUserPreference.KEY_USERNAME);
            String password = AppUserPreference.getStringData(this, AppUserPreference.KEY_PASSWORD);

            edtEmail.setText(userName);
            edtPassword.setText(password);
        }
        //edtEmail.setText("beetlebox.dev@gmail.com");
        //edtPassword.setText("Testing123456");

        findViewById(R.id.txtRegister).setOnClickListener(this);
        findViewById(R.id.txtForgotPassword).setOnClickListener(this);
        findViewById(R.id.btnLogin).setOnClickListener(this);
    }

    private void configureTxtNotInternetConnection(boolean isConnectToInternet){
        txtNoInternet.setVisibility((isConnectToInternet) ? View.INVISIBLE : View.VISIBLE);

        edtEmail.setEnabled(isConnectToInternet);
        edtPassword.setEnabled(isConnectToInternet);
        chkRememberMe.setEnabled(isConnectToInternet);
        findViewById(R.id.txtRegister).setEnabled(isConnectToInternet);
        findViewById(R.id.txtForgotPassword).setEnabled(isConnectToInternet);
        findViewById(R.id.btnLogin).setEnabled(isConnectToInternet);
    }

    private LoginModel validateData(){
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        LoginModel loginModel = null;

        // cek data kosong
        if(email.length() == 0 || password.length() == 0){
            Toast.makeText(this, "Silahkan isi semua field yang ada", Toast.LENGTH_SHORT).show();
        }
        else{
            loginModel = new LoginModel();
            loginModel.setUsername(email);
            String enc = EncryptionUtils.encrypt(password, this);
            //enc = (enc.length()>24)?enc.substring(0, 24):enc;
            enc = enc.replace("\n", "");
            loginModel.setPassword(enc);
            loginModel.setToken(AppUserPreference.getStringData(this, AppUserPreference.KEY_GCM_TOKEN));
        }

        return loginModel;
    }

    @Override
    public void onGetResponse(BranchHealthModel branchHealthModel) {
        AppUserPreference.putData(this, AppUserPreference.KEY_IS_REMEMBER_ME, chkRememberMe.isChecked());
        if(chkRememberMe.isChecked()){
            AppUserPreference.putData(this, AppUserPreference.KEY_PASSWORD, edtPassword.getText().toString());
        }
        else{
            AppUserPreference.putData(this, AppUserPreference.KEY_PASSWORD, "");
        }

        AppUserPreference.putData(this, AppUserPreference.KEY_IS_LOGIN, true);

        String prevUsername = AppUserPreference.getStringData(this, AppUserPreference.KEY_USERNAME);
        AppUserPreference.clearAllData(this, edtEmail.getText().toString().equals(prevUsername));

        AppUserPreference.putData(this, AppUserPreference.KEY_USERNAME, edtEmail.getText().toString());

        goToNextPage(MainActivity.class);
        finish();
    }

    @Override
    public void onGetErrorResponse(VolleyError error) {
    }
}
