package com.gebeya.smartcontract.view.searchUser;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.CheckInternetConnection;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.databinding.ActivityAllSearchUserBinding;
import com.gebeya.smartcontract.viewmodel.searchUser.SearchUserViewModel;

public class SearchUserActivity extends BaseActivity {

    ActivityAllSearchUserBinding binding;
    SearchView mSearchView;
    SearchUserViewModel searchUserViewModel;

    private SearchUserAdapter mSearchUserAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the binding layout ui.
        binding =
              DataBindingUtil.setContentView(this, R.layout.activity_all_search_user);

        // Set the action bar.
        setActionBar();

        // call the function which set the recycler view.
        initViews();

        // Create a PublicLedgerViewModel the first time the system calls an
        // fragments.Re-created fragments receive the same PublicLedgerViewModel instance
        // created by the first fragment.
        searchUserViewModel =
              ViewModelProviders.of(this).get(SearchUserViewModel.class);

        // Sets the LifecycleOwner that should be used for observing changes of LiveData.
        binding.setLifecycleOwner(this);

        // Observe the change in public ledger view model.
        observeViewModel(searchUserViewModel);
    }

    /**
     * Observe the change in search user view model.
     *
     * @param searchUserViewModel which hold the data for Search User UI.
     */
    private void observeViewModel(SearchUserViewModel searchUserViewModel) {
        if (isConnected()) {
            searchUserViewModel.getUser().observe(this, usersResponse -> {
                if (usersResponse != null) {

                    // Instantiate SearchUserAdapter
                    mSearchUserAdapter =
                          new SearchUserAdapter(this, usersResponse,
                                (position, id) -> toast("Selected position is: " + position));
                    binding.searchUserRecyclerView.setAdapter(mSearchUserAdapter);
                    mSearchUserAdapter.notifyDataSetChanged();
                    binding.progressViewSearchUser.setVisibility(View.GONE);
                } else {
                    binding.noSearchUser.setVisibility(View.VISIBLE);
                }
            });
        } else {
            toast("No Internet Connection");

            binding.progressViewSearchUser.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu file (menu_search.xml) that have the search icon,
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Declaring search menu item.
        MenuItem mSearch = menu.findItem(R.id.action_search);

        // Declaring SearchView that we are going to use it to perform the actual search.
        if (mSearch != null) {
            mSearchView = (SearchView) mSearch.getActionView();
        }

        mSearchView.setQueryHint("Search...");

        // Listen user when performing a search request.
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

            public boolean onQueryTextChange(String newText) {
                if (mSearchUserAdapter != null) mSearchUserAdapter.getFilter().filter(newText);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        };
        //return super.onCreateOptionsMenu(menu);
        mSearchView.setOnQueryTextListener(queryTextListener);
        return true;
    }

    /**
     * whenever an back item in options menu is selected.
     *
     * @param item: MenuItem is passed as a parameter.
     * @return: true if selected and false if not
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    /**
     * Setting action bar
     */
    private void setActionBar() {
        setSupportActionBar(binding.searchUserToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Initiate the recycler view
     */
    private void initViews() {
        binding.progressViewSearchUser.setVisibility(View.VISIBLE);
        mLayoutManager = new LinearLayoutManager(this);
        binding.searchUserRecyclerView.setHasFixedSize(true);
        binding.searchUserRecyclerView.setLayoutManager(mLayoutManager);
    }

    /**
     * Check connection.
     *
     * @return true, if there is connection; false, if there is no connection.
     */
    private boolean isConnected() {
        return new CheckInternetConnection().CheckInternetConnection(this);
    }

}
