package com.gebeya.smartcontract.view.publicLedger.transactionDetail;

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
import com.gebeya.smartcontract.model.data.dto.CarTransactionDTO;
import com.gebeya.smartcontract.model.data.dto.HouseTransactionDTO;
import com.gebeya.smartcontract.model.data.model.House;

import java.util.List;

import butterknife.BindView;

public class TransactionDetailAdapter extends RecyclerView.Adapter<TransactionDetailViewHolder> {

    @BindView(R.id.transactionDetailRecyclerView)
    RecyclerView mRecyclerView;

    private List<CarTransactionDTO> mCarTransactions;
    private List<HouseTransactionDTO> mHouseTransactions;
    private LayoutInflater inflater;
    private TransactionDetailCallback mCallback;
    private Context mContext;


    public TransactionDetailAdapter(Context context,
                                    List<CarTransactionDTO> carTransactions,
                                    List<HouseTransactionDTO> houseTransaction,
                                    TransactionDetailCallback callback) {
        mCarTransactions = carTransactions;
        mHouseTransactions = houseTransaction;
        mCallback = callback;
        mContext = context;
    }

    @NonNull
    @Override
    public TransactionDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        inflater = LayoutInflater.from(context);
        final View itemView = inflater.inflate(R.layout.transaction_detail,
              viewGroup, false);
        return new TransactionDetailViewHolder(itemView, mCallback);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull TransactionDetailViewHolder transactionDetailViewHolder,
                                 int position) {
        if (mCarTransactions.size() != 0) {
            CarTransactionDTO transaction = mCarTransactions.get(position);

            if (transaction.getCar() != null) {
                transactionDetailViewHolder.setType("Car Transaction");
            }

            if (transaction.getFrom() != null &&
                  transaction.getTo() != null &&
                  transaction.getCreatedAt() != null) {

                transactionDetailViewHolder.setFrom(transaction.getFrom().getFirstName() + " " + transaction.getFrom().getLastName());
                transactionDetailViewHolder.setTo(transaction.getTo().getFirstName() + " " + transaction.getTo().getLastName());
                DateFormatter dateFormatter = new DateFormatter();
                transactionDetailViewHolder.setCreatedAt(dateFormatter.DateFormatter(transaction.getCreatedAt()));
            }
        } else if (mHouseTransactions.size() != 0) {
            HouseTransactionDTO transaction = mHouseTransactions.get(position);

            if (transaction.getHouse() != null) {
                transactionDetailViewHolder.setType("House Transaction");
            }

            if (transaction.getFrom() != null &&
                  transaction.getTo() != null &&
                  transaction.getCreatedAt() != null) {

                transactionDetailViewHolder.setFrom(transaction.getFrom().getFirstName() + " " + transaction.getFrom().getLastName());
                transactionDetailViewHolder.setTo(transaction.getTo().getFirstName() + " " + transaction.getTo().getLastName());
                DateFormatter dateFormatter = new DateFormatter();
                transactionDetailViewHolder.setCreatedAt(dateFormatter.DateFormatter(transaction.getCreatedAt()));
            }

        }
    }

    @Override
    public int getItemCount() {
        if (mCarTransactions.size() != 0)
            return mCarTransactions.size();
        else
            return mHouseTransactions.size();
    }

    /**
     * get the transaction from the API and update the transaction view.
     *
     * @param transactions: list of transaction
     */
    public void updateCarTransactions(List<CarTransactionDTO> transactions) {

        mCarTransactions = transactions;
        notifyDataSetChanged();

    }

    public void updaterHouseTransactions(List<HouseTransactionDTO> transactions) {
        mHouseTransactions = transactions;
        notifyDataSetChanged();
    }
}
