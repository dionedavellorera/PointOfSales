package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nerdvana.com.pointofsales.R;

public abstract class PasswordDialog extends Dialog implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private Button proceed;
    public PasswordDialog(@NonNull Context context) {
        super(context);
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
        setContentView(R.layout.dialog_password);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(this);

    }

    public abstract void passwordSuccess();
    public abstract void passwordFailed();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.proceed:
                passwordSuccess();
                dismiss();
                break;
        }
    }

}
