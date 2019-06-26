package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.model.RoomTableModel;

public abstract class ConfirmCheckInDialog extends BaseDialog {

    private Button confirm;
    private RoomTableModel selectedRoom;
    public ConfirmCheckInDialog(@NonNull Context context, RoomTableModel selectedRoom) {
        super(context);
        this.selectedRoom = selectedRoom;
    }

    public ConfirmCheckInDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ConfirmCheckInDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_confirm_checkin);
        setDialogLayout(R.layout.dialog_confirm_checkin, "CONFIRM CHECK IN");
        confirm = findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setCancelable(false);
    }

    public abstract void checkinSuccess(String roomRatePriceId);
    public abstract void checkinFailed();
}
