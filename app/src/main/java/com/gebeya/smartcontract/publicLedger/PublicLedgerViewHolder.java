package com.gebeya.smartcontract.publicLedger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gebeya.smartcontract.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublicLedgerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.transactionType)
    TextView mType;

    @BindView(R.id.transactionFrom)
    TextView mFrom;

    @BindView(R.id.transactionTo)
    TextView mTo;

    @BindView(R.id.transactionTimeStamp)
    TextView mTimeStamp;

    private Context mContext;
    private PublicLedgerCallback mCallback;

    /**
     * stores and recycles views as they are scrolled off screen
     * @param itemView : View
     * @param callback: publicLedgerCallback
     */

    public PublicLedgerViewHolder(@NonNull View itemView,
                                  PublicLedgerCallback callback) {
        super(itemView);

        this.mCallback = callback;
        mContext = itemView.getContext();
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    /**
     * when a single ledger click, set the position of the item to the
     * public ledger callback
     * @param v
     */
    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        mCallback.onSelected(position);
    }

    public void setType(String type) {
        if (!type.isEmpty())
            mType.setText(type);
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
