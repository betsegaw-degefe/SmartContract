package com.gebeya.framework.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

public final class CheckInternetConnecction {


    public boolean CheckInternetConnecction(Context context) {

        // querying the active network and determine if it has Internet connectivity.

        ConnectivityManager cm =
              (ConnectivityManager) Objects.requireNonNull(context)
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
              activeNetwork.isConnectedOrConnecting();
    }
}
