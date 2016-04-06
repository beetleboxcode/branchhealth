package com.app.branchhealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.app.branchhealth.R;
import com.app.branchhealth.data.AppUserPreference;
import com.app.branchhealth.services.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by eReFeRHa on 26/2/16.
 */
public class SplashScreenActivity extends BaseFragmentActivity {

    // how long until we go to the next activity
    private final int _splashTime = 4000;
    private Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        // thread for displaying the SplashScreen
        splashTread = new Thread(){
            @Override
            public void run(){
                try{
                    synchronized(this){
                        wait(_splashTime);
                    }
                }catch(InterruptedException e){
                }finally{
                    configureSplash();
                }
            }
        };

        splashTread.start();
    }

    @Override
    protected int getLayout() {
        return R.layout.act_splash_screen;
    }

    @Override
    protected int getContainerBody() {
        return 0;
    }

    @Override
    protected void configureView() {

    }

    private void configureSplash(){
        boolean hasAlreadyOpenApps = AppUserPreference.getBooleanData(this, AppUserPreference.KEY_HAS_ALREADY_OPEN_APPS);
        boolean isLogin = AppUserPreference.getBooleanData(this, AppUserPreference.KEY_IS_LOGIN);
        String username = AppUserPreference.getStringData(this, AppUserPreference.KEY_USERNAME);
        if(isLogin && username != null && username.length() > 0){
            AppUserPreference.clearAllData(this, false);
            goToNextPage(MainActivity.class);
        }
        else {
            goToNextPage((!hasAlreadyOpenApps) ? TermAndConditionActivity.class : LoginActivity.class);
        }
        finish();
    }

    // Function that will handle the touch
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            synchronized(splashTread){
                splashTread.notifyAll();
            }
        }
        return true;
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            return false;
        }
        return true;
    }
}
