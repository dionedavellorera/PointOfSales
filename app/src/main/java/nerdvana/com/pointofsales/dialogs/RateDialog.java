package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.RoomRate;

public abstract class RateDialog extends Dialog implements View.OnClickListener {

    private Button update;
    private Spinner rateSpinner;
    private double amountSelected = 0;

    private List<RoomRate> roomRateList;
    public RateDialog(@NonNull Context context, List<RoomRate> roomRateList) {
        super(context);
        this.roomRateList = roomRateList;
    }

    public RateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected RateDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rates);

        update = findViewById(R.id.update);
        update.setOnClickListener(this);
        rateSpinner = findViewById(R.id.rateSpinner);

        rateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                amountSelected = roomRateList.get(position).getRoomRate().getAmount().getAmount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update:
                rateChangeSuccess(amountSelected);
                break;
        }
    }

    public abstract void rateChangeSuccess(double amount);
}
