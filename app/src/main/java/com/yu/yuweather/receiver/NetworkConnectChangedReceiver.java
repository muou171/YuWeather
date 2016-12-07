package com.yu.yuweather.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    private OnNetworkConnectChangedListener onNetworkConnectChangedListener;

    public void setOnNetworkConnectChangedListener(OnNetworkConnectChangedListener onNetworkConnectChangedListener) {
        this.onNetworkConnectChangedListener = onNetworkConnectChangedListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.isConnected()) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        if (onNetworkConnectChangedListener != null) {
                            onNetworkConnectChangedListener.OnNetworkConnectChanged(true);
                        }
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        if (onNetworkConnectChangedListener != null) {
                            onNetworkConnectChangedListener.OnNetworkConnectChanged(true);
                        }
                    }
                } else {
                    if (onNetworkConnectChangedListener != null) {
                        onNetworkConnectChangedListener.OnNetworkConnectChanged(false);
                    }
                }

            } else {   // not connected to the internet
                if (onNetworkConnectChangedListener != null) {
                    onNetworkConnectChangedListener.OnNetworkConnectChanged(false);
                }
            }

        }
    }

    public interface OnNetworkConnectChangedListener {
        void OnNetworkConnectChanged(boolean isNetworkConnected);
    }
}
