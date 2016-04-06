package com.app.branchhealth.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by eReFeRHa on 6/8/15.
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    // listener
    public interface UpdateNetworkState {
        void onUpdateNetworkState(boolean isConnectToInternet);
    }

    public static boolean IS_INTERNET_CONNECTION = false;
    public UpdateNetworkState updateNetworkState;

    public void registerUpdateNetworkState(UpdateNetworkState updateNetworkState) {
        this.updateNetworkState = updateNetworkState;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        IS_INTERNET_CONNECTION = isNetworksAvailable(context);

        if(updateNetworkState != null){
            updateNetworkState.onUpdateNetworkState(IS_INTERNET_CONNECTION);
        }
    }

    public boolean isNetworksAvailable(Context context) {
        ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnMgr != null)  {
            NetworkInfo[] mNetInfo = mConnMgr.getAllNetworkInfo();
            if (mNetInfo != null) {
                for (int i = 0; i < mNetInfo.length; i++) {
                    if (mNetInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
