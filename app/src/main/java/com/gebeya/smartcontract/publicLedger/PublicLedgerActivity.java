package com.gebeya.smartcontract.publicLedger;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.publicLedger.api.model.PublicLedgerRespond;
import com.gebeya.smartcontract.publicLedger.api.service.PublicLedgerClient;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.gebeya.framework.utils.Api.BASE_URL;

public class PublicLedgerActivity extends BaseActivity {

    @BindView(R.id.ledgerToolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_ledger);
        bind();

        setSupportActionBar(mToolbar);

        //Creating a Retrofit client
        Retrofit.Builder builder = new Retrofit.Builder()
              .baseUrl(BASE_URL)
              .addConverterFactory(GsonConverterFactory.create());

        //Creating a retrofit object
        Retrofit retrofit = builder.build();

        PublicLedgerClient client = retrofit.create(PublicLedgerClient.class);
        Call<List<PublicLedgerRespond>> call =  client.publicLedger();

        call.enqueue(new Callback<List<PublicLedgerRespond>>() {
            @Override
            public void onResponse(Call<List<PublicLedgerRespond>> call, Response<List<PublicLedgerRespond>> response) {
                List<PublicLedgerRespond> publicLedgers = response.body();
            }

            @Override
            public void onFailure(Call<List<PublicLedgerRespond>> call, Throwable t) {

            }
        });
    }



}
