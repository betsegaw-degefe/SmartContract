package com.gebeya.smartcontract.myAsset;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class SlidingImageAdapter extends PagerAdapter {

    private Context mContext;
    private String[] mImageUrls;


    public SlidingImageAdapter(Context context, String[] imageUrls) {
        this.mContext = context;
        this.mImageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return mImageUrls.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        Picasso.get()
              .load(mImageUrls[position])
              .fit()
              .centerCrop()
              .into(imageView);
        container.addView(imageView);

        return imageView;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
