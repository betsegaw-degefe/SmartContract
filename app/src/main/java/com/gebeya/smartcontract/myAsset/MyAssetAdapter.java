package com.gebeya.smartcontract.myAsset;

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
import com.gebeya.smartcontract.data.dto.CarDTO;
import com.gebeya.smartcontract.data.dto.TransactionDTO;
import com.gebeya.smartcontract.data.dto.UserResponseDTO;

import java.util.List;

public class MyAssetAdapter extends RecyclerView.Adapter<MyAssetViewHolder> {

    private LayoutInflater inflater;
    private List<CarDTO> mCars;

    private UserResponseDTO mUserResponseDTO;
    private MyAssetCallback mCallback;
    private Context mContext;

    public MyAssetAdapter(Context context,
                          List<CarDTO> cars,
                          UserResponseDTO userResponseDTO) {
        this.mContext = context;
        this.mCars = cars;
        this.mUserResponseDTO = userResponseDTO;
    }

    @NonNull
    @Override
    public MyAssetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        inflater = LayoutInflater.from(context);
        final View itemView = inflater.inflate(R.layout.my_asset_layout,
              viewGroup, false);

        return new MyAssetViewHolder(itemView, mCallback);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyAssetViewHolder myAssetViewHolder, int position) {
        CarDTO car = mCars.get(position);

        myAssetViewHolder.setAssetSpecification(car.getBrand());
        myAssetViewHolder.setAssetType(car.getModel() + ", " + car.getYearOfManufactured());
        myAssetViewHolder.setAssetPictures(car.getPictures());

        DateFormatter dateFormatter = new DateFormatter();

        myAssetViewHolder.setAssetRegistered(dateFormatter.DateFormatter(car.getCreatedAt()));
    }

    @Override
    public int getItemCount() {
        return mCars.size();
    }


    /**
     * get the Car Assets from the API and update the transaction view.
     *
     * @param cars users specific car loaded from api using retrofit.
     */
    public void updateMyAssetCar(List<CarDTO> cars) {
        mCars = cars;
        notifyDataSetChanged();
    }
}
