package com.gebeya.smartcontract.myAsset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.Api;
import com.gebeya.smartcontract.App;
import com.gebeya.smartcontract.MainActivity;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.model.MakeTransactionModel;
import com.gebeya.smartcontract.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.myAsset.api.service.MakeTransactionService;

import java.util.List;
import java.util.Objects;

import butterknife.BindFloat;
import butterknife.BindView;
import butterknife.OnClick;
import customfonts.EditText_SFUI_Regular;
import customfonts.MyTextView_Roboto_Regular;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
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

    private MakeTransactionService mMakeTransactionService;
    private String mAssetId;
    private String mTo;
    private String mFrom;

    BoxStore userBox;
    Box<UserLoginData> box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_transaction);
        bind();

        // Hiding the success and failure image and message.
        failureImage.setVisibility(View.INVISIBLE);
        successImage.setVisibility(View.INVISIBLE);
        failureMessage.setVisibility(View.INVISIBLE);
        successMessage.setVisibility(View.INVISIBLE);


        // Extracting the intent passing from MyAssetFragment
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras!= null) {
            mAssetId = intent.getExtras().getString("ASSET_ID");
        }

        // Initialize an instance of the MakeTransactionService interface
        mMakeTransactionService = Api.makeTransactionService();

        // Retrieve the Box for the UserLogin
        userBox = ((App) getApplication()).getStore();
        box = userBox.boxFor(UserLoginData.class);


        // loads User
        List<UserLoginData> user = box.getAll();
        mFrom = user.get(0).getUserId();
    }

    @OnClick(R.id.submitAssetTransfer)
    public void submitAssetTransfer() {
        mTo = Objects.requireNonNull(idTo.getText()).toString().trim();

        mMakeTransactionService.makeTransaction(CONTENT_TYPE,
              mAssetId,
              mTo,
              mFrom).enqueue(new Callback<MakeTransactionModel>() {
            @Override
            public void onResponse(Call<MakeTransactionModel> call,
                                   Response<MakeTransactionModel> response) {
                int statusCode = response.code();
                String statusMessage = response.message();
                statusShow(statusCode, statusMessage);
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

}
