package nerdvana.com.pointofsales.prelogin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.SystemConstants;
import nerdvana.com.pointofsales.interfaces.PreloginContract;
import nerdvana.com.pointofsales.model.UserModel;

public class RightFrameFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button proceed;
    private EditText username;
    private EditText password;
    private static PreloginContract preloginContract;

    public static RightFrameFragment newInstance(PreloginContract preloginContract) {
        RightFrameFragment rightFrameFragment = new RightFrameFragment();
        RightFrameFragment.preloginContract = preloginContract;
        return rightFrameFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.prelogin_right_frame, container, false);
        proceed = view.findViewById(R.id.proceed);
        proceed.setOnClickListener(this);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.proceed:
                if (validateLogin(username.getText().toString(), password.getText().toString())) {
                    RightFrameFragment.preloginContract.loginSuccess();
                } else {
                    RightFrameFragment.preloginContract.loginFailed();
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
//            UserModel userModel = new UserModel(username, true, SystemConstants.SYS_ROOM);
//            SharedPreferenceManager.saveString(getContext(), GsonHelper.getGson().toJson(userModel), ApplicationConstants.userSettings);
            isValid = true;
        }
        return isValid;
    }

}
