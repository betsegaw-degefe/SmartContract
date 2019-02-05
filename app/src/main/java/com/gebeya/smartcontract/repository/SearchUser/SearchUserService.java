package com.gebeya.smartcontract.repository.SearchUser;

import com.gebeya.smartcontract.model.data.dto.SearchUserBodyDTO;
import com.gebeya.smartcontract.model.data.dto.UserDTO;
import com.gebeya.smartcontract.model.data.dto.UserResponseDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SearchUserService {

    @POST("/users/findUser")
    Call<ArrayList<UserDTO>> searchUser(
          @Header("Authorization") String token,
          @Header("Content-Type") String type,
          @Body SearchUserBodyDTO searchUserBodyDTO
    );
}
