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

public class MyAssetHouseViewHolder extends RecyclerView.ViewHolder
      implements View.OnClickListener {


    /**
     * Binding with house asset layout components.
     */

    @BindView(R.id.houseArea)
    TextView mHouseArea;

    @BindView(R.id.houseRegistered)
    TextView mHouseRegestered;

    @BindView(R.id.tvLocationx1)
    TextView mCoordinate1;

    @BindView(R.id.tvLocationx2)
    TextView mCoordinate2;

    @BindView(R.id.tvLocationx3)
    TextView mCoordinate3;

    @BindView(R.id.tvLocationx4)
    TextView mCoordinate4;

    @BindView(R.id.myHouseAssetViewPager)
    ViewPager mHouseViewPager;

    @BindView(R.id.houseImageIndicator)
    CirclePageIndicator houseImageIndicator;

    private String mTypeOfAsset;


    /**
     * Declaring local variables.
     */

    private MyAssetCallback mCallback;
    private Context mContext;
    private String assetId;

    public MyAssetHouseViewHolder(@NonNull View itemView, MyAssetCallback callback) {
        super(itemView);

        this.mCallback = callback;
        mContext = itemView.getContext();
        ButterKnife.bind(this, itemView);
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
            mCoordinate1.setText(String.format("[%f,", coordinates[0]));
            mCoordinate2.setText(String.format("%f,", coordinates[1]));
            mCoordinate3.setText(String.format("%f,", coordinates[2]));
            mCoordinate4.setText(String.format("%f]", coordinates[3]));
        }
    }

    public void setArea(String area) {
        if (!area.isEmpty())
            mHouseArea.setText(area);
    }

    public void setHouseRegistered(String registered) {
        if (!registered.isEmpty())
            mHouseRegestered.setText(registered);
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
            mHouseViewPager.setAdapter(adapter);
            houseImageIndicator.setViewPager(mHouseViewPager);

        }
    }

    public void setAssetID(String id) {
        this.assetId = id;
    }
}
