package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;

import nerdvana.com.pointofsales.R;

public class DialogSettings extends BaseDialog {

    public DialogSettings(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_settings, "SETTINGS");


    }
}
