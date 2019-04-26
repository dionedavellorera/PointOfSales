package nerdvana.com.pointofsales.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Utils;
import okhttp3.internal.Util;

public abstract class RemoveOtDialog extends BaseDialog {

    private TextView oldOtValue;
    private EditText newOtValue;

    private Button updateOverTime;

    private String overTimeValue;

    private Activity activity;

    public RemoveOtDialog(@NonNull Context context, String overTimeValue, Activity activity) {
        super(context);
        this.overTimeValue = overTimeValue;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_remove_overtime, "CANCEL OVERTIME");
        oldOtValue = findViewById(R.id.oldOtValue);
        newOtValue = findViewById(R.id.newOtValue);
        updateOverTime = findViewById(R.id.updateOverTime);
        oldOtValue.setText(overTimeValue);

        updateOverTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(newOtValue.getText().toString())) {
                    if (Integer.valueOf(newOtValue.getText().toString()) > Integer.valueOf(overTimeValue) || Integer.valueOf(newOtValue.getText().toString()) == Integer.valueOf(overTimeValue)) {
                        Utils.showDialogMessage(activity, "Cannot accept OT greater or equal than the real OT hours" ,"Information");
                    } else {
                        removeOtSuccess(overTimeValue, newOtValue.getText().toString());
                        dismiss();
                    }
                }
            }
        });


    }

    public abstract void removeOtSuccess(String oldOtValue, String remainingOt);


}
