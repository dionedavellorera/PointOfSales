package nerdvana.com.pointofsales.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Utils;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public abstract class ConfirmWithRemarksDialog extends BaseDialog {

    private EditText remarks;
    private Button save;
    private Context context;
    private TextView header;
    private Boolean isRequired;
    public ConfirmWithRemarksDialog(@NonNull Context context) {
        super(context);
    }

    public ConfirmWithRemarksDialog(@NonNull Context context, boolean isRequired) {
        super(context);
        this.context = context;
        this.isRequired = isRequired;
    }

    public ConfirmWithRemarksDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ConfirmWithRemarksDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_confirm);
        setDialogLayout(R.layout.dialog_confirm, "CONFIRM");
        remarks = findViewById(R.id.remarks);
        save = findViewById(R.id.save);
        header = findViewById(R.id.header);

        if (isRequired != null) {
            if (isRequired) {
                if (TextUtils.isEmpty(remarks.getText().toString().trim())) {
                    header.setText("Please provide remarks(required)");
                } else {
                    header.setText("Please provide remarks(optional");
                }
            } else {
                header.setText("Please provide remarks(optional");
            }
        } else {
            header.setText("Please provide remarks(optional");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRequired != null) {
                    if (isRequired) {
                        if (TextUtils.isEmpty(remarks.getText().toString().trim())) {
                            Utils.showDialogMessage(context, "Remarks is required", "Information");
                        } else {
                            save(remarks.getText().toString());
                            dismiss();
                        }
                    } else {
                        save(remarks.getText().toString());
                        dismiss();
                    }
                } else {
                    save(remarks.getText().toString());
                    dismiss();
                }

            }
        });

        setCancelable(false);
    }

    public abstract void save(String remarks);

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
}
