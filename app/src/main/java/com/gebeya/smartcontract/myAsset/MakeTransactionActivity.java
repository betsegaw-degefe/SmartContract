package com.gebeya.smartcontract.myAsset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.Api;
import com.gebeya.framework.utils.ErrorUtils;
import com.gebeya.smartcontract.App;
import com.gebeya.smartcontract.MainActivity;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.model.data.dto.ErrorResponseDTO;
import com.gebeya.smartcontract.model.data.dto.MakeTransactionBodyDTO;
import com.gebeya.smartcontract.model.data.model.MakeTransactionModel;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.myAsset.api.service.MakeTransactionService;
import com.gebeya.smartcontract.view.searchUser.SearchUserActivity;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import customfonts.Button_sfuitext_regular;
import customfonts.EditText_SFUI_Regular;
import customfonts.MyTextView_Roboto_Regular;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gebeya.framework.utils.Constants.CONTENT_TYPE;

public class MakeTransactionActivity extends BaseActivity {

    // Id of the asset receiver.
    @BindView(R.id.idAssetTransfer)
    EditText_SFUI_Regular idTo;

    @BindView(R.id.transferFailure)
    ImageView failureImage;

    @BindView(R.id.transferSuccess)
    ImageView successImage;

    @BindView(R.id.successMessage)
    MyTextView_Roboto_Regular successMessage;

    @BindView(R.id.failureMessage)
    MyTextView_Roboto_Regular failureMessage;

    @BindView(R.id.searchUserIcon)
    ImageView searchUser;

    @BindView(R.id.submitAssetTransfer)
    Button_sfuitext_regular submitTransferButton;

    @BindView(R.id.progressViewMakeTransaction)
    CircularProgressView mProgressView;


    MakeTransactionBodyDTO mMakeTransactionBodyDTO = new MakeTransactionBodyDTO();

    private MakeTransactionService mMakeTransactionService;
    private String mAssetId;
    private String mTo;
    private String mFrom;
    private String userId;

    Animation shake;


    private static final String KEY_USER_ID = "USER_ID";
    private static final String KEY_ASSET_ID = "ASSET_ID";


    BoxStore userBox;
    Box<UserLoginData> box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_transaction);
        bind();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        // Hiding the success and failure image and message.
        failureImage.setVisibility(View.INVISIBLE);
        successImage.setVisibility(View.INVISIBLE);
        failureMessage.setVisibility(View.INVISIBLE);
        successMessage.setVisibility(View.INVISIBLE);


        // Extracting the intent passing from MyAssetFragment
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAssetId = intent.getExtras().getString(KEY_ASSET_ID);
            userId = intent.getExtras().getString(KEY_USER_ID);
            idTo.setText(userId);
        }

        // Initialize an instance of the MakeTransactionService interface
        mMakeTransactionService = Api.makeTransactionService();

        // Retrieve the Box for the UserLogin
        userBox = ((App) getApplication()).getStore();
        box = userBox.boxFor(UserLoginData.class);


        // loads User
        List<UserLoginData> user = box.getAll();
        mFrom = user.get(0).getUserId();

        // create instance of animation for editText
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        mProgressView.setVisibility(View.GONE);

    }

    @OnClick(R.id.submitAssetTransfer)
    public void submitAssetTransfer() {

        String id = Objects.requireNonNull(idTo.getText()).toString().trim();
        if (TextUtils.isEmpty(id)) {
            submitTransferButton.setBackground(this.getResources().getDrawable(R.drawable.button_blue_background));
            idTo.setError(getString(R.string.myAsset_Transfer_Asset_ID_hint));
            // shake the phone number edit text.
            idTo.startAnimation(shake);
            return;
        }
        mProgressView.setVisibility(View.VISIBLE);
        mTo = Objects.requireNonNull(idTo.getText()).toString().trim();
        submitTransferButton.setBackground(this.getResources().getDrawable(R.drawable.button_pressed));
        submitTransferButton.setEnabled(false);


        // loads User token from objectBox
        List<UserLoginData> users = box.getAll();
        String token = users.get(0).getToken();
        String bearerToken = "Bearer " + token;

        mMakeTransactionBodyDTO.setCarId(mAssetId);
        mMakeTransactionBodyDTO.setFrom(mFrom);
        mMakeTransactionBodyDTO.setTo(mTo);

        mMakeTransactionService.makeTransaction(bearerToken, CONTENT_TYPE,
              mMakeTransactionBodyDTO).enqueue(new Callback<MakeTransactionModel>() {
            @Override
            public void onResponse(Call<MakeTransactionModel> call,
                                   Response<MakeTransactionModel> response) {
                if (response.isSuccessful()) {
                    successImage.setVisibility(View.VISIBLE);
                    successMessage.setVisibility(View.VISIBLE);
                    mProgressView.setVisibility(View.GONE);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            openMainActivity();
                        }
                    }, 2000);

                } else {
                    ErrorResponseDTO errorResponse = ErrorUtils.parseError(response);
                    idTo.setError(errorResponse.getMessage());
                    failureImage.setVisibility(View.VISIBLE);
                    failureMessage.setVisibility(View.VISIBLE);
                    submitTransferButton.setEnabled(true);
                    submitTransferButton.setBackground(getResources().getDrawable(R.drawable.button_blue_background));
                    mProgressView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MakeTransactionModel> call,
                                  Throwable t) {
                d("Transaction failed to send to API");
                t.printStackTrace();

            }
        });
    }

    private void statusShow(int code, String Message) {
        String statusCode = Integer.toString(code);

        if (code == 201) {
            //startActivity(new Intent(this, MainActivity.class));
            successImage.setVisibility(View.VISIBLE);
            successMessage.setVisibility(View.VISIBLE);
            this.finish();
        } else {
            failureImage.setVisibility(View.VISIBLE);
            failureMessage.setVisibility(View.VISIBLE);
            toast(Message);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @OnClick(R.id.searchUserIcon)
    public void searchUserActivity() {
        Intent intent = new Intent(this, SearchUserActivity.class);
        intent.putExtra(KEY_ASSET_ID, mAssetId);
        startActivity(intent);
    }

    public void openMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

}
