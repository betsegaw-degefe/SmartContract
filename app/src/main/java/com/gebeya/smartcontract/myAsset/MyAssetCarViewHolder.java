package com.gebeya.smartcontract.myAsset;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gebeya.smartcontract.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAssetCarViewHolder extends RecyclerView.ViewHolder
      implements View.OnClickListener {

    /**
     * Binding with car asset layout components.
     */

    @BindView(R.id.tvAssetSpecification)
    TextView assetSpecification;

    @BindView(R.id.assetType)
    TextView assetType;

    @BindView(R.id.assetRegistered)
    TextView assetRegistered;

    @BindView(R.id.myAssetViewPager)
    ViewPager mViewPager;

    @BindView(R.id.indicator)
    CirclePageIndicator indicator;


    /**
     * Declaring local variables.
     */

    private MyAssetCallback mCallback;
    private Context mContext;

    public MyAssetCarViewHolder(@NonNull View itemView, MyAssetCallback callback) {
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

    /**
     * Set car pictures in to the prepared used container.
     *
     * @param pictures a list of car pictures of a specific user.
     */
    public void setAssetPictures(List<List<String>> pictures) {
        String[] url = new String[pictures.size()];
        if (!pictures.isEmpty()) {
            int i = 0;
            for (List<String> innerList : pictures) {
                for (String imageUrl : innerList) {
                    url[i] = imageUrl;
                    i++;
                }
            }
            SlidingImageAdapter adapter = new SlidingImageAdapter(mContext, url);
            mViewPager.setAdapter(adapter);
            indicator.setViewPager(mViewPager);

        }
    }


}
