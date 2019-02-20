package com.gebeya.smartcontract.repository.myAsset;

import com.gebeya.framework.utils.Api;
import com.gebeya.framework.utils.ErrorUtils;
import com.gebeya.smartcontract.model.data.dto.ErrorResponseDTO;
import com.gebeya.smartcontract.model.data.dto.MyAssetCarResponseDTO;
import com.gebeya.smartcontract.model.data.dto.MyAssetHouseResponseDTO;
import com.gebeya.smartcontract.repository.myAsset.service.MyAssetCarService;
import com.gebeya.smartcontract.repository.myAsset.service.MyAssetHouseService;
import com.gebeya.smartcontract.viewmodel.myAsset.MyAssetViewModel;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gebeya.framework.utils.Constants.CONTENT_TYPE;

/**
 * This class provide Singleton network request for hitting API
 * and using LiveData for observing api response.
 */
public class MyAssetRepository {

    private MyAssetCarService mMyAssetCarService;
    private MyAssetHouseService mMyAssetHouseService;
    private static MyAssetRepository INSTANCE;

    /**
     * called when the instance of the MyAssetRepository class needed.
     *
     * @return singletonHelper instance class.
     */
    public static MyAssetRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MyAssetRepository();
        }
        return INSTANCE;
    }

    /**
     * create the retrofit service client for this class.
     */
    private MyAssetRepository() {
        mMyAssetCarService = Api.getMyAssetCarService();
        mMyAssetHouseService = Api.getMyAssetHouseService();
    }

    public void getMyAssetRepository(String bearerToken, String userId) {

        // Request for Car Asset
        mMyAssetCarService.getMyCarAsset(bearerToken,
              CONTENT_TYPE,
              userId).enqueue(new Callback<MyAssetCarResponseDTO>() {
            @Override
            public void onResponse(Call<MyAssetCarResponseDTO> call, Response<MyAssetCarResponseDTO> response) {
                if (response.isSuccessful() && Objects.requireNonNull(response.body()).data.size() != (0)) {
                    MyAssetViewModel.getMyAssetCarResponseObservable().setValue(response.body());
                } else if (response.errorBody() != null) {
                    ErrorResponseDTO errorResponse = ErrorUtils.parseError(response);
                    MyAssetViewModel.getErrorResponseObservable().setValue(errorResponse);
                } else {
                    MyAssetViewModel.getMyAssetCarResponseObservable().setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MyAssetCarResponseDTO> call, Throwable t) {
                MyAssetViewModel.getMyAssetCarResponseObservable().setValue(null);
            }
        });

        // Request for House Asset
        mMyAssetHouseService.getMyHouseAsset(bearerToken,
              CONTENT_TYPE,
              userId).enqueue(new Callback<MyAssetHouseResponseDTO>() {
            @Override
            public void onResponse(Call<MyAssetHouseResponseDTO> call, Response<MyAssetHouseResponseDTO> response) {
                if (response.isSuccessful()) {
                    MyAssetViewModel.getMyAssetHouseResponseObservable().setValue(response.body());
                } else if (response.errorBody() != null) {
                    ErrorResponseDTO errorResponse = ErrorUtils.parseError(response);
                    MyAssetViewModel.getErrorResponseObservable().setValue(errorResponse);
                } else {
                    MyAssetViewModel.getMyAssetHouseResponseObservable().setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MyAssetHouseResponseDTO> call, Throwable t) {
                MyAssetViewModel.getMyAssetHouseResponseObservable().setValue(null);
            }
        });
    }
}
