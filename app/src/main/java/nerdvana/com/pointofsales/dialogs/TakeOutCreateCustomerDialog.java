package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.api_responses.FetchRoomAreaResponse;

public abstract class TakeOutCreateCustomerDialog extends BaseDialog {
    private EditText name;
    private Button create;
    private Spinner roomAreaSpinner;
    private List<FetchRoomAreaResponse.Result> roomAreaList;
    private Context context;
    private int areaId = 0;
    public TakeOutCreateCustomerDialog(@NonNull Context context, List<FetchRoomAreaResponse.Result> roomAreaList) {
        super(context);
        this.context = context;
        this.roomAreaList = roomAreaList;

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
//        setContentView(R.layout.dialog_take_out_create_customer);
        setDialogLayout(R.layout.dialog_take_out_create_customer, "CUSTOMER INFO");
        roomAreaSpinner = findViewById(R.id.roomArea);
        name = findViewById(R.id.name);
        create = findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSuccess(name.getText().toString(), areaId);
            }
        });

        setRoomArea();

    }

    public abstract void createSuccess(String customerName, int areaId);

    private void setRoomArea() {
        List<String> areaArray = new ArrayList<>();
        for (FetchRoomAreaResponse.Result area : roomAreaList) {
            areaArray.add(area.getRoomArea());
        }
        CustomSpinnerAdapter rateSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                areaArray);
        roomAreaSpinner.setAdapter(rateSpinnerAdapter);

        roomAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaId = roomAreaList.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
