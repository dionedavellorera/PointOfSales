package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.model.CartItemsModel;

public abstract class OpenPriceDialog extends BaseDialog {
    private TextView name;
    private TextView oldPrice;
    private EditText newPrice;
    private EditText newQty;
    private Button submit;
    private TextView qtyLabel;
    private CartItemsModel cartItemsModel;
    private Boolean isPosted;
    private int selectedItemPosition = 0;
    private String type;
    public OpenPriceDialog(@NonNull Context context,
                           CartItemsModel cartItemsModel,
                           int position,
                           boolean isPosted,
                           String type) {
        super(context);
        this.cartItemsModel = cartItemsModel;
        this.selectedItemPosition = position;
        this.isPosted = isPosted;
        this.type = type;
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
        setDialogLayout(R.layout.dialog_openprice, "OPEN PRICE");
        name = findViewById(R.id.name);
        oldPrice = findViewById(R.id.oldPrice);
        newPrice = findViewById(R.id.newPrice);
        newQty = findViewById(R.id.newQty);
        qtyLabel = findViewById(R.id.qtyLabel);
        submit = findViewById(R.id.submit);
        name.setText(cartItemsModel.getName());
        oldPrice.setText(String.valueOf(cartItemsModel.getUnitPrice()));
        newPrice.setText(String.valueOf(cartItemsModel.getUnitPrice()));
        newQty.setText(String.valueOf(cartItemsModel.getQuantity()));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(newQty.getText().toString()) && !TextUtils.isEmpty(newPrice.getText().toString())) {
                    if (Double.valueOf(newPrice.getText().toString()).equals(cartItemsModel.getUnitPrice()) &&
                        Integer.valueOf(newQty.getText().toString()) == cartItemsModel.getQuantity()) {
                        Toast.makeText(getContext(), "No quantity / price to update", Toast.LENGTH_SHORT).show();
                    } else {
                        if (type.equalsIgnoreCase("price")) {
                            if (Integer.valueOf(newQty.getText().toString()) > cartItemsModel.getQuantity()) {
                                Toast.makeText(getContext(), "Please punch new item instead of adding", Toast.LENGTH_SHORT).show();
                            } else {
                                openPriceChangeSuccess(
                                        Integer.valueOf(newQty.getText().toString()),
                                        Double.valueOf(newPrice.getText().toString()),
                                        selectedItemPosition,
                                        type);
                            }
                        } else {
                            openPriceChangeSuccess(
                                    Integer.valueOf(newQty.getText().toString()),
                                    Double.valueOf(newPrice.getText().toString()),
                                    selectedItemPosition,
                                    type);
                        }
                        Toast.makeText(getContext(), "Please update", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Please put quantity and amount", Toast.LENGTH_SHORT).show();
                }


//                if (!TextUtils.isEmpty(newPrice.getText().toString())) {
//                    openPriceChangeSuccess(
//                            Integer.valueOf(newQty.getText().toString()),
//                            Double.valueOf(newPrice.getText().toString()),
//                            selectedItemPosition,
//                            type);
//                }

            }
        });

        if (type.equalsIgnoreCase("price")) {
            newQty.setEnabled(false);
            newPrice.setEnabled(true);
        } else if (type.equalsIgnoreCase("qty")) {
            newQty.setEnabled(true);
            newPrice.setEnabled(false);
        }

//        if (isPosted) {
//            newQty.setVisibility(View.GONE);
//            qtyLabel.setVisibility(View.GONE);
//        }
    }

    public abstract void openPriceChangeSuccess(int quantity, Double newPrice, int position, String type);
}
