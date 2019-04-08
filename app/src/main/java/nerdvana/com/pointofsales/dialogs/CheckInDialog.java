package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import nerdvana.com.pointofsales.api_responses.FetchNationalityResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomAreaResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.model.RoomTableModel;

public abstract class CheckInDialog extends BaseDialog implements View.OnClickListener{
    private List<RoomRateMain> priceList;
    private RoomTableModel selectedRoom;
    private Spinner rateSpinner;
    private Spinner vehicleSpinner;
    private Spinner carSpinner;
    private Spinner guestTypeSpinner;
    private Spinner spinnerNationalirt;

    private EditText customer;
    private EditText plateNumber;
    private Spinner steward;
    private EditText child;
    private EditText adult;
    private Spinner roomArea;

    private Button checkin;

    private List<FetchCarResponse.Result> carList;
    private List<FetchVehicleResponse.Result> vehicleList;
    private List<FetchGuestTypeResponse.Result> guestTypeList;
    private List<FetchRoomAreaResponse.Result> areaList;
    private List<FetchNationalityResponse.Result> nationalityList;
    private Context context;

    private String userId = "";
    private int roomRateId = 0;
    private int roomRatePriecId = 0;
    private int carId = 0;
    private int vehicleId = 0;
    private int guestTypeId = 0;
    private int areaId = 0;
    private int nationalityId = 0;

    private FetchRoomPendingResponse.Result fetchRoomPendingResult;
    private List<FetchUserResponse.Result> userList;
    public CheckInDialog(@NonNull Context context, RoomTableModel selectedRoom,
                         List<FetchCarResponse.Result> carList, List<FetchVehicleResponse.Result> vehicleList,
                         List<FetchGuestTypeResponse.Result> guestTypeList,
                         List<FetchUserResponse.Result> userList,
                         List<FetchRoomAreaResponse.Result> areaList,
                         List<FetchNationalityResponse.Result> nationalityList,
                         FetchRoomPendingResponse.Result fetchRoomPendingResult) {
        super(context);
        this.selectedRoom = selectedRoom;
        this.context = context;
        this.priceList = selectedRoom.getPrice();
        this.carList = carList;
        this.vehicleList = vehicleList;
        this.guestTypeList = guestTypeList;
        this.userList = userList;
        this.areaList = areaList;
        this.nationalityList = nationalityList;
        this.fetchRoomPendingResult = fetchRoomPendingResult;
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
        setDialogLayout(R.layout.dialog_checkin, String.format("%s - %s (%s)", "GUEST INFO", selectedRoom.getName(), selectedRoom.getRoomType()));

        child = findViewById(R.id.child);
        adult = findViewById(R.id.adult);
        customer = findViewById(R.id.customer);
        plateNumber = findViewById(R.id.plateNumber);
        steward = findViewById(R.id.steward);
        rateSpinner = findViewById(R.id.spinnerRates);
        vehicleSpinner = findViewById(R.id.spinnerVehicle);
        carSpinner = findViewById(R.id.spinnerCar);
        guestTypeSpinner = findViewById(R.id.spinnerGuestType);
        spinnerNationalirt = findViewById(R.id.spinnerNationalirt);
        checkin = findViewById(R.id.checkin);
        checkin.setOnClickListener(this);
        steward = findViewById(R.id.steward);
        roomArea = findViewById(R.id.roomArea);



        setPriceSelection();
        setCarSelection();
        setVehicleSelection();
        setGuestTypeSelection();
        setUserSelection();
        setRoomArea();
        setNationality();

        setSelectedFields();
    }

