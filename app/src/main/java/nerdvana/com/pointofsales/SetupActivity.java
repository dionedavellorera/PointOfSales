package nerdvana.com.pointofsales;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nerdvana.com.pointofsales.interfaces.PreloginContract;
import nerdvana.com.pointofsales.model.UserModel;
import nerdvana.com.pointofsales.prelogin.RightFrameFragment;

public class SetupActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private Button proceed;
    private Button setup;
    private EditText username;
    private EditText password;
    private static PreloginContract preloginContract;

    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        setup = findViewById(R.id.setup);
        setup.setOnClickListener(this);


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
                } else {
//                    preloginContract.loginFailed();
                }
                break;
            case R.id.setup:
                final Dialog setupDialog = new Dialog(this);
                setupDialog.setContentView(R.layout.dialog_setup);

                final EditText ipAddress = setupDialog.findViewById(R.id.ipAddress);
                final EditText branchName = setupDialog.findViewById(R.id.branchName);
                final EditText branchCode = setupDialog.findViewById(R.id.branchCode);
                final EditText serial = setupDialog.findViewById(R.id.serialNumber);
                Button proceed = setupDialog.findViewById(R.id.proceed);


                if (!TextUtils.isEmpty(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.HOST))) {
                    ipAddress.setText(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.HOST));
                    branchName.setText(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.BRANCH));
                    branchCode.setText(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.CODE));
                    serial.setText(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.SERIAL_NUMBER));
                }

                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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


//                        PosClient.changeApiBaseUrl(ipAddress.getText().toString() + "/"
//                                + branchName.getText().toString() + "/"
//                                + branchCode.getText().toString() + "/api/");
                        setupDialog.dismiss();
                    }
                });


                if (!setupDialog.isShowing()) setupDialog.show();
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
            if (TextUtils.isEmpty(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.HOST))) {
                Toast.makeText(SetupActivity.this, "Please setup first", Toast.LENGTH_SHORT).show();
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

        }
        return isValid;
    }
}
