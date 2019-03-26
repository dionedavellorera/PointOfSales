package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import nerdvana.com.pointofsales.R;

public class DiscountDialog extends BaseDialog {

    public DiscountDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_discount, "DISCOUNT");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Dialog dialog = this;
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
