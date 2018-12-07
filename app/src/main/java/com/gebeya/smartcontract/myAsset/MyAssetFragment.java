package com.gebeya.smartcontract.myAsset;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gebeya.framework.base.BaseFragment;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.publicLedger.PublicLedgerAdapter;
import com.gebeya.smartcontract.publicLedger.api.service.PublicLedgerService;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import butterknife.BindView;

public class MyAssetFragment extends BaseFragment {

    @BindView(R.id.rvMyAsset)
    RecyclerView mRecyclerView;

    @BindView(R.id.pvMyAsset)
    CircularProgressView progressView;

    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflate(R.layout.fragment_my_asset, container);


        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
