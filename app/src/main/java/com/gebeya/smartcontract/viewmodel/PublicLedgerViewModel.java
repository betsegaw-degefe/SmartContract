package com.gebeya.smartcontract.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.gebeya.smartcontract.App;
import com.gebeya.smartcontract.model.data.dto.PublicLedgerResponseDTO;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.repository.PublicLedgerRepository;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class PublicLedgerViewModel extends AndroidViewModel {

    //private static final MutableLiveData MUTABLE_LIVE_DATA = new MutableLiveData();
    private LiveData<PublicLedgerResponseDTO> publicLedgerResponseObservable;

    BoxStore userBox;
    Box<UserLoginData> box;

   /* static {
        MUTABLE_LIVE_DATA.setValue(null);
    }*/

    public PublicLedgerViewModel(@NonNull Application application) {
        super(application);

        userBox = ((App) getApplication().getApplicationContext()).getStore();
        box = userBox.boxFor(UserLoginData.class);

        // loads User token from objectBox
        List<UserLoginData> users = box.getAll();
        String token = users.get(0).getToken();
        String bearerToken = "Bearer " + token;


        publicLedgerResponseObservable = PublicLedgerRepository.getInstance()
              .getPublicLedgerRepository(bearerToken);
    }

    public LiveData<PublicLedgerResponseDTO> getPublicLedgerResponseObservable() {
        return publicLedgerResponseObservable;
    }
}
