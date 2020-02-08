package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.adapters.RoomRatesAdapter;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.custom.HidingEditText;

public abstract class RateDialog extends BaseDialog implements View.OnClickListener {
    private Timer timer;
    private Button update;
    private Spinner rateSpinner;
    private double amountSelected = 0;

    private Context mContext;

    private RoomRateImpl roomRateImpl;

    private RoomRatesAdapter roomRatesAdapter;
    private RecyclerView listRates;
    private RoomRateMain selectedRate;
    private List<RoomRateMain> roomRateMainList;
    private List<RoomRateMain> filteredRoomRateList;
    private HidingEditText searchRate;
    private Spinner qtySpinner;

    private String qtySelected = "1";
    public RateDialog(@NonNull Context context, List<RoomRateMain> roomRateMainList) {
        super(context);
        this.roomRateMainList = roomRateMainList;
        mContext = context;
        filteredRoomRateList = new ArrayList<>();
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.dialog_rates);
        setDialogLayout(R.layout.dialog_rates, "Add Rate");

        listRates = findViewById(R.id.listRates);
        searchRate = findViewById(R.id.searchRate);
        update = findViewById(R.id.update);
        update.setOnClickListener(this);
        rateSpinner = findViewById(R.id.rateSpinner);
        qtySpinner = findViewById(R.id.qty);
        List<String> roomRates = new ArrayList<>();

        roomRateImpl = new RoomRateImpl() {
            @Override
            public void clicked(RoomRateMain roomRateMain) {
                rateChangeSuccess(roomRateMain, qtySelected);
            }
        };

        qtySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                qtySelected = qtySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        for (RoomRateMain rrm : roomRateMainList) {
            roomRates.add(String.format(
                    "%s - %f",
                    rrm.getRatePrice().getRoomRate().getRoomRate(),
                    rrm.getRatePrice().getAmount()));
        }
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(mContext, R.id.spinnerItem, roomRates);
        rateSpinner.setAdapter(customSpinnerAdapter);
        rateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRate = roomRateMainList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filteredRoomRateList = new ArrayList<>();
                for (RoomRateMain rrm : roomRateMainList) {
                    if ((String.valueOf(rrm.getRatePrice().getAmount()).contains(searchRate.getText().toString().toLowerCase())) ||
                            rrm.getRatePrice().getRoomRate().getRoomRate().toLowerCase().contains(searchRate.getText().toString().toLowerCase())) {
                        filteredRoomRateList.add(rrm);
                    }
                }

                if (filteredRoomRateList.size() > 0) {
                    roomRatesAdapter = new RoomRatesAdapter(filteredRoomRateList, roomRateImpl);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    listRates.setLayoutManager(linearLayoutManager);
                    listRates.setAdapter(roomRatesAdapter);
                } else {
                    roomRatesAdapter = new RoomRatesAdapter(roomRateMainList, roomRateImpl);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    listRates.setLayoutManager(linearLayoutManager);
                    listRates.setAdapter(roomRatesAdapter);
                }
            }
        });

        setRateList();

        setQuantitySpinner();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update:
                rateChangeSuccess(selectedRate, qtySelected);
                break;
        }
    }

    public abstract void rateChangeSuccess(RoomRateMain selectedRate, String qty);

    private void setRateList() {
        roomRatesAdapter = new RoomRatesAdapter(roomRateMainList, roomRateImpl);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        listRates.setLayoutManager(linearLayoutManager);
        listRates.setAdapter(roomRatesAdapter);
    }

    public interface RoomRateImpl {
        void clicked(RoomRateMain roomRateMain);
    }

    private void setQuantitySpinner() {
        ArrayList<String> stringArray = new ArrayList<>();
        stringArray.add("1");
        stringArray.add("2");
        stringArray.add("3");
        stringArray.add("4");
        stringArray.add("5");
        stringArray.add("6");
        stringArray.add("7");
        stringArray.add("8");
        stringArray.add("9");
        stringArray.add("10");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, stringArray);
        qtySpinner.setAdapter(arrayAdapter);
    }

}
