package com.gebeya.smartcontract.publicLedger;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gebeya.framework.utils.DateFormatter;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.dto.TransactionDTO;

import java.util.List;

public class PublicLedgerAdapter extends RecyclerView.Adapter<PublicLedgerViewHolder> {

    private List<TransactionDTO> mTransactions;
    private LayoutInflater inflater;
    private PublicLedgerCallback mCallback;
    private Context mContext;

    /**
     *  Passing data to the constructor
     */
    public PublicLedgerAdapter(Context context,
                               List<TransactionDTO> transactions,
                               PublicLedgerCallback callback) {
        this.mCallback = callback;
        this.mTransactions = transactions;
        this.mContext = context;
    }

    /**
     * Inflate the row layout from xml when needed
     */
    @NonNull
    @Override
    public PublicLedgerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                     int viewType) {
        Context context = viewGroup.getContext();
        inflater = LayoutInflater.from(context);
        final View itemView = inflater.inflate(R.layout.public_ledger_layout, viewGroup, false);
        return new PublicLedgerViewHolder(itemView, mCallback);
    }


    /**
     * binds the data to the TextView in each row
     * @param publicLedgerViewHolder
     * @param position
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull PublicLedgerViewHolder publicLedgerViewHolder, int position) {

        TransactionDTO transaction = mTransactions.get(position);

        if (transaction.getCar() != null) {
            publicLedgerViewHolder.setType("Car Transaction");
        } else if (transaction.getHouse() != null) {
            publicLedgerViewHolder.setType("House Transaction");
        }

        publicLedgerViewHolder.setFrom(transaction.getFrom().getPublicId());
        publicLedgerViewHolder.setTo(transaction.getTo().getPublicId());

        DateFormatter dateFormatter = new DateFormatter();

        publicLedgerViewHolder.setCreatedAt(dateFormatter.DateFormatter(transaction.getCreatedAt()));

    }

    /**
     * Total number of transaction.
     * @return mTransactions.size()
     */
    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    /**
     * get the transaction from the API and update the transaction view.
     * @param transactions
     */
    public void updateTransactions(List<TransactionDTO> transactions) {
        mTransactions = transactions;
        notifyDataSetChanged();

    }

    private TransactionDTO getTransaction(int adapterPosition) {
        return mTransactions.get(adapterPosition);
    }

}
