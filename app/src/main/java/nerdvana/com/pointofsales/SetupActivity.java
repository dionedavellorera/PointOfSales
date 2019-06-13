package nerdvana.com.pointofsales;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import nerdvana.com.pointofsales.api_requests.LoginRequest;
import nerdvana.com.pointofsales.api_requests.VerifyMachineRequest;
import nerdvana.com.pointofsales.api_responses.LoginResponse;
import nerdvana.com.pointofsales.api_responses.VerifyMachineResponse;
import nerdvana.com.pointofsales.dialogs.DialogProgressBar;
import nerdvana.com.pointofsales.dialogs.SetupDialog;
import nerdvana.com.pointofsales.interfaces.PreloginContract;
import nerdvana.com.pointofsales.model.UserModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetupActivity extends AppCompatActivity implements View.OnClickListener {

    private DialogProgressBar dialogProgressBar;

    private Button proceed;
    private ImageView setup;
    private EditText username;
    private EditText password;
    private TextView loginLabel;

    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);


        InputMethodManager imm = (InputMethodManager)getBaseContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.RESULT_HIDDEN,InputMethodManager.RESULT_HIDDEN);


        dialogProgressBar = new DialogProgressBar(SetupActivity.this);
        dialogProgressBar.setCancelable(false);

        loginLabel = findViewById(R.id.loginLabel);
        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(this);
        username = findViewById(R.id.username);
//        username.requestFocus();
//        username.setShowSoftInputOnFocus(false);
        password = findViewById(R.id.password);
//        password.requestFocus();
//        password.setShowSoftInputOnFocus(false);

        setup = findViewById(R.id.setup);
        setup.setOnClickListener(this);

//        loginLabel.setText("SERIAL: " + Build.SERIAL + "\n" +
//                "MODEL: " + Build.MODEL + "\n" +
//                "ID: " + Build.ID + "\n" +
//                "Manufacture: " + Build.MANUFACTURER + "\n" +
//                "brand: " + Build.BRAND + "\n" +
//                "type: " + Build.TYPE + "\n" +
//                "user: " + Build.USER + "\n" +
//                "BASE: " + Build.VERSION_CODES.BASE + "\n" +
//                "INCREMENTAL " + Build.VERSION.INCREMENTAL + "\n" +
//                "SDK  " + Build.VERSION.SDK + "\n" +
//                "BOARD: " + Build.BOARD + "\n" +
//                "BRAND " + Build.BRAND + "\n" +
//                "HOST " + Build.HOST + "\n" +
//                "FINGERPRINT: "+Build.FINGERPRINT + "\n" +
//                "Version Code: " + Build.VERSION.RELEASE + "\n");


        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.API_BASE_URL))) {
            PosClient.changeApiBaseUrl(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.API_BASE_URL));
        }



        userModel = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(this, ApplicationConstants.userSettings), UserModel.class);

        if (userModel != null) {
            if (userModel.isLoggedIn()) { //post login

                startActivity(new Intent(this, MainActivity.class));
                finish();




            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.proceed:
                if (validateLogin(username.getText().toString(), password.getText().toString())) {

                    sendLoginRequest(username.getText().toString(),
                                    password.getText().toString());
                }
                break;
            case R.id.setup:
                showSetupDialog();
                break;
        }
    }

    private boolean validateLogin(String username, String password) {
        boolean isValid = false;
        //system type option
        // room
        // table
        // checkout
        if (!TextUtils.isEmpty(username.trim()) && !TextUtils.isEmpty(password.trim())) {
            userModel = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(this, ApplicationConstants.userSettings), UserModel.class);
            if (TextUtils.isEmpty(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.MACHINE_ID))) {

                Utils.showDialogMessage(SetupActivity.this, "Machine not yet registered","Information");

                isValid = false;
            } else {
                isValid = true;
            }

        } else {

            Utils.showDialogMessage(SetupActivity.this, "Please enter username and password to proceed","Information");

        }
        return isValid;
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }



    @Subscribe
    public void apiError(ApiError apiError) {

        dismissProgress();

        Utils.showDialogMessage(SetupActivity.this, apiError.message(),"ERROR");
    }

    private void showSetupDialog() {

        SetupDialog setupDialog = new SetupDialog(SetupActivity.this);



        if (!setupDialog.isShowing()) {
            setupDialog.show();
            Window window = setupDialog.getWindow();
            window.setLayout((Utils.getDeviceWidth(SetupActivity.this) / 2), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void sendLoginRequest(String username, String password) {
        showPRogress();
        BusProvider.getInstance().post(new LoginRequest(
                username,
                password,
                ""
        ));
    }

    @Subscribe
    public void onReceiveLoginResponse(LoginResponse loginResponse) {
        dismissProgress();
        if (loginResponse.getStatus() == 0) {
            //fail
            Utils.showDialogMessage(SetupActivity.this, loginResponse.getMessage(),"Information");
//            Toast.makeText(getApplicationContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            //success
            UserModel userModel = new UserModel(loginResponse.getResult().get(0).getName(),
                    true,
                    SystemConstants.SYS_ROOM,
                    SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.HOST),
                    SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.BRANCH),
                    SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.CODE),
                    String.valueOf(loginResponse.getResult().get(0).getId()),
                    String.valueOf(loginResponse.getResult().get(0).getRols().getRole()),
                    String.valueOf(loginResponse.getResult().get(0).getRols().getId()));
            SharedPreferenceManager.saveString(SetupActivity.this, GsonHelper.getGson().toJson(userModel), ApplicationConstants.userSettings);
            SharedPreferenceManager.saveString(SetupActivity.this, String.valueOf(loginResponse.getResult().get(0).getUserId()), ApplicationConstants.USER_ID);

            startActivity(new Intent(this, MainActivity.class));
            finish();

        }
    }

    private void showPRogress() {
        if (dialogProgressBar != null) {
            if (!dialogProgressBar.isShowing()) dialogProgressBar.show();
        }
    }

    private void dismissProgress() {
        if (dialogProgressBar != null) {
            if (dialogProgressBar.isShowing()) dialogProgressBar.dismiss();
        }
    }
}
