package com.gebeya.smartcontract.myAsset;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.dto.UserResponseDTO;

public class MyAssetAdapter extends RecyclerView.Adapter<MyAssetViewHolder> {

    private LayoutInflater inflater;
    private UserResponseDTO mUserResponseDTO;
    private MyAssetCallback mCallback;
    private Context mContext;

    public MyAssetAdapter(Context context,
                          UserResponseDTO userResponseDTO
                          ) {
        this.mContext = context;
        this.mUserResponseDTO = userResponseDTO;
     //   this.mCallback = callback;
    }

    @NonNull
    @Override
    public MyAssetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        inflater = LayoutInflater.from(context);
        final View itemView = inflater.inflate(R.layout.my_asset_layout,
              viewGroup, false);

        return new MyAssetViewHolder(itemView,mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAssetViewHolder myAssetViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
