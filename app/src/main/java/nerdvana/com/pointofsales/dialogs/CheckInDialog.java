package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.api_requests.WelcomeGuestRequest;
import nerdvana.com.pointofsales.api_responses.FetchCarResponse;
import nerdvana.com.pointofsales.api_responses.FetchGuestTypeResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.model.RoomTableModel;

public class CheckInDialog extends Dialog implements View.OnClickListener{
    private List<RoomRateMain> priceList;
    private RoomTableModel selectedRoom;
    private Spinner rateSpinner;
    private Spinner vehicleSpinner;
    private Spinner carSpinner;
    private Spinner guestTypeSpinner;

    private EditText customer;
    private EditText plateNumber;
    private EditText steward;
    private EditText child;
    private EditText adult;

    private Button checkin;

    private List<FetchCarResponse.Result> carList;
    private List<FetchVehicleResponse.Result> vehicleList;
    private List<FetchGuestTypeResponse.Result> guestTypeList;

    private Context context;

    private int roomRateId = 0;
    private int roomRatePriecId = 0;
    private int carId = 0;
    private int vehicleId = 0;
    private int guestTypeId = 0;
    public CheckInDialog(@NonNull Context context, RoomTableModel selectedRoom,
                         List<FetchCarResponse.Result> carList, List<FetchVehicleResponse.Result> vehicleList,
                         List<FetchGuestTypeResponse.Result> guestTypeList) {
        super(context);
        this.selectedRoom = selectedRoom;
        this.context = context;
        this.priceList = selectedRoom.getPrice();
        this.carList = carList;
        this.vehicleList = vehicleList;
        this.guestTypeList = guestTypeList;
    }

    public CheckInDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CheckInDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_checkin);

        child = findViewById(R.id.child);
        adult = findViewById(R.id.adult);
        customer = findViewById(R.id.customer);
        plateNumber = findViewById(R.id.plateNumber);
        steward = findViewById(R.id.steward);
        rateSpinner = findViewById(R.id.spinnerRates);
        vehicleSpinner = findViewById(R.id.spinnerVehicle);
        carSpinner = findViewById(R.id.spinnerCar);
        guestTypeSpinner = findViewById(R.id.spinnerGuestType);
        checkin = findViewById(R.id.checkin);
        checkin.setOnClickListener(this);



        setPriceSelection();
        setCarSelection();
        setVehicleSelection();
        setGuestTypeSelection();

    }

    private void setPriceSelection() {
        List<String> priceArray = new ArrayList<>();
        for (RoomRateMain rrm : priceList) {
            priceArray.add(String.format("%d - %s", rrm.getRatePrice().getAmount(), rrm.getRatePrice().getRoomRate().getRoomRate()));
        }
        CustomSpinnerAdapter rateSpinnerAdapter = new CustomSpinnerAdapter(context, R.id.spinnerItem,
                priceArray);
        rateSpinner.setAdapter(rateSpinnerAdapter);

        rateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                roomRateId = priceList.get(position).getRatePrice().getRoomRateId();
                roomRatePriecId = priceList.get(position).getRoomRatePriceId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCarSelection() {
        List<String> carArray = new ArrayList<>();
        for (FetchCarResponse.Result r : carList) {
            carArray.add(r.getCarMake());
        }
        CustomSpinnerAdapter carSpinnerAdapter = new CustomSpinnerAdapter(context, R.id.spinnerItem,
                carArray);
        carSpinner.setAdapter(carSpinnerAdapter);
        carSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                carId = carList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setVehicleSelection() {
        List<String> vehicleArray = new ArrayList<>();
        for (FetchVehicleResponse.Result r : vehicleList) {
            vehicleArray.add(r.getVehicle());
        }
        CustomSpinnerAdapter vehicleSpinnerAdapter = new CustomSpinnerAdapter(context, R.id.spinnerItem,
                vehicleArray);
        vehicleSpinner.setAdapter(vehicleSpinnerAdapter);
        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehicleId = vehicleList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setGuestTypeSelection() {
        List<String> guestTypeArray = new ArrayList<>();
        for (FetchGuestTypeResponse.Result r : guestTypeList) {
            guestTypeArray.add(r.getGuestType());
        }
        CustomSpinnerAdapter guestTypeAdapter = new CustomSpinnerAdapter(context, R.id.spinnerItem,
                guestTypeArray);
        guestTypeSpinner.setAdapter(guestTypeAdapter);

        guestTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                guestTypeId = guestTypeList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkin:
                welcomeGuestRequest();
                dismiss();
                break;
        }
    }

    private void welcomeGuestRequest() {
        BusProvider.getInstance().post(new WelcomeGuestRequest(
                String.valueOf(selectedRoom.getRoomId()),
                String.valueOf(selectedRoom.getRoomTypeId()),
                String.valueOf(roomRateId),
                String.valueOf(roomRatePriecId),
                String.valueOf(carId),
                String.valueOf(vehicleId),
                String.valueOf(guestTypeId),
                customer.getText().toString().trim(),
                plateNumber.getText().toString().trim(),
                steward.getText().toString().trim(),
                "",
                "",
                "1",
                SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_ID),
                adult.getText().toString(),
                child.getText().toString(),
                ".12"
        ));


    }


}
