package com.gebeya.smartcontract.view.publicLedger;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.databinding.PublicLedgerLayoutBinding;

import butterknife.BindView;

public class PublicLedgerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public PublicLedgerLayoutBinding binding;

    private PublicLedgerCallback mCallback;
    private String assetId;
    private String type;


    /**
     * stores and recycles views as they are scrolled off screen
     *
     * @param itemView  : View
     * @param callback: publicLedgerCallback
     */

    PublicLedgerViewHolder(@NonNull View itemView,
                           PublicLedgerCallback callback,
                           PublicLedgerLayoutBinding publicLedgerLayoutBinding) {

        super(publicLedgerLayoutBinding.getRoot());
        this.mCallback = callback;
        binding = publicLedgerLayoutBinding;
        itemView.setOnClickListener(this);
    }

    /**
     * when a single ledger clicked, set the position of the item to the
     * public ledger callback
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        mCallback.onSelected(position, assetId, type);
    }

    public void setType(String type) {
        if (!type.isEmpty()) {
            this.type = type;
            binding.transactionType.setText(type);
        }
    }


    public void setFrom(String from) {
        binding.transactionFrom.setText(from);
    }

    public void setTo(String to) {
        binding.transactionTo.setText(to);
    }

    public void setCreatedAt(String createdAt) {
        binding.transactionTimeStamp.setText(createdAt);
    }

    public void setAssetId(String id) {
        this.assetId = id;
    }

}
