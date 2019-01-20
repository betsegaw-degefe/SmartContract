package com.gebeya.smartcontract.profile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gebeya.framework.base.BaseFragment;
import com.gebeya.framework.utils.DateFormatter;
import com.gebeya.smartcontract.App;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.view.changePassword.ChangePasswordActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ProfileFragment extends BaseFragment {

    @BindView(R.id.profilePicture)
    com.alexzh.circleimageview.CircleImageView profilePicture;

    @BindView(R.id.profileUserId)
    TextView id;

    @BindView(R.id.profileFirstName)
    TextView firstName;

    @BindView(R.id.profileLastName)
    TextView lastName;

    @BindView(R.id.profilePhoneNumber)
    TextView phoneNumber;

    @BindView(R.id.profileRegistered)
    TextView registered;

    @BindView(R.id.changePasswordProfile)
    TextView changePassword;


    BoxStore userBox;
    Box<UserLoginData> box;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the Box for the UserLogin.
        userBox = ((App) Objects.requireNonNull(getActivity()).getApplicationContext()).getStore();
        box = userBox.boxFor(UserLoginData.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate(R.layout.fragment_profile, container);

        List<UserLoginData> users = box.getAll();

        firstName.setText(users.get(0).getFirstName());
        lastName.setText(users.get(0).getLastName());
        phoneNumber.setText(users.get(0).getPhoneNo());

        DateFormatter dateFormatter = new DateFormatter();
        registered.setText(dateFormatter.DateFormatter(users.get(0).getRegistered()));

        id.setText(users.get(0).getUserId());
        Picasso.get()
              .load(users.get(0).getProfilePic())
              .fit()
              .centerCrop()
              .into(profilePicture);
        return root;
    }

    @OnClick(R.id.changePasswordProfile)
    public void openChangePasswordActivity() {
        startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
    }
}
