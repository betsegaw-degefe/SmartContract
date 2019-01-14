package com.gebeya.smartcontract.myAsset;

import android.content.Intent;
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
import com.gebeya.framework.utils.CheckInternetConnection;
import com.gebeya.smartcontract.App;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.dto.CarDTO;
import com.gebeya.smartcontract.data.dto.HouseDTO;
import com.gebeya.smartcontract.data.dto.MyAssetCarResponseDTO;
import com.gebeya.smartcontract.data.dto.MyAssetHouseResponseDTO;
import com.gebeya.smartcontract.data.dto.UserResponseDTO;
import com.gebeya.smartcontract.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.myAsset.api.service.MyAssetCarService;
import com.gebeya.smartcontract.myAsset.api.service.MyAssetHouseService;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import io.objectbox.Box;
import io.objectbox.BoxStore;
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
    private boolean isConnected;

    BoxStore userBox;
    Box<UserLoginData> box;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the Box for the UserLogin.
        userBox = ((App) Objects.requireNonNull(getActivity()).getApplicationContext()).getStore();
        box = userBox.boxFor(UserLoginData.class);

        // Create the retrofit client service.
        createService();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        inflate(R.layout.fragment_my_asset, container);


        // Initializing the adapter.
        mMyAssetAdapter = new MyAssetAdapter(getActivity(),
              new ArrayList<CarDTO>(0),
              new ArrayList<HouseDTO>(0),
              new UserResponseDTO(),
              new MyAssetCallback() {
                  @Override
                  public void onSelected(int position, String id) {
                     // toast("Selected position is: " + id);
                      // start make transaction activity.
                      Intent intent = new Intent(getActivity(), MakeTransactionActivity.class);
                      intent.putExtra("ASSET_ID", id);

                      startActivity(intent);
                  }
              });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMyAssetAdapter);
        mRecyclerView.setHasFixedSize(true);

        // Check connection.
        isConnected = new CheckInternetConnection().CheckInternetConnection(getContext());

        // Load assets from the server.
        if (isConnected) {
            loadMyAsset();
        } else {
            toast("No Internet Connection");
            progressView.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
        }


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Load assets from the server.
                if (isConnected) {
                    loadMyAsset();
                } else {
                    toast("No Internet Connection");
                    progressView.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(
              R.color.colorPrimary,
              R.color.colorPrimaryDark,
              R.color.ruby_dark);

        return root;
    }

    /**
     * Create the retrofit client service.
     */
    private void createService() {
        mMyAssetCarService = Api.getMyAssetCarService();
        mMyAssetHouseService = Api.getMyAssetHouseService();
    }


    /**
     * Load assets from the server.
     */
    private void loadMyAsset() {

        // loads User from objectBox
        List<UserLoginData> users = box.getAll();
        String token = users.get(0).getToken();
        String bearerToken = "Bearer " + token;
        String userId = users.get(0).getUserId();

        /**
         * Loading specific user car asset from getCarAsset of a user api end point.
         */
        mMyAssetCarService.getMyCarAsset(bearerToken,
              CONTENT_TYPE,
              userId).enqueue(new Callback<MyAssetCarResponseDTO>() {

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
         * Loading specific user's house asset from getHouseAssets of a user api end point.
         */
        mMyAssetHouseService.getMyHouseAsset(
              bearerToken, CONTENT_TYPE,
              userId).enqueue(new Callback<MyAssetHouseResponseDTO>() {
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
                    progressView.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<MyAssetHouseResponseDTO> call,
                                  Throwable t) {
                d("My assets Fragment error loading from API");
                t.printStackTrace();
                progressView.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
