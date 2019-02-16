package com.gebeya.smartcontract.view.publicLedger.transactionDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.Api;
import com.gebeya.framework.utils.CheckInternetConnection;
import com.gebeya.framework.utils.ErrorUtils;
import com.gebeya.smartcontract.App;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.model.data.dto.CarHistoryTransactionBodyDTO;
import com.gebeya.smartcontract.model.data.dto.CarTransactionDTO;
import com.gebeya.smartcontract.model.data.dto.CarTransactionHistoryResponseDTO;
import com.gebeya.smartcontract.model.data.dto.ErrorResponseDTO;
import com.gebeya.smartcontract.model.data.dto.HouseHistoryTransactionBodyDTO;
import com.gebeya.smartcontract.model.data.dto.HouseTransactionDTO;
import com.gebeya.smartcontract.model.data.dto.HouseTransactionHistoryResponseDTO;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.myAsset.SlidingImageAdapter;
import com.gebeya.smartcontract.view.publicLedger.api.service.CarTransactionHistoryDetailService;
import com.gebeya.smartcontract.view.publicLedger.api.service.HouseTransactionHistoryDetailService;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gebeya.framework.utils.Constants.CONTENT_TYPE;

public class TransactionDetailActivity extends BaseActivity {

    @BindView(R.id.transactionDetailRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.transactionDetailSwipeContainer)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.progressViewTransactionDetail)
    CircularProgressView progressView;

    @BindView(R.id.tvTransactionHistoryAssetSpecification)
    TextView brand;

    @BindView(R.id.transactionHistoryAssetType)
    TextView model;

    @BindView(R.id.transactionHistoryViewPager)
    ViewPager mViewPager;

    @BindView(R.id.houseTransactionHistoryViewPager)
    ViewPager mHouseViewPager;

    @BindView(R.id.includeCarHistory)
    View includeCarHisttory;

    @BindView(R.id.includeHouseHistory)
    View includeHouseHistory;

    @BindView(R.id.indicator)
    CirclePageIndicator indicator;

    @BindView(R.id.houseImageIndicator)
    CirclePageIndicator houseImageIndicator;

    @BindView(R.id.tvLocationx1)
    TextView mCoordinate1;

    @BindView(R.id.tvLocationx2)
    TextView mCoordinate2;

    @BindView(R.id.tvLocationx3)
    TextView mCoordinate3;

    @BindView(R.id.tvLocationx4)
    TextView mCoordinate4;

    @BindView(R.id.transactionHistoryHouseArea)
    TextView houseArea;


    private RecyclerView.LayoutManager mLayoutManager;
    private CarTransactionHistoryDetailService mCarTransactionHistoryDetailService;
    private HouseTransactionHistoryDetailService mHouseTransactionHistoryDetailService;

    private TransactionDetailAdapter mTransactionDetailAdapter;
    private String assetId;
    private String assetType;
    private boolean isConnected;

    BoxStore userBox;
    Box<UserLoginData> box;
    String bearerToken;

    // Intent Key name space holders.
    private static final String KEY_ASSET_ID = "ASSET_ID";
    private static final String KEY_ASSET_TYPE = "ASSET_TYPE";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_transactions_detail);
        bind();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Create the retrofit client service for the public ledger.
        mCarTransactionHistoryDetailService = Api.carTransactionHistoryDetailService();
        mHouseTransactionHistoryDetailService = Api.houseTransactionHistoryDetailService();


        // Extracting the Asset Id which is passed from publicLedgerFragment.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            assetId = intent.getExtras().getString(KEY_ASSET_ID);
            assetType = intent.getExtras().getString(KEY_ASSET_TYPE);
        }

        // Initializing the adapter.
        mTransactionDetailAdapter = new TransactionDetailAdapter(this,
              new ArrayList<>(0),
              new ArrayList<>(0),
              (position) -> {
              });

        // Retrieve the Box for the UserLogin
        userBox = ((App) getApplication()).getStore();
        box = userBox.boxFor(UserLoginData.class);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mTransactionDetailAdapter);
        mRecyclerView.setHasFixedSize(true);

        // loads User token from objectBox
        List<UserLoginData> users = box.getAll();
        String token = users.get(0).getToken();
        bearerToken = "Bearer " + token;

        // Check connection.
        isConnected = new CheckInternetConnection().CheckInternetConnection(getApplicationContext());

        if (isConnected) {
            if (assetType.equals("House Transaction")) {
                loadHouseTransactionDetail();
            } else if (assetType.equals("Car Transaction")) {
                loadCarTransactionDetail();
            }
        } else {
            toast("No Internet Connection");
            progressView.setVisibility(View.GONE);
            swipeContainer.setRefreshing(false);
        }

        swipeContainer.setOnRefreshListener(() -> {
            if (isConnected) {
                loadCarTransactionDetail();
            } else {
                toast("No Internet Connection");
                progressView.setVisibility(View.GONE);
                swipeContainer.setRefreshing(false);
            }
        });
        swipeContainer.setColorSchemeResources(
              R.color.colorPrimary,
              R.color.colorPrimaryDark,
              R.color.ruby_dark);
    }

    private void loadCarTransactionDetail() {

        CarHistoryTransactionBodyDTO carHistoryTransactionBodyDTO
              = new CarHistoryTransactionBodyDTO();
        carHistoryTransactionBodyDTO.setCarId(assetId);

        mCarTransactionHistoryDetailService.getCarTransactionHistory(bearerToken,
              CONTENT_TYPE, carHistoryTransactionBodyDTO)
              .enqueue(new Callback<CarTransactionHistoryResponseDTO>() {
                  @Override
                  public void onResponse(Call<CarTransactionHistoryResponseDTO> call,
                                         Response<CarTransactionHistoryResponseDTO> response) {
                      if (response.isSuccessful()) {

                          // The two Car transaction DTO variables are one is for holding
                          // Response from the network. after checking whether the response
                          // from the network is null or not we use the second variable for
                          // holding the non null variable.
                          List<CarTransactionDTO> checkingTransactionNull = response.body().getData();
                          List<CarTransactionDTO> transactions = new ArrayList<>();

                          // Check whether transactions have a null field or not
                          // if null field found then the transaction removed from the list.
                          for (int i = 0; i < checkingTransactionNull.size(); i++) {
                              if (checkingTransactionNull.get(i).getCar() != null &&
                                    checkingTransactionNull.get(i).getFrom() != null &&
                                    checkingTransactionNull.get(i).getTo() != null) {

                                  transactions.add(checkingTransactionNull.get(i));
                              }
                          }
                          updateCarLayoutData(transactions.get(0));
                          mTransactionDetailAdapter.updateCarTransactions(transactions);
                          progressView.setVisibility(View.GONE);
                          swipeContainer.setRefreshing(false);


                      } else if (response.errorBody() != null) {
                          String message2 = response.message();
                          Integer code = response.code();
                          ErrorResponseDTO errorResponse = ErrorUtils.parseError(response);
                          String message = errorResponse.getMessage();
                          Integer errorCode = errorResponse.getStatus();
                      }
                  }

                  @Override
                  public void onFailure(Call<CarTransactionHistoryResponseDTO> call,
                                        Throwable t) {
                      toast("failed");
                      d("----------------------------Transaction failed to send to API-------------------------------------");
                      t.printStackTrace();
                  }
              });
    }


    /**
     * update the car asset detail
     *
     * @param transaction: the detail of the car object.
     */
    private void updateCarLayoutData(CarTransactionDTO transaction) {
        // set the brand of the car in the layout.
        brand.setText(transaction.getCar().getBrand());

        // Set the model of the car.
        String carModel = transaction.getCar().getModel();
        String year = String.valueOf(transaction.getCar().getYearOfManufactured());
        model.setText(String.format("%s %s", carModel, year));

        // Set the picture of the car in the layout.
        List<List<String>> pictures = transaction.getCar().getPictures();
        String[] url = new String[pictures.size()];
        if (!pictures.isEmpty()) {
            int i = 0;
            for (List<String> innerList : pictures) {
                for (String imageUrl : innerList) {
                    url[i] = imageUrl;
                    i++;
                }
            }
            SlidingImageAdapter adapter = new SlidingImageAdapter(getApplicationContext(), url);
            mViewPager.setAdapter(adapter);
            indicator.setViewPager(mViewPager);
            includeCarHisttory.setVisibility(View.VISIBLE);
        }

    }

    private void loadHouseTransactionDetail() {
        HouseHistoryTransactionBodyDTO houseHistoryTransactionBodyDTO
              = new HouseHistoryTransactionBodyDTO();
        houseHistoryTransactionBodyDTO.setHouseId(assetId);

        mHouseTransactionHistoryDetailService.getHouseTransactionHistory(bearerToken,
              CONTENT_TYPE, houseHistoryTransactionBodyDTO)
              .enqueue(new Callback<HouseTransactionHistoryResponseDTO>() {
                  @Override
                  public void onResponse(Call<HouseTransactionHistoryResponseDTO> call,
                                         Response<HouseTransactionHistoryResponseDTO> response) {

                      if (response.isSuccessful()) {

                          List<HouseTransactionDTO> checkingTransactionNull = response.body().getData();
                          List<HouseTransactionDTO> transactions = new ArrayList<>();

                          // Check whether transactions have a null field or not
                          // if null field found then the transaction removed from the list.
                          for (int i = 0; i < checkingTransactionNull.size(); i++) {
                              if (checkingTransactionNull.get(i).getHouse() != null &&
                                    checkingTransactionNull.get(i).getFrom() != null &&
                                    checkingTransactionNull.get(i).getTo() != null) {

                                  transactions.add(checkingTransactionNull.get(i));
                              }
                          }

                          updateHouseLayoutData(transactions.get(0));
                          mTransactionDetailAdapter.updaterHouseTransactions(transactions);
                          progressView.setVisibility(View.GONE);
                          swipeContainer.setRefreshing(false);

                      } else if (response.errorBody() != null) {
                          String message2 = response.message();
                          Integer code = response.code();
                          ErrorResponseDTO errorResponse = ErrorUtils.parseError(response);
                          String message = errorResponse.getMessage();
                          Integer errorCode = errorResponse.getStatus();
                      }
                  }

                  @Override
                  public void onFailure(Call<HouseTransactionHistoryResponseDTO> call, Throwable t) {
                      toast("failed");
                      Log.d(this.getClass().getName(), "-----------------------------------------Request failed to send to API-------------------------------------------");
                      t.printStackTrace();
                  }
              });
    }

    /**
     * update the car asset detail
     *
     * @param transaction: the detail of the car object.
     */
    private void updateHouseLayoutData(HouseTransactionDTO transaction) {

        // Setting the picture of the house in the layout.
        List<List<String>> pictures = transaction.getHouse().getPictures();
        String[] url = new String[pictures.size()];
        if (!pictures.isEmpty()) {
            int i = 0;
            for (List<String> innerList : pictures) {
                for (String imageUrl : innerList) {
                    url[i] = imageUrl;
                    i++;
                }
            }
            SlidingImageAdapter adapter = new SlidingImageAdapter(getApplicationContext(), url);
            mHouseViewPager.setAdapter(adapter);
            houseImageIndicator.setViewPager(mViewPager);
            includeHouseHistory.setVisibility(View.VISIBLE);
        }

        // Extracting and setting the coordinate location of the house
        List<List<Double>> location = transaction.getHouse().getGeoLocation();
        Double[] coordinates = new Double[location.size()];
        if (!location.isEmpty()) {
            int i = 0;
            for (List<Double> innerList : location) {
                for (Double coordinate : innerList) {
                    coordinates[i] = coordinate;
                    i++;
                }
            }
            mCoordinate1.setText(String.format(Locale.getDefault(), "[%f,", coordinates[0]));
            mCoordinate2.setText(String.format(Locale.getDefault(), "%f,", coordinates[1]));
            mCoordinate3.setText(String.format(Locale.getDefault(), "%f,", coordinates[2]));
            mCoordinate4.setText(String.format(Locale.getDefault(), "%f]", coordinates[3]));
        }

        // Setting the area of the house.
        Integer area = transaction.getHouse().getAreaM2();
        String areaUnit = String.format("%o %s", area, "Square meter.");
        houseArea.setText(areaUnit);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }
}
