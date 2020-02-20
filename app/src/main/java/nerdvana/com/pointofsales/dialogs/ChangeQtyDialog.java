package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.CalculatorAdapter;
import nerdvana.com.pointofsales.custom.HidingEditText;
import nerdvana.com.pointofsales.interfaces.CalcuContract;
import nerdvana.com.pointofsales.model.CalcuModel;
import nerdvana.com.pointofsales.postlogin.adapter.ProductsAdapter;

public abstract class ChangeQtyDialog extends BaseDialog implements CalcuContract, View.OnClickListener {

    private RecyclerView rvCalculator;
    private HidingEditText qty;
    private ImageView setQty;

    LayoutAnimationController anim;

    private boolean isFirstUse = true;
    public ChangeQtyDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_change_qty, "Set Quantity");
        setQty = findViewById(R.id.setQty);
        setQty.setOnClickListener(this);

        rvCalculator = findViewById(R.id.rvCalculator);
        qty = findViewById(R.id.qty);
        qty.requestFocus();

        anim = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation);

        setCalcuAdapter();
    }

    private void setCalcuAdapter() {
        List<CalcuModel> calcuModelList = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            calcuModelList.add(new CalcuModel(String.valueOf(i), String.valueOf(i)));
        }
        calcuModelList.add(new CalcuModel("x", "X"));
        calcuModelList.add(new CalcuModel("0", "0"));
        calcuModelList.add(new CalcuModel("C", "C"));
        CalculatorAdapter calculatorAdapter = new CalculatorAdapter(calcuModelList, this, getContext());
        rvCalculator.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvCalculator.setAdapter(calculatorAdapter);
        rvCalculator.setLayoutAnimation(anim);
        calculatorAdapter.notifyDataSetChanged();
    }


    @Override
    public void clicked(String value) {
        if (!isFirstUse) {
            if (qty.getText().toString().equalsIgnoreCase("0")) {
                qty.setText("");
            }
            qty.setText(qty.getText().toString() + String.valueOf(value));
        } else {
            qty.setText(String.valueOf(value));
        }


        isFirstUse = false;

    }

    @Override
    public void subtract() {
        qty.setText(qty.getText().toString().substring(0, qty.getText().length() - 1));
        if (qty.getText().length() == 0) {
            qty.setText("0");
        } else {

        }
        isFirstUse = false;

    }

    @Override
    public void clear() {
        isFirstUse = false;
        qty.setText("0");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setQty:
                if (qty.getText().toString().length() == 0 || qty.getText().toString().equalsIgnoreCase("0")) {
                    qty.setText("1");
                }
                quantityChangeSuccess(qty.getText().toString());
                dismiss();
                break;
        }
    }

    public abstract void quantityChangeSuccess(String qty);
}
