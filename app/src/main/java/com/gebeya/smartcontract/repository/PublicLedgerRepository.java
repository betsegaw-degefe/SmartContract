package com.gebeya.smartcontract.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.gebeya.framework.utils.Api;
import com.gebeya.framework.utils.ErrorUtils;
import com.gebeya.smartcontract.model.data.dto.ErrorResponseDTO;
import com.gebeya.smartcontract.model.data.dto.PublicLedgerResponseDTO;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.view.publicLedger.api.service.PublicLedgerService;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gebeya.framework.utils.Constants.CONTENT_TYPE;

/**
 * This class provide Singleton network request for hitting API
 * and using LiveData for observing api response.
 */
public class PublicLedgerRepository {

    private PublicLedgerService mPublicLedgerService;

    // declaring for object box variables.
    BoxStore userBox;
    Box<UserLoginData> box;

    private static PublicLedgerRepository  INSTANCE;

    /**
     * called when the instance of the PublicLedgerRepository class needed.
     *
     * @return singletonHelper instance class.
     */
    public static PublicLedgerRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PublicLedgerRepository();
        }
        return INSTANCE;
    }

    /**
     * create the retrofit service client for this class.
     */
    private PublicLedgerRepository() {
        mPublicLedgerService = Api.getPublicLedgerService();
    }

    /**
     *
     * @param bearerToken : The user bearer token.
     * @return :
     */
    public LiveData<PublicLedgerResponseDTO> getPublicLedgerRepository(String bearerToken) {


        final MutableLiveData<PublicLedgerResponseDTO> publicLedgerResponse
              = new MutableLiveData<>();

        mPublicLedgerService.getLedger(bearerToken, CONTENT_TYPE)
              .enqueue(new Callback<PublicLedgerResponseDTO>() {

                  @Override
                  public void onResponse(Call<PublicLedgerResponseDTO> call,
                                         Response<PublicLedgerResponseDTO> response) {
                      if (response.isSuccessful())
                          publicLedgerResponse.setValue(response.body());

                  }

                  @Override
                  public void onFailure(Call<PublicLedgerResponseDTO> call,
                                        Throwable t) {
                      publicLedgerResponse.setValue(null);
                  }
              });
        return publicLedgerResponse;
    }
}
