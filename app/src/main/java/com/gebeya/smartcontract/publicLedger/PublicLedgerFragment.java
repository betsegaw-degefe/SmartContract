package com.gebeya.smartcontract.publicLedger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;

import com.gebeya.framework.base.BaseFragment;
import com.gebeya.framework.utils.Api;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.model.PublicLedgerResponse;
import com.gebeya.smartcontract.data.model.Transaction;
import com.gebeya.smartcontract.publicLedger.api.service.PublicLedgerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicLedgerFragment extends BaseFragment {

//    @BindView(R.id.ledgerToolbar)
//    Toolbar mToolbar;

    @BindView(R.id.publicLedgerRecyclerView)
    RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private PublicLedgerService mPublicLedgerService;
    private PublicLedgerAdapter mPublicLedgerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPublicLedgerService = Api.getPublicLedgerService();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        inflate(R.layout.fragment_public_ledger, container);

        mPublicLedgerAdapter = new PublicLedgerAdapter(getActivity(),
              new ArrayList<Transaction>(0),
              new PublicLedgerCallback() {
                  @Override
                  public void onSelected(int position) {
                      toast("Selected position is: " + position);
                  }
              });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mPublicLedgerAdapter);
        mRecyclerView.setHasFixedSize(true);

        loadPublicLedger();
        return root;
    }


    private void loadPublicLedger() {
        mPublicLedgerService.getLedger().enqueue(new Callback<PublicLedgerResponse>() {
            @Override
            public void onResponse(Call<PublicLedgerResponse> call,
                                   Response<PublicLedgerResponse> response) {
                if(response.isSuccessful()){
                    d("Public Ledger Activity transactions are loaded from API");
                    PublicLedgerResponse ledgerResponse = response.body();
                    List<Transaction> transactions = ledgerResponse.getData();
                    d("Transactions loaded: " + transactions.size());
                    mPublicLedgerAdapter.updateTransactions(response.body().getData());
                } else {
                    e("Response was not successful");
                    int statusCode  = response.code();
                    e("Response code: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<PublicLedgerResponse> call, Throwable t) {
                d("Public Ledger Fragment error loading from API");
                t.printStackTrace();
            }
        });
    }

}
