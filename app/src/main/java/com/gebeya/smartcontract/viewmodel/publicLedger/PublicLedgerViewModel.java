package com.gebeya.smartcontract.viewmodel.publicLedger;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.gebeya.app.App;
import com.gebeya.smartcontract.model.data.dto.ErrorResponseDTO;
import com.gebeya.smartcontract.model.data.dto.PublicLedgerResponseDTO;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.repository.publicLedger.PublicLedgerRepository;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class PublicLedgerViewModel extends AndroidViewModel {

    private final static MutableLiveData<PublicLedgerResponseDTO> publicLedgerResponseObservable = new MutableLiveData<>();
    private final static MutableLiveData<ErrorResponseDTO> mErrorResponseObservable = new MutableLiveData<>();

    /**
     * Constructor
     *
     * @param application The Application of package.
     */
    public PublicLedgerViewModel(@NonNull Application application) {
        super(application);

        // Object box database.
        BoxStore userBox = ((App) getApplication().getApplicationContext()).getStore();
        Box<UserLoginData> box = userBox.boxFor(UserLoginData.class);

        // loads User token from objectBox
        List<UserLoginData> users = box.getAll();
        String token = users.get(0).getToken();
        String bearerToken = "Bearer " + token;

        // Create an instance of public ledger repository for network request.
        PublicLedgerRepository.getInstance().getPublicLedgerRepository(bearerToken);
    }

    /**
     * Create a LiveData instance of PublicLedgerResponseDTO object.
     *
     * @return a LiveData instance of PublicLedgerResponseDTO.
     */
    public static MutableLiveData<PublicLedgerResponseDTO> getPublicLedgerResponseObservable() {
        return publicLedgerResponseObservable;
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
