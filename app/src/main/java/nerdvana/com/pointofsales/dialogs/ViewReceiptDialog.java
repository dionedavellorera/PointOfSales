package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import nerdvana.com.pointofsales.R;

public class ViewReceiptDialog extends BaseDialog {

    private Button btnSearch;

    public ViewReceiptDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_view_receipt, "View Receipt");
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewReceiptActualDialog viewReceiptActualDialog = new ViewReceiptActualDialog(getContext());
                viewReceiptActualDialog.show();
            }
        });
    }
}
