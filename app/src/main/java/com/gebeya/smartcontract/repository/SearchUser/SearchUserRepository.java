package com.gebeya.smartcontract.repository.SearchUser;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.gebeya.framework.utils.Api;
import com.gebeya.smartcontract.model.data.dto.SearchUserBodyDTO;
import com.gebeya.smartcontract.model.data.dto.UserDTO;
import com.gebeya.smartcontract.model.data.dto.UserResponseDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gebeya.framework.utils.Constants.CONTENT_TYPE;

public class SearchUserRepository {

    private String TAG = SearchUserRepository.class.getName();
    private SearchUserService mSearchUserService;
    private static SearchUserRepository INSTANCE;

    /**
     * Create an Instance of this class when the object of this class created.
     *
     * @return Instance of this class.
     */
    public static SearchUserRepository getInstance() {
        //return SingletonHelper.INSTANCE;
        if (INSTANCE == null) {
            INSTANCE = new SearchUserRepository();
        }
        return INSTANCE;
    }

    /**
     * Create the retrofit service client for this class.
     */
    private SearchUserRepository() {
        mSearchUserService = Api.searchUserService();
    }

    /**
     * Asynchronous request to the network for search user.
     *
     * @param bearerToken:       user token
     * @param searchUserBodyDTO: search query
     * @return list of user that match the search query.
     */
    public LiveData<ArrayList<UserDTO>> getSearchUserRepository(String bearerToken,
                                                               SearchUserBodyDTO searchUserBodyDTO) {

        // mutable data object for list of user.
        final MutableLiveData<ArrayList<UserDTO>> userListMutableLiveData
              = new MutableLiveData<>();

        // The actual Asynchronous request.
        mSearchUserService.searchUser(bearerToken, CONTENT_TYPE, searchUserBodyDTO)
              .enqueue(new Callback<ArrayList<UserDTO>>() {
                  @Override
                  public void onResponse(Call<ArrayList<UserDTO>> call,
                                         Response<ArrayList<UserDTO>> response) {
                      if (response.isSuccessful()) {
                          userListMutableLiveData.setValue(response.body());
                      }
                  }

                  @Override
                  public void onFailure(Call<ArrayList<UserDTO>> call,
                                        Throwable t) {
                      userListMutableLiveData.setValue(null);
                      Log.e(TAG, "User failed to send to API");
                      t.printStackTrace();
                  }
              });

        return userListMutableLiveData;
    }
}
