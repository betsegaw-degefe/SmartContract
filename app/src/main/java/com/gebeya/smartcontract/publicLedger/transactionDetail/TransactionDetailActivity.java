package com.gebeya.smartcontract.publicLedger.transactionDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.Api;
import com.gebeya.framework.utils.CheckInternetConnecction;
import com.gebeya.smartcontract.App;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.dto.PublicLedgerResponseDTO;
import com.gebeya.smartcontract.data.dto.TransactionDTO;
import com.gebeya.smartcontract.data.dto.TransactionDetailResponseDTO;
import com.gebeya.smartcontract.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.publicLedger.PublicLedgerAdapter;
import com.gebeya.smartcontract.publicLedger.api.service.TransactionDetailService;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.List;

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

    private RecyclerView.LayoutManager mLayoutManager;
    private TransactionDetailService mTransactionDetailService;
    private PublicLedgerAdapter mPublicLedgerAdapter;
    private String AssetId;
    private boolean isConnected;

    BoxStore userBox;
    Box<UserLoginData> box;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_transactions_detail);
        bind();

        // Create the retrofit client service for the public ledger.
        mTransactionDetailService = Api.transactionDetailService();

        // Extracting the Asset Id which is passed from publicLedgerFragment.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            AssetId = intent.getExtras().getString("ASSET_ID");
            toast(AssetId);
        }

        // Retrieve the Box for the UserLogin
        userBox = ((App) getApplication()).getStore();
        box = userBox.boxFor(UserLoginData.class);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mPublicLedgerAdapter);
        mRecyclerView.setHasFixedSize(true);

        // Check connection.
        isConnected = new CheckInternetConnecction().CheckInternetConnecction(getApplicationContext());

        if (isConnected) {
            loadTransactionDetail();
        } else {
            toast("No Internet Connection");
            progressView.setVisibility(View.GONE);
            swipeContainer.setRefreshing(false);
        }

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isConnected) {
                    loadTransactionDetail();
                } else {
                    toast("No Internet Connection");
                    progressView.setVisibility(View.GONE);
                    swipeContainer.setRefreshing(false);
                }
            }
        });
        swipeContainer.setColorSchemeResources(
              R.color.colorPrimary,
              R.color.colorPrimaryDark,
              R.color.ruby_dark);
    }

    private void loadTransactionDetail() {

        // loads User token from objectBox
        List<UserLoginData> users = box.getAll();
        String token = users.get(0).getToken();
        String bearerToken = "Bearer " + token;

        mTransactionDetailService.getTransactionHistory(
              bearerToken,
              CONTENT_TYPE,
              AssetId
        ).enqueue(new Callback<TransactionDetailResponseDTO>() {
            @Override
            public void onResponse(Call<TransactionDetailResponseDTO>
                                         call, Response<TransactionDetailResponseDTO> response) {
                if (response.isSuccessful()) {
                    d("TransactionDetailActivity are loaded from API");

                   /* TransactionDetailResponseDTO transactionDetailResponse = response.body();
                    //List<TransactionDetailResponseDTO> transactions = transactionDetailResponse;
                    // d("Transactions loaded: " + transactions.size());
                    if (!transactionDetailResponse.getId().isEmpty()) {
                        mPublicLedgerAdapter.updateTransactions(response.body().getData());
                    } else {
                        mNoPublicLedger.setVisibility(View.VISIBLE);
                    }
                    progressView.setVisibility(View.GONE);
                    swipeContainer.setRefreshing(false);*/
                } else {
                    e("Response was not successful");
                    int statusCode = response.code();
                    e("Response code: " + statusCode);
                    progressView.setVisibility(View.GONE);
                    swipeContainer.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<TransactionDetailResponseDTO>
                                        call, Throwable t) {

            }
        });
    }
}
