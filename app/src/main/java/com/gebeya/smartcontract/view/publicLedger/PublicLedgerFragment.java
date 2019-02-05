package com.gebeya.smartcontract.view.publicLedger;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gebeya.framework.base.BaseFragment;
import com.gebeya.framework.utils.CheckInternetConnection;
import com.gebeya.smartcontract.MainActivity;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.model.data.dto.TransactionDTO;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.view.publicLedger.transactionDetail.TransactionDetailActivity;
import com.gebeya.smartcontract.viewmodel.publicLedger.PublicLedgerViewModel;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class PublicLedgerFragment extends BaseFragment {

    @BindView(R.id.publicLedgerRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_view_public_ledger)
    CircularProgressView progressView;

    // Bind with the swipe refresh container
    @BindView(R.id.publicLedgerSwipeContainer)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.noPublicLedger)
    TextView mNoPublicLedger;

    private RecyclerView.LayoutManager mLayoutManager;
    private PublicLedgerAdapter mPublicLedgerAdapter;
    PublicLedgerViewModel viewModel;

    @BindView(R.id.publicLedgerRelativeLayout)
    RelativeLayout mRelativeLayout;

    BoxStore userBox;
    Box<UserLoginData> box;

    // Intent Key name space holders.
    private static final String KEY_ASSET_ID = "ASSET_ID";
    private static final String KEY_ASSET_TYPE = "ASSET_TYPE";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        inflate(R.layout.fragment_public_ledger, container);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        // call the function which set the recycler view.
        setupRecyclerView();

        // No public Ledger message.
        mNoPublicLedger.setVisibility(View.INVISIBLE);

        // Create a PublicLedgerViewModel the first time the system calls an
        // fragments.Re-created fragments receive the same PublicLedgerViewModel instance
        // created by the first fragment.
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
              .get(PublicLedgerViewModel.class);

        // Observe the change in public ledger view model.
        observeViewModel(viewModel);

        swipeContainer.setOnRefreshListener(() -> {
            // Load assets from the server.
            if (isConnected()) {
                // Observe the change in public ledger view model.
                observeViewModel(viewModel);
            } else {
                //toast("No Internet Connection");

                Snackbar.make(mRelativeLayout, R.string.NoInternetConnectionLabel, Snackbar.LENGTH_SHORT)
                      .show();
                progressView.setVisibility(View.GONE);
                swipeContainer.setRefreshing(false);
            }
        });

        swipeContainer.setColorSchemeResources(
              R.color.colorPrimary,
              R.color.colorPrimaryDark,
              R.color.ruby_dark);

        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                ((MainActivity) getActivity()).onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
                super.onScrolled(recyclerView, dx, dy);
                ((MainActivity) getActivity()).onScrolled(recyclerView, dx, dy);
            }
        });
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
     * Set the recycler view of public Ledger.
     */
    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mPublicLedgerAdapter = new PublicLedgerAdapter(getActivity(),
              new ArrayList<>(0),
              (position, id, type) -> {
                  // start make transaction activity.
                  Intent intent = new Intent(getActivity(), TransactionDetailActivity.class);
                  intent.putExtra(KEY_ASSET_ID, id);
                  intent.putExtra(KEY_ASSET_TYPE, type);
                  startActivity(intent);
              });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mPublicLedgerAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    /**
     * Observe the change in public ledger view model.
     *
     * @param viewModel: which hold the data for publicLedgerFragment
     */
    private void observeViewModel(PublicLedgerViewModel viewModel) {

        if (isConnected()) {
            viewModel.getPublicLedgerResponseObservable()
                  .observe(this, publicLedgerResponseDTO -> {
                      if (publicLedgerResponseDTO != null) {

                          List<TransactionDTO> checkingTransactionNull = publicLedgerResponseDTO.getData();
                          List<TransactionDTO> transactions = new ArrayList<>();

                          // Check whether transactions have a null field or not
                          // if null field found then the transaction removed from the list.
                          for (int i = 0; i < checkingTransactionNull.size(); i++) {
                              if (checkingTransactionNull.get(i).getCar() != null &&
                                    checkingTransactionNull.get(i).getFrom() != null &&
                                    checkingTransactionNull.get(i).getTo() != null) {

                                  transactions.add(checkingTransactionNull.get(i));

                              } else if (checkingTransactionNull.get(i).getHouse() != null &&
                                    checkingTransactionNull.get(i).getFrom() != null &&
                                    checkingTransactionNull.get(i).getTo() != null) {

                                  transactions.add(checkingTransactionNull.get(i));

                              }
                          }
                          mNoPublicLedger.setVisibility(View.GONE);
                          mPublicLedgerAdapter.updateTransactions(transactions);
                          mPublicLedgerAdapter.notifyDataSetChanged();
                          progressView.setVisibility(View.GONE);
                          swipeContainer.setRefreshing(false);
                      } else {
                          mNoPublicLedger.setVisibility(View.VISIBLE);
                          progressView.setVisibility(View.GONE);
                          swipeContainer.setRefreshing(false);
                      }
                  });
        } else {
            mNoPublicLedger.setVisibility(View.VISIBLE);
            toast("No Internet Connection");
            progressView.setVisibility(View.GONE);
        }

    }
}
