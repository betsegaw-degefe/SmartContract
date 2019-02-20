package com.gebeya.smartcontract.view.myAsset;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gebeya.framework.base.BaseFragment;
import com.gebeya.framework.utils.CheckInternetConnection;
import com.gebeya.app.App;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.databinding.FragmentMyAssetBinding;
import com.gebeya.smartcontract.view.login.LoginActivity;
import com.gebeya.smartcontract.model.data.dto.CarDTO;
import com.gebeya.smartcontract.model.data.dto.HouseDTO;
import com.gebeya.smartcontract.model.data.dto.UserLoginResponseDTO;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.view.makeTransaction.MakeTransactionActivity;
import com.gebeya.smartcontract.viewmodel.myAsset.MyAssetViewModel;
import com.gebeya.smartcontract.viewmodel.publicLedger.PublicLedgerViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class MyAssetFragment extends BaseFragment {

    // binding class for MyAssetLayout
    FragmentMyAssetBinding binding;

    private RecyclerView.LayoutManager layoutManager;
    private MyAssetAdapter mMyAssetAdapter;
    MyAssetViewModel viewModel;

    BoxStore userBox;
    Box<UserLoginData> box;

    // Intent Key name space holders.
    private static final String KEY_ASSET_ID = "ASSET_ID";
    private static final String KEY_ASSET_TYPE = "ASSET_TYPE";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the binding layout ui.
        binding = DataBindingUtil.inflate(
              inflater, R.layout.fragment_my_asset, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        // call the function which set the recycler view.
        setupRecyclerView();

        // No Asset Message.
        binding.noAsset.setVisibility(View.INVISIBLE);

        // Retrieve the Box for the UserLogin.
        userBox = ((App) Objects.requireNonNull(getActivity()).getApplicationContext()).getStore();
        box = userBox.boxFor(UserLoginData.class);

        // Providing view model class to MyAssetFragment.
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
              .get(MyAssetViewModel.class);

        // Observe the change in MyAsset view model.
        observeViewModel();

        // Observe the error from the MyAssetViewModel.
        observeErrorViewModel();

        // Swipe down refresh listener.
        binding.myAssetSwipeContainer.setOnRefreshListener(this::refreshMyAsset);

    }

    /**
     * Set the recycler view of My Asset.
     */
    private void setupRecyclerView() {
        // Initializing the adapter.
        mMyAssetAdapter = new MyAssetAdapter(getActivity(),
              new ArrayList<>(0),
              new ArrayList<>(0),
              new UserLoginResponseDTO(),
              (position, id, typeOfAsset) -> {
                  Intent intent = new Intent(getActivity(), MakeTransactionActivity.class);
                  intent.putExtra(KEY_ASSET_ID, id);
                  intent.putExtra(KEY_ASSET_TYPE, typeOfAsset);

                  startActivity(intent);
              });

        layoutManager = new LinearLayoutManager(getActivity());
        binding.rvMyAsset.setLayoutManager(layoutManager);
        binding.rvMyAsset.setAdapter(mMyAssetAdapter);
        binding.rvMyAsset.setHasFixedSize(true);
    }

    /**
     * Check connection.
     *
     * @return true: if there is connection; false: if there is no connection.
     */
    private boolean isConnected() {
        return new CheckInternetConnection().CheckInternetConnection(getContext());
    }

    /**
     * Called When user swipe down the myAsset fragment.
     */
    private void refreshMyAsset() {
        // Load assets from the server.
        if (isConnected()) {
            // Observe the change in my Asset view model.
            viewModel = new MyAssetViewModel(Objects.requireNonNull(getActivity()).getApplication());
            observeViewModel();
            observeErrorViewModel();
        } else {
            Snackbar.make(binding.myAssetRelativeLayout, R.string.NoInternetConnectionLabel, Snackbar.LENGTH_SHORT)
                  .show();
            binding.pvMyAsset.setVisibility(View.GONE);
            binding.myAssetSwipeContainer.setRefreshing(false);
        }

        // Color's for circular progressive view.
        binding.myAssetSwipeContainer.setColorSchemeResources(
              R.color.colorPrimary,
              R.color.colorPrimaryDark,
              R.color.ruby_dark);
    }

    /**
     * Observe the change in MyAsset view model.
     */
    private void observeViewModel() {
        if (isConnected()) {
            AtomicReference<Boolean> isCarNull = new AtomicReference<>(true);
            AtomicReference<Boolean> isHouseNull = new AtomicReference<>(true);

            MyAssetViewModel.getMyAssetCarResponseObservable()
                  .observe(this, myAssetCarResponseDTO -> {
                      if (myAssetCarResponseDTO != null &&
                            myAssetCarResponseDTO.data.size() != 0) {

                          isCarNull.set(false);
                          List<CarDTO> checkingAssetNull = myAssetCarResponseDTO.getData();
                          List<CarDTO> assetList = new ArrayList<>();

                          // Check whether transactions have a null field or not
                          // if null field found then the transaction removed from the list.
                          for (int i = 0; i < checkingAssetNull.size(); i++) {
                              if (checkingAssetNull.get(i).getModel() != null) {
                                  assetList.add(checkingAssetNull.get(i));
                              }
                          }
                          mMyAssetAdapter.updateMyAssetCar(assetList);
                          binding.pvMyAsset.setVisibility(View.GONE);
                          binding.noAsset.setVisibility(View.GONE);
                          binding.myAssetSwipeContainer.setRefreshing(false);
                      }
                  });

            MyAssetViewModel.getMyAssetHouseResponseObservable()
                  .observe(this, myAssetHouseResponseDTO -> {
                      if (myAssetHouseResponseDTO != null &&
                            myAssetHouseResponseDTO.getHouseData().size() != 0) {

                          isHouseNull.set(false);
                          List<HouseDTO> checkingAssetNull = myAssetHouseResponseDTO.getHouseData();
                          List<HouseDTO> houseList = new ArrayList<>();

                          // Check whether transactions have a null field or not
                          // if null field found then the transaction removed from the list.
                          for (int i = 0; i < checkingAssetNull.size(); i++) {
                              if (checkingAssetNull.get(i).getAreaM2() != null) {
                                  houseList.add(checkingAssetNull.get(i));
                              }
                          }
                          mMyAssetAdapter.updateMyAssetHouse(houseList);
                          binding.pvMyAsset.setVisibility(View.GONE);
                          binding.noAsset.setVisibility(View.GONE);
                          binding.myAssetSwipeContainer.setRefreshing(false);
                      }
                  });
            if (isCarNull.get() && isHouseNull.get()) {
                binding.noAsset.setVisibility(View.VISIBLE);
                binding.pvMyAsset.setVisibility(View.GONE);
            }
        } else {
            binding.noAsset.setVisibility(View.VISIBLE);
            Snackbar.make(binding.myAssetRelativeLayout, R.string.NoInternetConnectionLabel, Snackbar.LENGTH_SHORT)
                  .show();
            binding.pvMyAsset.setVisibility(View.GONE);
        }
    }

    /**
     * Observe the ErrorModelDTO object's change. If there is, This function triggered.
     */
    private void observeErrorViewModel() {
        // Set observable on the ErrorResponseDTO and triggered when the change happen.
        PublicLedgerViewModel.getErrorResponseObservable()
              .observe(this, errorResponseDTO -> {
                  errorResponseDTO = PublicLedgerViewModel.getErrorResponseObservable().getValue();
                  String message = Objects.requireNonNull(errorResponseDTO).getMessage();
                  if (message.equals("Authorization Header Token is Invalid")) {
                      startActivity(new Intent(getActivity(), LoginActivity.class));
                      Objects.requireNonNull(getActivity()).finish();
                  }
              });
    }
}
