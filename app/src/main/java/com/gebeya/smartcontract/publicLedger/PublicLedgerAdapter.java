package com.gebeya.smartcontract.publicLedger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.model.Transaction;

import java.util.List;

public class PublicLedgerAdapter extends RecyclerView.Adapter<PublicLedgerViewHolder> {

    private List<Transaction> mTransactions;
    private LayoutInflater inflater;
    private PublicLedgerCallback mCallback;
    private Context mContext;

    public PublicLedgerAdapter(Context context, List<Transaction> transactions, PublicLedgerCallback callback) {
        this.mCallback = callback;
        //this.inflater = inflater;
        this.mTransactions = transactions;
        this.mContext = context;

    }

    @NonNull
    @Override
    public PublicLedgerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                     int viewType) {
        Context context = viewGroup.getContext();
        inflater = LayoutInflater.from(context);
        final View itemView = inflater.inflate(R.layout.public_ledger_layout, viewGroup, false);
        return new PublicLedgerViewHolder(itemView, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicLedgerViewHolder publicLedgerViewHolder, int i) {

        final Transaction transaction = mTransactions.get(i);

        publicLedgerViewHolder.setFrom(transaction.getFrom().getFirstName());
        publicLedgerViewHolder.setTo(transaction.getTo().getFirstName());
        publicLedgerViewHolder.setCreatedAt(transaction.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    public void updateTransactions(List<Transaction> transactions) {
        mTransactions = transactions;
        notifyDataSetChanged();
    }

    private Transaction getTransaction(int adapterPosition) {
        return mTransactions.get(adapterPosition);
    }
}
