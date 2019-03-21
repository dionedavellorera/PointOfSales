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

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public abstract class ConfirmWithRemarksDialog extends Dialog {

    private EditText remarks;
    private Button save;

    public ConfirmWithRemarksDialog(@NonNull Context context) {
        super(context);
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
        setContentView(R.layout.dialog_confirm);

        remarks = findViewById(R.id.remarks);
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(remarks.getText().toString());
                dismiss();
            }
        });
    }

    public abstract void save(String remarks);
}