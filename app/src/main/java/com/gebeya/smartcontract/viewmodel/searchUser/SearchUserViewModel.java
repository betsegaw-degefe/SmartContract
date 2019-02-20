package com.gebeya.smartcontract.viewmodel.searchUser;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.gebeya.app.App;
import com.gebeya.smartcontract.model.data.dto.FirstName;
import com.gebeya.smartcontract.model.data.dto.SearchUserBodyDTO;
import com.gebeya.smartcontract.model.data.dto.UserDTO;
import com.gebeya.smartcontract.model.data.model.SearchUser;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.repository.SearchUser.SearchUserRepository;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class SearchUserViewModel extends AndroidViewModel {

    private LiveData<ArrayList<UserDTO>> mUserListLiveData;
    ArrayList<UserDTO> mUserDTOS;

    public MutableLiveData<String> firstName = new MutableLiveData<>();

    private MutableLiveData<SearchUser> mSearchUserMutableLiveData = new MutableLiveData<>();
    private SearchUserBodyDTO mSearchUserBodyDTO = new SearchUserBodyDTO();
    private FirstName mFirstName = new FirstName();


    BoxStore userBox;
    Box<UserLoginData> box;

    /**
     * Constructor.
     *
     * @param application The Application of package.
     */

    public SearchUserViewModel(@NonNull Application application) {
        super(application);

        userBox = ((App) getApplication().getApplicationContext()).getStore();
        box = userBox.boxFor(UserLoginData.class);

        // loads User token from objectBox
        List<UserLoginData> users = box.getAll();
        String token = users.get(0).getToken();
        String bearerToken = "Bearer " + token;

        //SearchUser searchUser = new SearchUser(firstName.getValue());
        SearchUser searchUser = new SearchUser("");


        mSearchUserMutableLiveData.setValue(searchUser);

        mFirstName.set$regex("");
        mSearchUserBodyDTO.setFirstName(mFirstName);

        // Send request to search user repository.
        mUserListLiveData = SearchUserRepository.getInstance().getSearchUserRepository(
              bearerToken, mSearchUserBodyDTO);
    }

    /**
     * Create a MutableLiveData instance of ChangePassword object.
     *
     * @return a MutableLiveData instance of ChangePassword.
     */
    public MutableLiveData<SearchUser> getSearchUser() {
        if (mSearchUserMutableLiveData == null) {
            mSearchUserMutableLiveData = new MutableLiveData<>();
        }
        return mSearchUserMutableLiveData;
    }

    /**
     * @return list of user.
     */
    public LiveData<ArrayList<UserDTO>> getUser() {
        return mUserListLiveData;
    }
}
