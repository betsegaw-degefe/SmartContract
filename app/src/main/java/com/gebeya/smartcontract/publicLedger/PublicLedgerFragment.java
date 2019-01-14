package com.gebeya.smartcontract.publicLedger;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.gebeya.framework.base.BaseFragment;
import com.gebeya.framework.utils.Api;
import com.gebeya.framework.utils.CheckInternetConnection;
import com.gebeya.smartcontract.App;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.dto.PublicLedgerResponseDTO;
import com.gebeya.smartcontract.data.dto.TransactionDTO;
import com.gebeya.smartcontract.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.publicLedger.api.service.PublicLedgerService;
import com.gebeya.smartcontract.publicLedger.transactionDetail.TransactionDetailActivity;
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

public class PublicLedgerFragment extends BaseFragment {

    @BindView(R.id.publicLedgerRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_view_public_ledger)
    CircularProgressView progressView;

    //Bind with the swipe refresh container
    @BindView(R.id.publicLedgerSwipeContainer)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.noPublicLedger)
    TextView mNoPublicLedger;

    private RecyclerView.LayoutManager mLayoutManager;
    private PublicLedgerService mPublicLedgerService;
    private PublicLedgerAdapter mPublicLedgerAdapter;
    private boolean isConnected;

    BoxStore userBox;
    Box<UserLoginData> box;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the retrofit client service for the public ledger.
        mPublicLedgerService = Api.getPublicLedgerService();

        // Retrieve the Box for the UserLogin.
        userBox = ((App) Objects.requireNonNull(getActivity()).getApplicationContext()).getStore();
        box = userBox.boxFor(UserLoginData.class);


    }


    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        inflate(R.layout.fragment_public_ledger, container);

        // No public Ledger message.
        mNoPublicLedger.setVisibility(View.INVISIBLE);

        mPublicLedgerAdapter = new PublicLedgerAdapter(getActivity(),
              new ArrayList<TransactionDTO>(0),
              new PublicLedgerCallback() {
                  @Override
                  public void onSelected(int position, String id) {
                      //toast("Selected position is: " + position);
                      // start make transaction activity.
                      Intent intent = new Intent(getActivity(), TransactionDetailActivity.class);
                      intent.putExtra("ASSET_ID", id);
                      startActivity(intent);
                  }
              });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mPublicLedgerAdapter);
        mRecyclerView.setHasFixedSize(true);

        // Check connection.
        isConnected = new CheckInternetConnection().CheckInternetConnection(getContext());

        if (isConnected) {
            loadPublicLedger();
        } else {
            toast("No Internet Connection");
            progressView.setVisibility(View.GONE);
            swipeContainer.setRefreshing(false);
        }

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isConnected) {
                    loadPublicLedger();
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
        return root;
    }


    private void loadPublicLedger() {

        // loads User token from objectBox
        List<UserLoginData> users = box.getAll();
        String token = users.get(0).getToken();
        String bearerToken = "Bearer " + token;

        mPublicLedgerService.getLedger(bearerToken,
              CONTENT_TYPE
        ).enqueue(new Callback<PublicLedgerResponseDTO>() {

            @Override
            public void onResponse(Call<PublicLedgerResponseDTO> call,
                                   Response<PublicLedgerResponseDTO> response) {
                if (response.isSuccessful()) {
                    d("Public Ledger Activity transaction are loaded from API");
                    PublicLedgerResponseDTO ledgerResponse = response.body();
                    List<TransactionDTO> transactions = ledgerResponse.getData();
                    d("Transactions loaded: " + transactions.size());
                    if (!transactions.isEmpty()) {
                        mPublicLedgerAdapter.updateTransactions(response.body().getData());
                    } else {
                        mNoPublicLedger.setVisibility(View.VISIBLE);
                    }
                    progressView.setVisibility(View.GONE);
                    swipeContainer.setRefreshing(false);
                } else {
                    e("Response was not successful");
                    int statusCode = response.code();
                    e("Response code: " + statusCode);
                    progressView.setVisibility(View.GONE);
                    swipeContainer.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<PublicLedgerResponseDTO> call, Throwable t) {
                d("Public Ledger Fragment error loading from API");
                t.printStackTrace();
                progressView.setVisibility(View.GONE);
                swipeContainer.setRefreshing(false);
            }
        });
    }
}
