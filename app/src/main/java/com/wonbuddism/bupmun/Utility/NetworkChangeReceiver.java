package com.wonbuddism.bupmun.Utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.wonbuddism.bupmun.Writing.HTTPconnection.LogRegistManager;


public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String status = NetworkStatusManger.getConnectivityStatusString(context);
        new PrefNetworkStatusManager(context).networkStatusChange(status);
        if(new PrefNetworkStatusManager(context).getNetworkStatus()){
            new LogRegistManager(context).LocalLogUpload();
        }
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}