    private void setSelectedFields() {
        if (fetchRoomPendingResult != null) {
            List<String> nationalityArray = new ArrayList<>();
            nationalityArray.add(fetchRoomPendingResult.getBooked().get(0).getBranchNationality().getNationality());
            CustomSpinnerAdapter rateSpinnerAdapter = new CustomSpinnerAdapter(context, R.id.spinnerItem,
                    nationalityArray);
            spinnerNationalirt.setAdapter(rateSpinnerAdapter);

            List<String> carArray = new ArrayList<>();
            carArray.add(fetchRoomPendingResult.getBooked().get(0).getCar().getCarMake());
            CustomSpinnerAdapter carSpinnerAdapter = new CustomSpinnerAdapter(context, R.id.spinnerItem,
                    carArray);
            carSpinner.setAdapter(carSpinnerAdapter);

            List<String> guestTypeArray = new ArrayList<>();
            guestTypeArray.add(fetchRoomPendingResult.getBooked().get(0).getGuestType().getGuestType());
            CustomSpinnerAdapter guestTypeAdapter = new CustomSpinnerAdapter(context, R.id.spinnerItem,
                    guestTypeArray);
            guestTypeSpinner.setAdapter(guestTypeAdapter);

            plateNumber.setEnabled(false);
            plateNumber.setText(fetchRoomPendingResult.getBooked().get(0).getPlateNo());

            adult.setEnabled(false);
            adult.setText(String.valueOf(fetchRoomPendingResult.getBooked().get(0).getAdult()));

            child.setEnabled(false);
            child.setText(String.valueOf(fetchRoomPendingResult.getBooked().get(0).getChild()));

            List<String> userArray = new ArrayList<>();
            for (FetchUserResponse.Result res : userList) {
                if (res.getUsername().equalsIgnoreCase(String.valueOf(fetchRoomPendingResult.getBooked().get(0).getSteward()))) {
                    userArray.add(res.getName());
                    break;
                }
            }



            CustomSpinnerAdapter userAdapter = new CustomSpinnerAdapter(context, R.id.spinnerItem,
                    userArray);
            steward.setAdapter(userAdapter);




            List<String> vehicleArray = new ArrayList<>();


            for (FetchVehicleResponse.Result res : vehicleList) {
                if (res.getId() == fetchRoomPendingResult.getBooked().get(0).getVehicleId()) {
                    vehicleArray.add(res.getVehicle());
                    break;
                }
            }
            CustomSpinnerAdapter vehicleSpinnerAdapter = new CustomSpinnerAdapter(context, R.id.spinnerItem,
                    vehicleArray);
            vehicleSpinner.setAdapter(vehicleSpinnerAdapter);

            for (int i = 0; i < priceList.size(); i++) {
                if (fetchRoomPendingResult.getBooked().get(0).getRoomRatePriceId() == priceList.get(i).getRoomRatePriceId()) {
                    rateSpinner.setSelection(i, true);
                    roomRateId = priceList.get(i).getRatePrice().getRoomRateId();
                    roomRatePriecId = priceList.get(i).getRoomRatePriceId();
                    break;
                }
            }

        }
    }

    private void setNationality() {
        List<String> nationalityArray = new ArrayList<>();
        for (FetchNationalityResponse.Result nationality : nationalityList) {
            nationalityArray.add(nationality.getNationality());
        }
        CustomSpinnerAdapter rateSpinnerAdapter = new CustomSpinnerAdapter(context, R.id.spinnerItem,
                nationalityArray);
        spinnerNationalirt.setAdapter(rateSpinnerAdapter);

        spinnerNationalirt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nationalityId = nationalityList.get(position).getCoreId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setRoomArea() {
        List<String> areaArray = new ArrayList<>();
        for (FetchRoomAreaResponse.Result area : areaList) {
            areaArray.add(area.getRoomArea());
        }
        CustomSpinnerAdapter rateSpinnerAdapter = new CustomSpinnerAdapter(context, R.id.spinnerItem,
                areaArray);
        roomArea.setAdapter(rateSpinnerAdapter);

        roomArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaId = areaList.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUserSelection() {
        List<String> userArray = new ArrayList<>();
        for (FetchUserResponse.Result us : userList) {
            userArray.add(us.getName());
        }
        CustomSpinnerAdapter rateSpinnerAdapter = new CustomSpinnerAdapter(context, R.id.spinnerItem,
                userArray);
        steward.setAdapter(rateSpinnerAdapter);

        steward.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userId = userList.get(position).getUsername();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setPriceSelection() {
        List<String> priceArray = new ArrayList<>();
        for (RoomRateMain rrm : priceList) {
            priceArray.add(String.format("%f - %s", rrm.getRatePrice().getAmount(), rrm.getRatePrice().getRoomRate().getRoomRate()));
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

        if (fetchRoomPendingResult != null) {
//            fetchRoomPendingResult.getBooked().get(0).getroo
//            rateSpinner.setSelection();
        }
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
                successCheckIn(new WelcomeGuestRequest(
                        String.valueOf(selectedRoom.getRoomId()),
                        String.valueOf(selectedRoom.getRoomTypeId()),
                        String.valueOf(roomRateId),
                        String.valueOf(roomRatePriecId),
                        String.valueOf(carId),
                        String.valueOf(vehicleId),
                        String.valueOf(guestTypeId),
                        customer.getText().toString().trim(),
                        plateNumber.getText().toString().trim(),
                        userId,
                        "",
                        "",
                        adult.getText().toString(),
                        child.getText().toString(),
                        SharedPreferenceManager.getString(context.getApplicationContext(), ApplicationConstants.TAX_RATE),
                        String.valueOf(areaId),
                        String.valueOf(nationalityId)
                ));
//                welcomeGuestRequest();
                dismiss();
                break;
        }
    }

    private void welcomeGuestRequest() {
//        BusProvider.getInstance().post();


    }

    public abstract void successCheckIn(WelcomeGuestRequest welcomeGuestRequest);


    @Override
    protected void onStart() {
        super.onStart();
        Dialog dialog = this;
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
