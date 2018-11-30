package com.gebeya.smartcontract.publicLedger;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.Api;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.model.PublicLedgerResponse;
import com.gebeya.smartcontract.data.model.Transaction;
import com.gebeya.smartcontract.publicLedger.api.service.PublicLedgerService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicLedgerActivity extends BaseActivity {

    @BindView(R.id.ledgerToolbar)
    Toolbar mToolbar;

    @BindView(R.id.publicLedgerRecyclerView)
    RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Transaction> mTransactions;
    private LayoutInflater inflater;
    private PublicLedgerCallback callback;
    private PublicLedgerService mPublicLedgerService;
    private PublicLedgerAdapter mPublicLedgerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_ledger);
        bind();
        attachFragment();

        setSupportActionBar(mToolbar);

        mPublicLedgerService = Api.getPublicLedgerService();

        mPublicLedgerAdapter = new PublicLedgerAdapter(this,
              new ArrayList<Transaction>(0),
              new PublicLedgerCallback() {
                  @Override
                  public void onSelected(int position) {
                      toast("Selected position is: " + position);
                  }
              });

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mPublicLedgerAdapter);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,
              DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        loadPublicLedger();

    }

    private void attachFragment() {
        PublicLedgerFragment fragment = new PublicLedgerFragment();
        getSupportFragmentManager()
              .beginTransaction()
              .add(R.id.publicLedgerRecyclerView, fragment)
              .commit();
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
                d("Public Ledger Activity error loading from API");
            }
        });
    }


}
