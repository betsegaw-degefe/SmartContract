package com.gebeya.smartcontract.myAsset;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gebeya.framework.base.BaseFragment;
import com.gebeya.framework.utils.Api;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.dto.CarDTO;
import com.gebeya.smartcontract.data.dto.HouseDTO;
import com.gebeya.smartcontract.data.dto.MyAssetCarResponseDTO;
import com.gebeya.smartcontract.data.dto.MyAssetHouseResponseDTO;
import com.gebeya.smartcontract.data.dto.UserResponseDTO;
import com.gebeya.smartcontract.data.model.LoginRequest;
import com.gebeya.smartcontract.myAsset.api.service.MyAssetCarService;
import com.gebeya.smartcontract.myAsset.api.service.MyAssetHouseService;
import com.gebeya.smartcontract.myAsset.api.service.UserService;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gebeya.framework.utils.Constants.CONTENT_TYPE;

public class MyAssetFragment extends BaseFragment {

    @BindView(R.id.rvMyAsset)
    RecyclerView mRecyclerView;

    @BindView(R.id.pvMyAsset)
    CircularProgressView progressView;

    @BindView(R.id.myAssetSwipeContainer)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView.LayoutManager mLayoutManager;
    private MyAssetCarService mMyAssetCarService;
    private MyAssetHouseService mMyAssetHouseService;
    private MyAssetAdapter mMyAssetAdapter;
    private UserService mUserService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyAssetCarService = Api.getMyAssetCarService();
        mUserService = Api.getUserService();
        mMyAssetHouseService = Api.getMyAssetHouseService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        inflate(R.layout.fragment_my_asset, container);

        mMyAssetAdapter = new MyAssetAdapter(getActivity(),
              new ArrayList<CarDTO>(0),
              new ArrayList<HouseDTO>(0),
              new UserResponseDTO());

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMyAssetAdapter);
        mRecyclerView.setHasFixedSize(true);

        loadMyAsset();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMyAsset();
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(
              R.color.colorPrimary,
              R.color.colorPrimaryDark,
              R.color.ruby_dark);

        return root;
    }


    private void loadMyAsset() {

        LoginRequest loginRequest = new LoginRequest("0922847962", "P@ssW0rd");

        mUserService.getUser("application/json",
              loginRequest)
              .enqueue(new Callback<UserResponseDTO>() {

                  @Override
                  public void onResponse(Call<UserResponseDTO> call,
                                         Response<UserResponseDTO> response) {
                      if (response.isSuccessful()) {
                          d("My assets are loaded from API");
                          UserResponseDTO userResponseDTO = response.body();

                          String token = userResponseDTO.getToken();
                          String bearerToken = "Bearer " + token;
                          /**
                           * Loading specific user car asset from getCarAssetsofAUser api end point.
                           */
                          mMyAssetCarService.getMyCarAsset(bearerToken, CONTENT_TYPE,
                                "5bf505d1e0029e364679fab0")
                                .enqueue(new Callback<MyAssetCarResponseDTO>() {
                                    @Override
                                    public void onResponse(Call<MyAssetCarResponseDTO> call,
                                                           Response<MyAssetCarResponseDTO> response) {
                                        if (response.isSuccessful()) {
                                            MyAssetCarResponseDTO myAssetResponseDTO = response.body();
                                            List<CarDTO> carList = myAssetResponseDTO.getData();
                                            d("----------------------------Assets loaded: " + carList.size());
                                            mMyAssetAdapter.updateMyAssetCar(response.body().getData());
                                        } else {
                                            e("Response was not successful");
                                            int statusCode = response.code();
                                            e("Response code: " + statusCode);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MyAssetCarResponseDTO> call,
                                                          Throwable t) {
                                        d("My assets Fragment error loading from API");
                                        t.printStackTrace();
                                    }
                                });
                          /**
                           * Loading specific user house asset from getHouseAssetsofAUser api end point.
                           */
                          mMyAssetHouseService.getMyHouseAsset(bearerToken, CONTENT_TYPE,
                                "5bf505d1e0029e364679fab0").enqueue(new Callback<MyAssetHouseResponseDTO>() {
                              @Override
                              public void onResponse(Call<MyAssetHouseResponseDTO> call,
                                                     Response<MyAssetHouseResponseDTO> response) {
                                  if (response.isSuccessful()) {
                                      MyAssetHouseResponseDTO myAssetHouseResponseDTO = response.body();
                                      List<HouseDTO> houseList = myAssetHouseResponseDTO.getHouseData();
                                      d("------------House assets loaded: " + houseList.size());
                                      mMyAssetAdapter.updateMyAssetHouse(response.body().getHouseData());
                                      progressView.setVisibility(View.GONE);
                                      mSwipeRefreshLayout.setRefreshing(false);
                                  } else {
                                      e("Response was not successful");
                                      int statusCode = response.code();
                                      e("Response code: " + statusCode);
                                  }
                              }

                              @Override
                              public void onFailure(Call<MyAssetHouseResponseDTO> call,
                                                    Throwable t) {
                                  d("My assets Fragment error loading from API");
                                  t.printStackTrace();
                              }
                          });
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
