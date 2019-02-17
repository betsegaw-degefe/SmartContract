package com.gebeya.smartcontract.view.myAsset;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.databinding.MyHouseAssetLayoutBinding;
import com.gebeya.smartcontract.view.slidingImage.SlidingImageAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAssetHouseViewHolder extends RecyclerView.ViewHolder
      implements View.OnClickListener {


    MyHouseAssetLayoutBinding binding;


    /**
     * Declaring local variables.
     */
    private MyAssetCallback mCallback;
    private String mTypeOfAsset;
    private Context mContext;
    private String assetId;

    public MyAssetHouseViewHolder(@NonNull View itemView, MyAssetCallback callback,
                                  MyHouseAssetLayoutBinding myHouseAssetLayoutBinding) {
        super(myHouseAssetLayoutBinding.getRoot());

        this.mCallback = callback;
        mContext = itemView.getContext();
        binding = myHouseAssetLayoutBinding;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        mCallback.onSelected(position, assetId, mTypeOfAsset);
    }

    /**
     * Set the location of the house
     */
    public void setLocation(List<List<Double>> location) {
        Double[] coordinates = new Double[location.size()];

        if (!location.isEmpty()) {
            int i = 0;
            for (List<Double> innerList : location) {
                for (Double coordinate : innerList) {
                    coordinates[i] = coordinate;
                    i++;
                }
            }
            binding.tvLocationx1.setText(String.format(Locale.getDefault(), "[%f,", coordinates[0]));
            binding.tvLocationx2.setText(String.format(Locale.getDefault(), "%f,", coordinates[1]));
            binding.tvLocationx3.setText(String.format(Locale.getDefault(), "%f,", coordinates[2]));
            binding.tvLocationx4.setText(String.format(Locale.getDefault(), "%f]", coordinates[3]));
        }
    }

    public void setArea(String area) {
        if (!area.isEmpty())
            binding.houseArea.setText(area);
    }

    public void setHouseRegistered(String registered) {
        if (!registered.isEmpty())
            binding.houseRegistered.setText(registered);
    }

    public void setTypeOfAsset(String typeOfAsset) {
        this.mTypeOfAsset = typeOfAsset;
    }

    /**
     * Set House pictures in to the prepared container.
     *
     * @param pictures a list of house pictures of a specific user.
     */
    public void setHousePictures(List<List<String>> pictures) {
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
            binding.myHouseAssetViewPager.setAdapter(adapter);
            binding.houseImageIndicator.setViewPager(binding.myHouseAssetViewPager);

        }
    }

    public void setAssetID(String id) {
        this.assetId = id;
    }
}
