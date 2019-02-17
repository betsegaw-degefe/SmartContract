package com.gebeya.smartcontract.view.myAsset;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.databinding.MyCarAssetLayoutBinding;
import com.gebeya.smartcontract.view.slidingImage.SlidingImageAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAssetCarViewHolder extends RecyclerView.ViewHolder
      implements View.OnClickListener {

    private MyCarAssetLayoutBinding binding;

    /**
     * Declaring local variables.
     */
    private MyAssetCallback mCallback;
    private Context mContext;
    private String assetId;
    private String mTypeOfAsset;


    public MyAssetCarViewHolder(@NonNull View itemView, MyAssetCallback callback,
                                MyCarAssetLayoutBinding myCarAssetLayoutBinding) {
        super(myCarAssetLayoutBinding.getRoot());
        this.mCallback = callback;
        mContext = itemView.getContext();
        binding = myCarAssetLayoutBinding;
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        mCallback.onSelected(position, assetId, mTypeOfAsset);
    }

    public void setAssetSpecification(String specification) {
        if (!specification.isEmpty())
            binding.tvAssetSpecification.setText(specification);
    }

    public void setAssetType(String type) {
        if (!type.isEmpty())
            binding.assetType.setText(type);
    }

    public void setAssetRegistered(String registered) {
        if (!registered.isEmpty())
            binding.assetRegistered.setText(registered);
    }

    void setAssetId(String id) {
        this.assetId = id;
    }

    public void setTypeOfAsset(String assetType) {
        this.mTypeOfAsset = assetType;
    }

    /**
     * Set car pictures in to the prepared used container.
     *
     * @param pictures a list of car pictures of a specific user.
     */
    void setAssetPictures(List<List<String>> pictures) {
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
            binding.myAssetViewPager.setAdapter(adapter);
            binding.indicator.setViewPager(binding.myAssetViewPager);
        }
    }


}
