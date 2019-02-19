package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.adapters.RoomRatesAdapter;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;

public abstract class RateDialog extends Dialog implements View.OnClickListener {
    private Timer timer;
    private Button update;
    private Spinner rateSpinner;
    private double amountSelected = 0;

    private Context mContext;


    private RoomRatesAdapter roomRatesAdapter;
    private RecyclerView listRates;
    private RoomRateMain selectedRate;
    private List<RoomRateMain> roomRateMainList;
    private List<RoomRateMain> filteredRoomRateList;
    private android.support.v7.widget.SearchView searchRate;
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
        setContentView(R.layout.dialog_rates);


        listRates = findViewById(R.id.listRates);
        searchRate = findViewById(R.id.searchRate);
        update = findViewById(R.id.update);
        update.setOnClickListener(this);
        rateSpinner = findViewById(R.id.rateSpinner);
        List<String> roomRates = new ArrayList<>();
        for (RoomRateMain rrm : roomRateMainList) {
            roomRates.add(String.format(
                    "%s - %d",
                    rrm.getRatePrice().getRoomRate().getRoomRate(),
                    rrm.getRatePrice().getAmount()));
        }
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(mContext, R.id.spinnerItem, roomRates);
        rateSpinner.setAdapter(customSpinnerAdapter);
        rateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRate = roomRateMainList.get(position);}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchRate.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                filteredRoomRateList = new ArrayList<>();
                for (RoomRateMain rrm : roomRateMainList) {
                    if ((String.valueOf(rrm.getRatePrice().getAmount()).contains(s)) ||
                            rrm.getRatePrice().getRoomRate().getRoomRate().toLowerCase().contains(s.toLowerCase())) {
                        filteredRoomRateList.add(rrm);
                    }
                }


                if (filteredRoomRateList.size() > 0) {
                    roomRatesAdapter = new RoomRatesAdapter(filteredRoomRateList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    listRates.setLayoutManager(linearLayoutManager);
                    listRates.setAdapter(roomRatesAdapter);


                } else {
                    roomRatesAdapter = new RoomRatesAdapter(roomRateMainList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    listRates.setLayoutManager(linearLayoutManager);
                    listRates.setAdapter(roomRatesAdapter);
                }


//                if (timer != null) {
//                    timer.cancel();
//                }
//                timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//
//
//
//                    }
//                }, 1000); // 600ms delay before the timer executes the „run“ method from TimerTask
                return false;
            }
        });

        setRateList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update:
                rateChangeSuccess(selectedRate);
                break;
        }
    }

    public abstract void rateChangeSuccess(RoomRateMain selectedRate);

    private void setRateList() {
        roomRatesAdapter = new RoomRatesAdapter(roomRateMainList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        listRates.setLayoutManager(linearLayoutManager);
        listRates.setAdapter(roomRatesAdapter);
    }

}
