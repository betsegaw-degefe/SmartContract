package com.gebeya.smartcontract.view.publicLedger;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import com.gebeya.smartcontract.App;
import com.gebeya.smartcontract.MainActivity;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.databinding.FragmentAboutUsBinding;
import com.gebeya.smartcontract.databinding.FragmentPublicLedgerBinding;
import com.gebeya.smartcontract.login.LoginActivity;
import com.gebeya.smartcontract.model.data.dto.TransactionDTO;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.view.publicLedger.transactionDetail.TransactionDetailActivity;
import com.gebeya.smartcontract.viewmodel.changePassword.ChangePasswordViewModel;
import com.gebeya.smartcontract.viewmodel.publicLedger.PublicLedgerViewModel;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class PublicLedgerFragment extends BaseFragment {

    // binding class for publicLedgerLayout
    FragmentPublicLedgerBinding binding;

    private RecyclerView.LayoutManager mLayoutManager;
    private PublicLedgerAdapter mPublicLedgerAdapter;
    PublicLedgerViewModel viewModel;

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

        // Inflate the binding layout ui.
        binding = DataBindingUtil.inflate(
              inflater, R.layout.fragment_public_ledger, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        // call the function which set the recycler view.
        setupRecyclerView();

        // No public Ledger message.
        binding.noPublicLedger.setVisibility(View.INVISIBLE);

        // Create a PublicLedgerViewModel the first time the system calls an
        // fragments.Re-created fragments receive the same PublicLedgerViewModel instance
        // created by the first fragment.
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
              .get(PublicLedgerViewModel.class);

        // Retrieve the Box for the UserLogin.
        userBox = ((App) Objects.requireNonNull(getActivity()).getApplicationContext()).getStore();
        box = userBox.boxFor(UserLoginData.class);

        // Observe the change in public ledger view model.
        observeViewModel(viewModel);
        observeErrorViewModel();

        binding.publicLedgerSwipeContainer.setOnRefreshListener(() -> {
            // Load assets from the server.
            if (isConnected()) {
                // Observe the change in public ledger view model.
                viewModel = new PublicLedgerViewModel(getActivity().getApplication());
                observeViewModel(viewModel);
                observeErrorViewModel();
            } else {
                Snackbar.make(binding.publicLedgerRelativeLayout, R.string.NoInternetConnectionLabel, Snackbar.LENGTH_SHORT)
                      .show();
                binding.progressViewPublicLedger.setVisibility(View.GONE);
                binding.publicLedgerSwipeContainer.setRefreshing(false);
            }
        });

        binding.publicLedgerSwipeContainer.setColorSchemeResources(
              R.color.colorPrimary,
              R.color.colorPrimaryDark,
              R.color.ruby_dark);

        binding.publicLedgerRecyclerView.addOnScrollListener(new OnScrollListener() {
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
     * Observe the Error model DTO object change. If there is, This function triggered.
     */
    private void observeErrorViewModel() {
        // Set observable on the ErrorResponseDTO and triggered when the change happen.
        PublicLedgerViewModel.getErrorResponseObservable()
              .observe(this, errorResponseDTO -> {
                  errorResponseDTO = PublicLedgerViewModel.getErrorResponseObservable().getValue();
                  String message = Objects.requireNonNull(errorResponseDTO).getMessage();
                  if (message.equals("Authorization Header Token is Invalid")) {
                     // box.removeAll();
                      startActivity(new Intent(getActivity(), LoginActivity.class));
                      Objects.requireNonNull(getActivity()).finish();
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
        binding.publicLedgerRecyclerView.setLayoutManager(mLayoutManager);
        binding.publicLedgerRecyclerView.setAdapter(mPublicLedgerAdapter);
        binding.publicLedgerRecyclerView.setHasFixedSize(true);
    }

    /**
     * Observe the change in public ledger view model.
     *
     * @param viewModel: which hold the data for publicLedgerFragment
     */
    private void observeViewModel(PublicLedgerViewModel viewModel) {

        if (isConnected()) {
            PublicLedgerViewModel.getPublicLedgerResponseObservable()
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
                          binding.noPublicLedger.setVisibility(View.GONE);
                          mPublicLedgerAdapter.updateTransactions(transactions);
                          mPublicLedgerAdapter.notifyDataSetChanged();
                          binding.progressViewPublicLedger.setVisibility(View.GONE);
                          binding.publicLedgerSwipeContainer.setRefreshing(false);
                      } else {
                          binding.noPublicLedger.setVisibility(View.VISIBLE);
                          binding.progressViewPublicLedger.setVisibility(View.GONE);
                          binding.publicLedgerSwipeContainer.setRefreshing(false);
                      }
                  });
        } else {
            binding.noPublicLedger.setVisibility(View.VISIBLE);
            Snackbar.make(binding.publicLedgerRelativeLayout, R.string.NoInternetConnectionLabel, Snackbar.LENGTH_SHORT)
                  .show();
            binding.progressViewPublicLedger.setVisibility(View.GONE);
        }

    }
}
