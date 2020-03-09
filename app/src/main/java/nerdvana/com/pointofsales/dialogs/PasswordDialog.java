package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_requests.LoginRequest;
import nerdvana.com.pointofsales.api_responses.LoginResponse;
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
        setDialogLayout(R.layout.dialog_password, headerAppend);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(this);

        setCancelable(false);

        username.requestFocus();

    }

    public abstract void passwordSuccess(String employeeId, String employeeName);
    public abstract void passwordFailed();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.proceed:
                if (TextUtils.isEmpty(username.getText().toString()) ||
                        TextUtils.isEmpty(password.getText().toString())) {
                    if (getContext() != null) {
                        Utils.showDialogMessage(getContext(), "Please provide username and password", "Missing information!");
                    }
                } else {
                    sendLoginRequest(username.getText().toString(), password.getText().toString());
                }

//                passwordSuccess();
//                dismiss();
                break;
        }
    }

    private void sendLoginRequest(String username, String password) {


        LoginRequest loginRequest = new LoginRequest(username, password, "");
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<LoginResponse> request = iUsers.sendLoginRequest(loginRequest.getMapValue());
        request.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getStatus() == 0) {
                    //fail
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    cancel();
                } else {
                    if (response.body().getResult().get(0).getRols().getId() == 2 ||
                            response.body().getResult().get(0).getRols().getId() == 5 ||
                            response.body().getResult().get(0).getRols().getId() == 22 ||
                            response.body().getResult().get(0).getRols().getId() == 27 ||
                            response.body().getResult().get(0).getRols().getId() == 32 ||
                            response.body().getResult().get(0).getRols().getId() == 39 ||
                            response.body().getResult().get(0).getRols().getId() == 45 ||
                            response.body().getResult().get(0).getRols().getId() == 46 ||
                            response.body().getResult().get(0).getRols().getId() == 48 ||
                            response.body().getResult().get(0).getRols().getId() == 49 ||
                            response.body().getResult().get(0).getRols().getId() == 50 ||
                            response.body().getResult().get(0).getRols().getId() == 57 ||
                            response.body().getResult().get(0).getRols().getId() == 59 ||
                            response.body().getResult().get(0).getRols().getId() == 61 ||
                            response.body().getResult().get(0).getRols().getId() == 62 ||
                            response.body().getResult().get(0).getRols().getId() == 95 ||
                            response.body().getResult().get(0).getRols().getId() == 112 ||
                            response.body().getResult().get(0).getRols().getId() == 113 ||
                            response.body().getResult().get(0).getRols().getId() == 114) {
                        passwordSuccess(String.valueOf(response.body().getResult().get(0).getUserId()), response.body().getResult().get(0).getName());
                        dismiss();
                    } else {
                        Toast.makeText(context, "You are not authorized to alter this transaction", Toast.LENGTH_SHORT).show();
                        cancel();
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                cancel();
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
            dialog.getWindow().setLayout(600, height);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }
}
