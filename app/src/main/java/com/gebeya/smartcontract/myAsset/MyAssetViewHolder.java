package com.gebeya.smartcontract.myAsset;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gebeya.smartcontract.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAssetViewHolder extends RecyclerView.ViewHolder
      implements View.OnClickListener {

    @BindView(R.id.tvAssetSpecification)
    TextView assetSpecification;

    @BindView(R.id.assetType)
    TextView assetType;

    @BindView(R.id.assetRegistered)
    TextView assetRegistered;

    @BindView(R.id.myAssetViewPager)
    ViewPager mViewPager;

    private MyAssetCallback mCallback;
    private Context mContext;

    public MyAssetViewHolder(@NonNull View itemView, MyAssetCallback callback) {
        super(itemView);

        this.mCallback = callback;
        mContext = itemView.getContext();
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        mCallback.onSelected(position);
    }

    public void setAssetSpecification(String specification) {
        if (!specification.isEmpty())
            assetSpecification.setText(specification);
    }

    public void setAssetType(String type) {
        if (!type.isEmpty())
            assetType.setText(type);
    }

    public void setAssetRegistered(String registered) {
        if (!registered.isEmpty())
            assetRegistered.setText(registered);
    }

    public void setAssetPictures(List<List<String>> pictures) {
        String[] url = new String[pictures.size()];
        if (!pictures.isEmpty()) {
            int i = 0;
            for (List<String> innerList : pictures) {
                for(String imageUrl: innerList) {
                    url[i] = imageUrl;
                    i++;
                }
            }
            slidingImageAdapter adapter = new slidingImageAdapter(mContext, url);
            mViewPager.setAdapter(adapter);
        }

    }
}
