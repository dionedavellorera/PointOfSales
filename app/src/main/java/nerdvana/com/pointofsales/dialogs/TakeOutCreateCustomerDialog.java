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

public abstract class TakeOutCreateCustomerDialog extends Dialog {
    private EditText name;
    private Button create;
    public TakeOutCreateCustomerDialog(@NonNull Context context) {
        super(context);
    }

    public TakeOutCreateCustomerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected TakeOutCreateCustomerDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_take_out_create_customer);

        name = findViewById(R.id.name);
        create = findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSuccess(name.getText().toString());
            }
        });
    }

    public abstract void createSuccess(String customerName);
}
