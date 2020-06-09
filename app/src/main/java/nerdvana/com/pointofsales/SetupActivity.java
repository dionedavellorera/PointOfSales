package nerdvana.com.pointofsales;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import nerdvana.com.pointofsales.api_requests.LoginRequest;
import nerdvana.com.pointofsales.api_requests.RepatchDataRequest;
import nerdvana.com.pointofsales.api_responses.LoginResponse;
import nerdvana.com.pointofsales.custom.HidingEditText;
import nerdvana.com.pointofsales.custom.ImageFilePath;
import nerdvana.com.pointofsales.dialogs.DialogProgressBar;
import nerdvana.com.pointofsales.dialogs.SetupDialog;
import nerdvana.com.pointofsales.model.UserModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetupActivity extends AppCompatActivity implements View.OnClickListener {

    private DialogProgressBar dialogProgressBar;

    private Button proceed;
    private ImageView setup;
    private HidingEditText username;
    private EditText password;
    private TextView loginLabel;

    private ProgressBar progressBar;

    private UserModel userModel;

    private ImageView ivBannerImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
//        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1000);
        callRepatch();
        ivBannerImage = findViewById(R.id.ivBannerImage);
        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(SetupActivity.this, "saved_image"))) {

            Uri uri = Uri.parse(SharedPreferenceManager.getString(SetupActivity.this, "saved_image"));
            try {

                File direct = new File(SharedPreferenceManager.getString(SetupActivity.this, "saved_image"));
                Picasso.get().load(direct)
                        .fit()
                        .error(R.drawable.no_image)
                        .into(ivBannerImage);


            } catch (Exception e) {

            }


        }


        ivBannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(
                        SetupActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat
                            .requestPermissions(
                                    SetupActivity.this,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    1000);
                }
                else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2000);
                }

            }
        });
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
        dialogProgressBar = new DialogProgressBar(this);
        dialogProgressBar.setCancelable(false);

        loginLabel = findViewById(R.id.loginLabel);
        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        setup = findViewById(R.id.setup);
        setup.setOnClickListener(SetupActivity.this);


        InputMethodManager imm = (InputMethodManager)getBaseContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.RESULT_HIDDEN,InputMethodManager.RESULT_HIDDEN);

        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.API_BASE_URL))) {
            PosClient.changeApiBaseUrl(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.API_BASE_URL));
            PosClientCompany.changeApiBaseUrl(SharedPreferenceManager.getString(SetupActivity.this, ApplicationConstants.HOST)+"/api/");
        }
        userModel = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(this, ApplicationConstants.userSettings), UserModel.class);
        if (userModel != null) {
//            progressBar.setVisibility(View.GONE);
            if (userModel.isLoggedIn()) { //post login
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
//            else {
//                progressBar.setVisibility(View.VISIBLE);
//                disableViews();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        enableViews();
//                        progressBar.setVisibility(View.GONE);
//                    }
//                }, 3000);
//            }
        }
//        else {
//            progressBar.setVisibility(View.VISIBLE);
//            disableViews();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    enableViews();
//                    progressBar.setVisibility(View.GONE);
//                }
//            }, 3000);
//        }




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


    }

    private void callRepatch() {
        RepatchDataRequest repatchDataRequest = new RepatchDataRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<ResponseBody> repatchData = iUsers.repatchData(
                repatchDataRequest.getMapValue());
        repatchData.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void enableViews() {
        username.setEnabled(true);
        password.setEnabled(true);
        setup.setEnabled(true);
        proceed.setEnabled(true);
    }

    private void disableViews() {
        username.setEnabled(false);
        password.setEnabled(false);
        setup.setEnabled(false);
        proceed.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.proceed:
                if (validateLogin(username.getText().toString(), password.getText().toString())) {

                    sendLoginRequest(username.getText().toString(),
                                    password.getText().toString());

//                    throw new RuntimeException("Test Crash"); // Force a crash
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

    // Function to check and request permission
    public void checkPermission(String permission, int requestCode)
    {

        if (ContextCompat.checkSelfPermission(
                SetupActivity.this,
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            SetupActivity.this,
                            new String[] { permission },
                            requestCode);
        }
        else {
            Toast
                    .makeText(SetupActivity.this,
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();
        }

    }


    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {

        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // now, you have permission go ahead
            // TODO: something

        } else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SetupActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // now, user has denied permission (but not permanently!)

                Utils.showDialogMessage(SetupActivity.this, "File Storage permission required", "WARNING");



            } else {

                // now, user has denied permission permanently!

                Utils.showDialogMessage(SetupActivity.this, "File Storage permission required, Please enable in settings", "WARNING");


                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 1000);
            }

        }
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
            SharedPreferenceManager.saveString(SetupActivity.this, String.valueOf(loginResponse.getResult().get(0).getUsername()), ApplicationConstants.USERNAME);
            SharedPreferenceManager.saveString(SetupActivity.this, String.valueOf(loginResponse.getResult().get(0).getRols().getGroup().getAccess()), ApplicationConstants.ACCESS_RIGHTS);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();


                Log.d("URIDATARESULT", uri.toString());


                String realPath = ImageFilePath.getPath(SetupActivity.this, data.getData());
//                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                SharedPreferenceManager.saveString(SetupActivity.this, realPath, "saved_image");

                Log.d("QKWKWK", "onActivityResult: file path : " + realPath);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                    // Log.d(TAG, String.valueOf(bitmap));


                    Picasso.get().load(uri)
                            .fit()
                            .error(R.drawable.no_image)
                            .into(ivBannerImage);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            //TODO: action
        }
    }
}
