package com.gebeya.smartcontract.publicLedger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gebeya.framework.base.BaseFragment;
import com.gebeya.smartcontract.R;

public class PublicLedgerFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.fragment_public_ledger,container,false);
        //return view;
        inflate(R.layout.fragment_public_ledger, container);
        return root;
    }
}
