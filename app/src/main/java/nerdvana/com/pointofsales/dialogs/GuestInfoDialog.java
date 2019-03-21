package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.R;

public class GuestInfoDialog extends Dialog {
    private Button updateGuestInfo;

    public GuestInfoDialog(@NonNull Context context) {
        super(context);
    }

    public GuestInfoDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected GuestInfoDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_guest_info);

        updateGuestInfo = findViewById(R.id.updateGuestInfo);

        updateGuestInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
        Dialog dialog = this;
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
