package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import nerdvana.com.pointofsales.R;

public class ReprintActualZReadReceiptDialog extends BaseDialog {
    public ReprintActualZReadReceiptDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_reprint_z_read, "REPRINT Z READ");

        Button btnSearch = findViewById(R.id.search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZXActualDialog zxActualDialog = new ZXActualDialog(getContext(), "z");
                zxActualDialog.show();
            }
        });
    }
}
