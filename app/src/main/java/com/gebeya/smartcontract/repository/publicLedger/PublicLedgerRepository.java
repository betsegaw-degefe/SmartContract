package com.gebeya.smartcontract.repository.publicLedger;

import com.gebeya.framework.utils.Api;
import com.gebeya.framework.utils.ErrorUtils;
import com.gebeya.smartcontract.model.data.dto.ErrorResponseDTO;
import com.gebeya.smartcontract.model.data.dto.PublicLedgerResponseDTO;
import com.gebeya.smartcontract.view.publicLedger.api.service.PublicLedgerService;
import com.gebeya.smartcontract.viewmodel.publicLedger.PublicLedgerViewModel;

import java.util.Objects;

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
    private static PublicLedgerRepository INSTANCE;


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
     * @param bearerToken : The user bearer token.
     */
    public void getPublicLedgerRepository(String bearerToken) {
        mPublicLedgerService.getLedger(bearerToken, CONTENT_TYPE)
              .enqueue(new Callback<PublicLedgerResponseDTO>() {

                  @Override
                  public void onResponse(Call<PublicLedgerResponseDTO> call,
                                         Response<PublicLedgerResponseDTO> response) {
                      if (response.isSuccessful() && Objects.requireNonNull(response.body()).data.size() != (0)) {
                          PublicLedgerViewModel.getPublicLedgerResponseObservable().setValue(response.body());
                      } else {
                          ErrorResponseDTO errorResponse = ErrorUtils.parseError(response);
                          PublicLedgerViewModel.getErrorResponseObservable().setValue(errorResponse);
                      }
                  }

                  @Override
                  public void onFailure(Call<PublicLedgerResponseDTO> call,
                                        Throwable t) {
                      PublicLedgerViewModel.getPublicLedgerResponseObservable().setValue(null);
                  }
              });
    }
}
