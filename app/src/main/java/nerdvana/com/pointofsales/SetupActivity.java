package nerdvana.com.pointofsales;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import nerdvana.com.pointofsales.api_requests.VerifyMachineRequest;
import nerdvana.com.pointofsales.api_responses.VerifyMachineResponse;
import nerdvana.com.pointofsales.interfaces.PreloginContract;
import nerdvana.com.pointofsales.model.UserModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetupActivity extends AppCompatActivity implements View.OnClickListener {

    private Dialog setupDialog;

    private View view;
    private Button proceed;
    private Button setup;
    private EditText username;
    private EditText password;
    private TextView loginLabel;
    private static PreloginContract preloginContract;

    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        loginLabel = findViewById(R.id.loginLabel);
        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

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

        userModel = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(this, ApplicationConstants.userSettings), UserModel.class);

        if (userModel != null) {
            if (userModel.isLoggedIn()) { //post login

                startActivity(new Intent(this, MainActivity.class));
                finish();

                PosClient.changeApiBaseUrl(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.API_BASE_URL));


            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.proceed:
                if (validateLogin(username.getText().toString(), password.getText().toString())) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
//                else {
//                    Toast.makeText(SetupActivity.this, "Machine number missing, please setup first", Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.setup:
                setupDialog = new Dialog(this);
                setupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                setupDialog.setContentView(R.layout.dialog_setup);

                final EditText ipAddress = setupDialog.findViewById(R.id.ipAddress);
                final EditText branchName = setupDialog.findViewById(R.id.branchName);
                final EditText branchCode = setupDialog.findViewById(R.id.branchCode);
                final EditText serial = setupDialog.findViewById(R.id.serialNumber);
                final Button proceed = setupDialog.findViewById(R.id.proceed);


                if (!TextUtils.isEmpty(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.HOST))) {
                    ipAddress.setText(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.HOST));
                    branchName.setText(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.BRANCH));
                    branchCode.setText(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.CODE));
                    serial.setText(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.SERIAL_NUMBER));
                }

                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!TextUtils.isEmpty(ipAddress.getText().toString().trim()) &&
                                !TextUtils.isEmpty(branchName.getText().toString().trim()) &&
                                !TextUtils.isEmpty(branchCode.getText().toString().trim()) &&
                                !TextUtils.isEmpty(serial.getText().toString().trim())) {

                            if (URLUtil.isValidUrl(String.format("%s/%s/%s/%s/",
                                    ipAddress.getText().toString(),
                                    "api",
                                    branchName.getText().toString(),
                                    branchCode.getText().toString()))) {

                                SharedPreferenceManager.saveString(SetupActivity.this,
                                        ipAddress.getText().toString(),ApplicationConstants.HOST);
                                SharedPreferenceManager.saveString(SetupActivity.this,
                                        branchName.getText().toString(),ApplicationConstants.BRANCH);
                                SharedPreferenceManager.saveString(SetupActivity.this,
                                        branchCode.getText().toString(),ApplicationConstants.CODE);
                                SharedPreferenceManager.saveString(SetupActivity.this,
                                        serial.getText().toString(),ApplicationConstants.SERIAL_NUMBER);

                                String apiBaseUrl = String.format("%s/%s/%s/%s/",
                                        ipAddress.getText().toString(),
                                        "api",
                                        branchName.getText().toString(),
                                        branchCode.getText().toString());
                                SharedPreferenceManager.saveString(SetupActivity.this, apiBaseUrl, ApplicationConstants.API_BASE_URL);
                                PosClient.changeApiBaseUrl(
                                        apiBaseUrl
                                );

                                sendVerifyMachineRequest(serial.getText().toString().toUpperCase());

                            } else {
                                Toast.makeText(SetupActivity.this, "Please enter valid url", Toast.LENGTH_SHORT).show();
                            }




                        } else {

                            Toast.makeText(SetupActivity.this, "Please fill up all fields", Toast.LENGTH_SHORT).show();

                        }


                    }
                });

                if (!setupDialog.isShowing()) {
                    setupDialog.show();
                    Window window = setupDialog.getWindow();
                    window.setLayout((Utils.getDeviceWidth(SetupActivity.this) / 2), ViewGroup.LayoutParams.WRAP_CONTENT);
                }

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
                Toast.makeText(SetupActivity.this, "Machine not yet registered", Toast.LENGTH_SHORT).show();
                isValid = false;
            } else {
                UserModel userModel = new UserModel(username,
                        true,
                        SystemConstants.SYS_ROOM,
                        SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.HOST),
                        SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.BRANCH),
                        SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.CODE));
                SharedPreferenceManager.saveString(SetupActivity.this, GsonHelper.getGson().toJson(userModel), ApplicationConstants.userSettings);
                isValid = true;
            }

        } else {
            Toast.makeText(SetupActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
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

    private void sendVerifyMachineRequest(String productKey) {
        VerifyMachineRequest request = new VerifyMachineRequest(
                productKey,
                Build.ID,
                Build.SERIAL,
                Build.MODEL,
                Build.MANUFACTURER,
                Build.BOARD
                );
        BusProvider.getInstance().post(request);
    }

    @Subscribe
    public void machineVerificationResponse(VerifyMachineResponse verifyMachineResponse) {
        if (verifyMachineResponse.getStatus() == 1) { //success
            SharedPreferenceManager.saveString(getApplicationContext(), String.valueOf(verifyMachineResponse.getResult().get(0).getId()), ApplicationConstants.MACHINE_ID);
            if (setupDialog != null) {
                if (setupDialog.isShowing()) setupDialog.dismiss();
            }
        }
        Toast.makeText(getApplicationContext(), verifyMachineResponse.getMesage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void apiError(ApiError apiError) {
        Toast.makeText(SetupActivity.this, apiError.message(), Toast.LENGTH_SHORT).show();
    }
}
