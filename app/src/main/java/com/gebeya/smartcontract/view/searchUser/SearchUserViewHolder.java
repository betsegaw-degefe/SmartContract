package com.gebeya.smartcontract.view.searchUser;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gebeya.smartcontract.databinding.SearchUserLayoutBinding;

public class SearchUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public SearchUserLayoutBinding binding;

    private SearchUserCallback mCallback;
    private String mUserId;

    SearchUserViewHolder(@NonNull View itemView,
                         SearchUserCallback mSearchUserCallback,
                         SearchUserLayoutBinding searchUserLayoutBinding) {
        super(searchUserLayoutBinding.getRoot());
        this.mCallback = mSearchUserCallback;
        binding = searchUserLayoutBinding;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        mCallback.onSelected(position, mUserId);
    }

    void setName(String firstName, String lastName) {
        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            binding.lblSearchName.setText(String.format("%s %s", firstName, lastName));
        }
    }

    void setPhoneNumber(String phoneNumber) {
        if (!phoneNumber.isEmpty()) {
            binding.lblSearchPhone.setText(phoneNumber);
        }
    }

    void setId(String userId) {
        this.mUserId = userId;
    }
}
