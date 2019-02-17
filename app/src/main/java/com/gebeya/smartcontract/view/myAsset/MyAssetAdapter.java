package com.gebeya.smartcontract.view.myAsset;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gebeya.framework.utils.DateFormatter;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.databinding.MyCarAssetLayoutBinding;
import com.gebeya.smartcontract.databinding.MyHouseAssetLayoutBinding;
import com.gebeya.smartcontract.model.data.dto.CarDTO;
import com.gebeya.smartcontract.model.data.dto.HouseDTO;
import com.gebeya.smartcontract.model.data.dto.UserLoginResponseDTO;
import com.gebeya.smartcontract.view.publicLedger.PublicLedgerViewHolder;

import java.util.List;

public class MyAssetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CarDTO> mCars;
    private List<HouseDTO> mHouse;

    private UserLoginResponseDTO mUserResponseDTO;
    private MyAssetCallback mCallback;
    private Context mContext;
    private int counter = 0;
    private int layoutCounter = 0;


    public MyAssetAdapter(Context context,
                          List<CarDTO> cars,
                          List<HouseDTO> houses,
                          UserLoginResponseDTO userResponseDTO,
                          MyAssetCallback callback) {
        this.mContext = context;
        this.mCars = cars;
        this.mHouse = houses;
        this.mUserResponseDTO = userResponseDTO;
        this.mCallback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {


        if (mCars != null && layoutCounter < mCars.size()) {
            MyCarAssetLayoutBinding carBinding = DataBindingUtil.inflate(
                  LayoutInflater.from(viewGroup.getContext()),
                  R.layout.my_car_asset_layout,
                  viewGroup, false);

            layoutCounter++;

            return new MyAssetCarViewHolder(carBinding.getRoot(), mCallback, carBinding);
        } else {

            MyHouseAssetLayoutBinding houseBinding = DataBindingUtil.inflate(
                  LayoutInflater.from(viewGroup.getContext()),
                  R.layout.my_house_asset_layout,
                  viewGroup, false);

            return new MyAssetHouseViewHolder(houseBinding.getRoot(), mCallback, houseBinding);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyAssetCarViewHolder) {
            MyAssetCarViewHolder myAssetCarViewHolder = (MyAssetCarViewHolder) holder;
            int i = layoutCounter - 1;
            CarDTO car = mCars.get(i);

            myAssetCarViewHolder.setAssetId(car.getId());
            myAssetCarViewHolder.setTypeOfAsset("CAR");
            myAssetCarViewHolder.setAssetSpecification(car.getBrand());
            myAssetCarViewHolder.setAssetType(car.getModel() + ", " + car
                  .getYearOfManufactured());
            myAssetCarViewHolder.setAssetPictures(car.getPictures());

            DateFormatter dateFormatter = new DateFormatter();


            myAssetCarViewHolder.setAssetRegistered(dateFormatter
                  .DateFormatter(car.getCreatedAt()));
        } else if (holder instanceof MyAssetHouseViewHolder) {
            MyAssetHouseViewHolder myAssetHouseViewHolder = (MyAssetHouseViewHolder) holder;
            if (counter < mHouse.size()) {
                HouseDTO house = mHouse.get(counter);

                myAssetHouseViewHolder.setAssetID(house.getId());
                myAssetHouseViewHolder.setHousePictures(house.getPictures());
                myAssetHouseViewHolder.setTypeOfAsset("HOUSE");

                myAssetHouseViewHolder.setLocation(house.getGeoLocation());
                myAssetHouseViewHolder.setArea(house.getAreaM2().toString() + " Square meter.");

                DateFormatter dateFormatter = new DateFormatter();
                myAssetHouseViewHolder.setHouseRegistered(dateFormatter
                      .DateFormatter(house.getCreatedAt()));

                counter++;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCars.size() + mHouse.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
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

    /**
     * get the House Assets of a specific user from the API and update the transaction view.
     *
     * @param houses list of houses owned by a specific user will be loaded.
     */
    public void updateMyAssetHouse(List<HouseDTO> houses) {
        mHouse = houses;
        notifyDataSetChanged();
    }
}
