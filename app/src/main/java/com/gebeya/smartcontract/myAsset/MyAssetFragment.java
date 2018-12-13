package com.gebeya.smartcontract.myAsset;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gebeya.framework.base.BaseFragment;
import com.gebeya.framework.utils.Api;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.dto.UserResponseDTO;
import com.gebeya.smartcontract.data.model.LoginRequest;
import com.gebeya.smartcontract.myAsset.api.service.MyAssetService;
import com.gebeya.smartcontract.myAsset.api.service.UserService;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAssetFragment extends BaseFragment {

    @BindView(R.id.rvMyAsset)
    RecyclerView mRecyclerView;

    @BindView(R.id.pvMyAsset)
    CircularProgressView progressView;

    private RecyclerView.LayoutManager mLayoutManager;
    private MyAssetService mMyAssetService;
    private MyAssetAdapter mMyAssetAdapter;
    private UserService mUserService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyAssetService = Api.getMyAssetService();
        mUserService = Api.getUserService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        inflate(R.layout.fragment_my_asset, container);

        mMyAssetAdapter = new MyAssetAdapter(getActivity(),
              new UserResponseDTO());

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMyAssetAdapter);
        mRecyclerView.setHasFixedSize(true);

        loadMyAsset();


        return root;
    }

    private void loadMyAsset() {

        LoginRequest loginRequest = new LoginRequest("0922847962","P@ssW0rd");

        mUserService.getUser("TZOSnu5qMcfUm1+Y/CNT+TaoBECTSEA=","application/json",
              loginRequest)
              .enqueue(new Callback<UserResponseDTO>() {
                  @Override
                  public void onResponse(Call<UserResponseDTO> call,
                                         Response<UserResponseDTO> response) {
                      if (response.isSuccessful()) {
                          d("My assets are loaded from API");
                          UserResponseDTO userResponseDTO = response.body();

                          String token = userResponseDTO.getToken();
                          toast(token);
                          //mMyAssetService.
                      } else {
                          e("Response was not successful");
                          int statusCode = response.code();
                          e("Response code: " + statusCode);
                      }
                  }

                  @Override
                  public void onFailure(Call<UserResponseDTO> call, Throwable t) {
                      d("My assets Fragment error loading from API");
                      t.printStackTrace();
                  }
              });
    }
}
