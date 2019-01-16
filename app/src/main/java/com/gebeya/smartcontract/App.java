package com.gebeya.smartcontract;


import android.app.Application;

import com.gebeya.framework.utils.Util;
import com.gebeya.smartcontract.model.data.objectBox.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.Box;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * Custom Application implementation in order to use the Application Context for initialization
 * of data.
 */
public class App extends Application {

    private BoxStore mStore;

    public BoxStore getStore() {
        return mStore;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Util.d(this, "< ============================== Application.onCreate() ============================== >");

        // Prepare the BoxStore object once for app.
        mStore = MyObjectBox.builder().androidContext(App.this).build();



    }
}
