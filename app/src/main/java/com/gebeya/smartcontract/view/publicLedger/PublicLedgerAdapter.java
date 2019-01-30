package com.gebeya.smartcontract.view.publicLedger;

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
import com.gebeya.smartcontract.model.data.dto.TransactionDTO;

import java.util.List;

import butterknife.BindView;

public class PublicLedgerAdapter extends RecyclerView.Adapter<PublicLedgerViewHolder> {

    @BindView(R.id.publicLedgerRecyclerView)
    RecyclerView mRecyclerView;

    private List<TransactionDTO> mTransactions;
    private LayoutInflater inflater;
    private PublicLedgerCallback mCallback;
    private Context mContext;

    /**
     * Passing data to the constructor
     */
    PublicLedgerAdapter(Context context,
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
     *
     * @param: publicLedgerViewHolder
     * @param: position
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull PublicLedgerViewHolder publicLedgerViewHolder, int position) {

        TransactionDTO transaction = mTransactions.get(position);

        if (transaction.getCar() != null) {
            publicLedgerViewHolder.setType("Car Transaction");
            publicLedgerViewHolder.setAssetId(transaction.getCar().getId());

        } else if (transaction.getHouse() != null) {
            publicLedgerViewHolder.setType("House Transaction");
            publicLedgerViewHolder.setAssetId(transaction.getHouse().getId());
        }

        if (transaction.getFrom() != null &&
              transaction.getTo() != null &&
              transaction.getCreatedAt() != null) {

            publicLedgerViewHolder.setFrom(transaction.getFrom().getFirstName() + " " + transaction.getFrom().getLastName());
            publicLedgerViewHolder.setTo(transaction.getTo().getFirstName() + " " + transaction.getTo().getLastName());
            DateFormatter dateFormatter = new DateFormatter();
            publicLedgerViewHolder.setCreatedAt(dateFormatter.DateFormatter(transaction.getCreatedAt()));
        }
    }

    /**
     * Total number of transaction.
     *
     * @return mTransactions.size()
     */
    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    /**
     * get the transaction from the API and update the transaction view.
     *
     * @param transactions: list of transaction
     */
    public void updateTransactions(List<TransactionDTO> transactions) {

        mTransactions = transactions;
        notifyDataSetChanged();

    }

    private TransactionDTO getTransaction(int adapterPosition) {
        return mTransactions.get(adapterPosition);
    }


}
