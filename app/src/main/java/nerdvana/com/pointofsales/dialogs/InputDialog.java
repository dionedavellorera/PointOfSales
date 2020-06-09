package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.custom.HidingEditText;

public abstract class InputDialog extends BaseDialog implements View.OnClickListener{
    public static boolean IS_SHOWN = false;
    private HidingEditText etInput;
    private Button btnSearch;
    public InputDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_input, "INPUT");
        etInput = findViewById(R.id.etInput);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
        IS_SHOWN = true;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        IS_SHOWN = false;

    }

    @Override
    public void cancel() {
        super.cancel();
        IS_SHOWN = false;
    }

    public abstract void searchCompleted(String toSearch);

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                if (!TextUtils.isEmpty(etInput.getText().toString())) {
                    searchCompleted(etInput.getText().toString().trim());
                }
                break;
        }
    }
}
