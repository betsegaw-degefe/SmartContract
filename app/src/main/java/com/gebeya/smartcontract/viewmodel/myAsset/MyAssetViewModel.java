package com.gebeya.smartcontract.viewmodel.myAsset;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.gebeya.smartcontract.App;
import com.gebeya.smartcontract.model.data.dto.ErrorResponseDTO;
import com.gebeya.smartcontract.model.data.dto.MyAssetCarResponseDTO;
import com.gebeya.smartcontract.model.data.dto.MyAssetHouseResponseDTO;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.repository.myAsset.MyAssetRepository;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class MyAssetViewModel extends AndroidViewModel {

    private final static MutableLiveData<MyAssetCarResponseDTO> myAssetCarResponseObservable = new MutableLiveData<>();
    private final static MutableLiveData<MyAssetHouseResponseDTO> myAssetHouseResponseObservable = new MutableLiveData<>();

    private final static MutableLiveData<ErrorResponseDTO> mErrorResponseObservable = new MutableLiveData<>();

    // Object box database.
    BoxStore userBox;
    Box<UserLoginData> box;

    public MyAssetViewModel(@NonNull Application application) {
        super(application);

        // Object box database.
        BoxStore userBox = ((App) getApplication().getApplicationContext()).getStore();
        Box<UserLoginData> box = userBox.boxFor(UserLoginData.class);

        // loads User token from objectBox
        List<UserLoginData> users = box.getAll();
        String token = users.get(0).getToken();
        String bearerToken = "Bearer " + token;
        String userId = users.get(0).getUserId();

        // Create an instance of my asset repository for network request.
        MyAssetRepository.getInstance().getMyAssetRepository(bearerToken, userId);

    }

    /**
     * Create a LiveData instance of MyAssetCarResponseDTO object.
     *
     * @return a LiveData instance of MyAssetCarResponseDTO.
     */
    public static MutableLiveData<MyAssetCarResponseDTO> getMyAssetCarResponseObservable() {
        return myAssetCarResponseObservable;
    }

    /**
     * Create a LiveData instance of MyAssetHouseResponseDTO object.
     *
     * @return a LiveData instance of MyAssetHouseCarResponseDTO.
     */
    public static MutableLiveData<MyAssetHouseResponseDTO> getMyAssetHouseResponseObservable() {
        return myAssetHouseResponseObservable;
    }

    /**
     * Create a LiveData instance of ErrorResponseDTO object.
     *
     * @return a LiveData instance of ErrorResponseDTO.
     */
    public static MutableLiveData<ErrorResponseDTO> getErrorResponseObservable() {
        return mErrorResponseObservable;
    }
}
