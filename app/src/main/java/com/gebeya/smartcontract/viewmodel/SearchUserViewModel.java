package com.gebeya.smartcontract.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class SearchUserViewModel extends AndroidViewModel {

    public MutableLiveData<String> oldPassword = new MutableLiveData<>();
    public MutableLiveData<String> newPassword = new MutableLiveData<>();
    public MutableLiveData<String> confirmPassword = new MutableLiveData<>();

    public SearchUserViewModel(@NonNull Application application) {
        super(application);
    }
}
