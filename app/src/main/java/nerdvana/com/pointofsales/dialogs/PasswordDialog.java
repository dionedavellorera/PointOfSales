package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epson.epos2.cashchanger.DirectIOListener;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_requests.CheckPermissionRequest;
import nerdvana.com.pointofsales.api_requests.LoginRequest;
import nerdvana.com.pointofsales.api_responses.CheckPermissionResponse;
import nerdvana.com.pointofsales.api_responses.LoginResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class PasswordDialog extends BaseDialog implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private Button proceed;
    private Context context;
    private String headerAppend = "";
    private String actionId = "";
    public PasswordDialog(@NonNull Context context, String actionId) {
        super(context);
        this.context = context;
        this.actionId = actionId;
    }

    public PasswordDialog(@NonNull Context context, String headerAppend,
                          String actionId) {
        super(context);
        this.context = context;
        this.headerAppend = headerAppend;
        this.actionId = actionId;
    }

    public PasswordDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PasswordDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_password);
        setDialogLayout(R.layout.dialog_password, headerAppend + "ADMIN PASSWORD");
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(this);

        setCancelable(false);

    }

    public abstract void passwordSuccess(String employeeId, String employeeName);
    public abstract void passwordFailed();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.proceed:
                if (TextUtils.isEmpty(username.getText().toString()) ||
                        TextUtils.isEmpty(password.getText().toString())) {
                    Utils.showDialogMessage(((MainActivity)context), "Please provide username and password", "Missing information!");
                } else {
                    sendLoginRequest(username.getText().toString(), password.getText().toString());
                }

//                passwordSuccess();
//                dismiss();
                break;
        }
    }

    private void sendLoginRequest(String username, String password) {
//        CheckPermissionRequest checkPermissionRequest =  new CheckPermissionRequest(username, password, actionId);
//        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
//        Call<CheckPermissionResponse> request = iUsers.checkPermission(checkPermissionRequest.getMapValue());
//        request.enqueue(new Callback<CheckPermissionResponse>() {
//            @Override
//            public void onResponse(Call<CheckPermissionResponse> call, Response<CheckPermissionResponse> response) {
//                if (response.body().getStatus() == 0) {
//                    Utils.showDialogMessage(((MainActivity)context), response.body().getMessage(), "Warning!");
//                } else {
//                    TODO :
//                    passwordSuccess("656", "DIONEY");
//                }
//                dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<CheckPermissionResponse> call, Throwable t) {
//
//            }
//        });

        LoginRequest loginRequest = new LoginRequest(username, password, actionId);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<LoginResponse> request = iUsers.sendLoginRequest(loginRequest.getMapValue());
        request.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getStatus() == 0) {
                    //fail
                    Utils.showDialogMessage(((MainActivity)context), response.body().getMessage(), "Warning!");
//                    Toast.makeText(getApplicationContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    passwordSuccess(String.valueOf(response.body().getResult().get(0).getUserId()), response.body().getResult().get(0).getName());
                }
                dismiss();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
        Dialog dialog = this;
        if (dialog != null) {
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(300, height);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }
}
