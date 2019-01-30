package com.gebeya.smartcontract.view.publicLedger.transactionDetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gebeya.smartcontract.R;

import butterknife.BindView;

public class TransactionDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.transactionDetailFrom)
    TextView mFrom;

    @BindView(R.id.transactionDetailTo)
    TextView mTo;

    @BindView(R.id.transactionDetailTimeStamp)
    TextView mTimeStamp;

    private TransactionDetailCallback mCallback;

    public TransactionDetailViewHolder(@NonNull View itemView,
                                       TransactionDetailCallback callback) {
        super(itemView);
        this.mCallback = callback;
    }

    @Override
    public void onClick(View v) {

    }

    public void setFrom(String from) {
        mFrom.setText(from);
    }

    public void setTo(String to) {
        mTo.setText(to);
    }

    public void setCreatedAt(String createdAt) {
        mTimeStamp.setText(createdAt);
    }

}
