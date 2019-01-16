package com.gebeya.smartcontract.publicLedger;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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
import com.gebeya.smartcontract.model.data.dto.PublicLedgerResponseDTO;
import com.gebeya.smartcontract.model.data.dto.TransactionDTO;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.publicLedger.api.service.PublicLedgerService;
import com.gebeya.smartcontract.publicLedger.transactionDetail.TransactionDetailActivity;
import com.gebeya.smartcontract.viewmodel.PublicLedgerViewModel;
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

    /*// Bind with the swipe refresh container
    @BindView(R.id.publicLedgerSwipeContainer)
    SwipeRefreshLayout swipeContainer;
*/
    @BindView(R.id.noPublicLedger)
    TextView mNoPublicLedger;

    private RecyclerView.LayoutManager mLayoutManager;
    private PublicLedgerService mPublicLedgerService;
    private PublicLedgerAdapter mPublicLedgerAdapter;
    PublicLedgerViewModel viewModel;

    BoxStore userBox;
    Box<UserLoginData> box;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
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
        // fragments.
        // Re-created fragments receive the same PublicLedgerViewModel instance
        // created by the first fragment.

        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
              .get(PublicLedgerViewModel.class);
        observeViewModel(viewModel);
    }

    /**
     * Check connection.
     *
     * @return true: if there is connection; false: if there is no connection.
     */
    private boolean checkConnection() {
        return new CheckInternetConnection().CheckInternetConnection(getContext());
    }

    /**
     * Set the recycler view of public Ledger.
     */
    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
            mPublicLedgerAdapter = new PublicLedgerAdapter(getActivity(),
                  new ArrayList<TransactionDTO>(0),
                  (position, id) -> {
                      //toast("Selected position is: " + position);
                      // start make transaction activity.
                      Intent intent = new Intent(getActivity(), TransactionDetailActivity.class);
                      intent.putExtra("ASSET_ID", id);
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
        viewModel.getPublicLedgerResponseObservable()
              .observe(this, publicLedgerResponseDTO -> {
                  if (publicLedgerResponseDTO != null) {
                      List<TransactionDTO> transactions = publicLedgerResponseDTO.getData();
                      mPublicLedgerAdapter.updateTransactions(transactions);
                      mPublicLedgerAdapter.notifyDataSetChanged();
                      progressView.setVisibility(View.GONE);
                      //swipeContainer.setRefreshing(false);
                  } else {
                      mNoPublicLedger.setVisibility(View.VISIBLE);
                      progressView.setVisibility(View.GONE);
                      // swipeContainer.setRefreshing(false);
                      toast("No Internet Connection");
                  }
              });
    }
}
