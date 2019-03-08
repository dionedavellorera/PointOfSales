package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

import nerdvana.com.pointofsales.R;

public class PluDialog extends Dialog {
    public PluDialog(@NonNull Context context) {
        super(context);
    }

    public PluDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PluDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_plu);
    }
}