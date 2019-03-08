package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.model.CartItemsModel;

public abstract class OpenPriceDialog extends Dialog {
    private TextView name;
    private TextView oldPrice;
    private EditText newPrice;
    private EditText newQty;
    private Button submit;
    private CartItemsModel cartItemsModel;
    private int selectedItemPosition = 0;
    public OpenPriceDialog(@NonNull Context context, CartItemsModel cartItemsModel, int position) {
        super(context);
        this.cartItemsModel = cartItemsModel;
        this.selectedItemPosition = position;
    }

    public OpenPriceDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected OpenPriceDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_openprice);
        name = findViewById(R.id.name);
        oldPrice = findViewById(R.id.oldPrice);
        newPrice = findViewById(R.id.newPrice);
        newQty = findViewById(R.id.newQty);
        submit = findViewById(R.id.submit);
        name.setText(cartItemsModel.getName());
        oldPrice.setText(String.valueOf(cartItemsModel.getAmount()));
        newPrice.setText(String.valueOf(cartItemsModel.getAmount()));
        newQty.setText(String.valueOf(cartItemsModel.getQuantity()));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(newPrice.getText().toString())) {
                    openPriceChangeSuccess(Integer.valueOf(newQty.getText().toString()), Double.valueOf(newPrice.getText().toString()), selectedItemPosition);
                }

            }
        });
    }

    public abstract void openPriceChangeSuccess(int quantity, Double newPrice, int position);
}