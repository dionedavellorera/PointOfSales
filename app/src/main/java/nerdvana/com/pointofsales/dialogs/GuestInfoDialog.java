package nerdvana.com.pointofsales.dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_requests.UpdateCheckInTimeRequest;
import nerdvana.com.pointofsales.api_requests.WakeUpCallUpdateRequest;
import nerdvana.com.pointofsales.api_responses.FetchCompanyUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchDiscountSpecialResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.api_responses.PostVoidResponse;
import nerdvana.com.pointofsales.api_responses.UpdateCheckInTimeResponse;
import nerdvana.com.pointofsales.api_responses.WakeUpCallUpdateResponse;
import nerdvana.com.pointofsales.model.ChangeWakeUpCallPrintModel;
import nerdvana.com.pointofsales.model.PrintModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class GuestInfoDialog extends BaseDialog {
    private Button updateGuestInfo;

    private TextView roomNumber;
    private TextView roomType;
    private TextView rateDescription;
    private TextView rateAmount;
    private TextView vehicle;
    private TextView carMake;
    private TextView plateNumber;
    private TextView roomBoy;
    private TextView remarks;
    private TextView wakeUpCall;
    private TextView checkInTime;

    String finalCheckInDateTime = ""; //24hrs time 23:23:00
    String finalCheckInDate = "";
    String finalCheckInTime = "";

    private Activity act;

    private Button changeCheckInTime;

    private String dateSet = "";
    private String timeSet = "";
    private String finalTime = "";
    private FetchRoomPendingResponse.Result fetchRoomPendingResult;
    public GuestInfoDialog(@NonNull Context context,
                           FetchRoomPendingResponse.Result fetchRoomPendingResult,
                           Activity act) {
        super(context);
        this.fetchRoomPendingResult = fetchRoomPendingResult;
        this.act = act;
    }

    public GuestInfoDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected GuestInfoDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_guest_info, "GUEST INFO");


        final DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        final DateTime jodatime = dtf.parseDateTime(String.valueOf(fetchRoomPendingResult.getBooked().get(0).getWakeUpCall()));
        final DateTime checkOutJodaTime = dtf.parseDateTime(String.valueOf(fetchRoomPendingResult.getBooked().get(0).getExpectedCheckOut().toString()));
        final DateTime checkInJodaTime = dtf.parseDateTime(String.valueOf(fetchRoomPendingResult.getBooked().get(0).getCheckIn().toString()));
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime finalDate = dtf.parseDateTime(dtfOut.print(jodatime));
        DateTime finalChDate = dtf.parseDateTime(dtfOut.print(checkInJodaTime));


        dateSet = String.format("%s-%s-%s", String.valueOf(finalDate.getYear()),
                String.valueOf(finalDate.getMonthOfYear() < 10 ? "0" + finalDate.getMonthOfYear() : finalDate.getMonthOfYear()),
                String.valueOf(finalDate.getDayOfMonth() < 10 ? "0" + finalDate.getDayOfMonth() : finalDate.getDayOfMonth()));
        timeSet = String.format("%s:%s:00", String.valueOf(finalDate.getHourOfDay()), String.valueOf(finalDate.getMinuteOfHour()));

        finalCheckInDateTime = String.format("%s-%s-%s", String.valueOf(finalChDate.getYear()),
                String.valueOf(finalChDate.getMonthOfYear() < 10 ? "0" + finalChDate.getMonthOfYear() : finalChDate.getMonthOfYear()),
                String.valueOf(finalChDate.getDayOfMonth() < 10 ? "0" + finalChDate.getDayOfMonth() : finalChDate.getDayOfMonth())) + " " +
                String.format("%s:%s:00", String.valueOf(finalChDate.getHourOfDay()), String.valueOf(finalChDate.getMinuteOfHour()));


        wakeUpCall = findViewById(R.id.wakeUpCallValue);

        wakeUpCall.setText(Utils.convertDateToReadableDate(String.valueOf(fetchRoomPendingResult.getBooked().get(0).getWakeUpCall())));

        checkInTime = findViewById(R.id.checkInTimeValue);
        checkInTime.setText(Utils.convertDateToReadableDate(String.valueOf(fetchRoomPendingResult.getBooked().get(0).getCheckIn().toString())));

        updateGuestInfo = findViewById(R.id.updateGuestInfo);

        updateGuestInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(act, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {


                                final TimePickerDialog timePickerDialog = new TimePickerDialog(act, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        dateSet = String.format("%s-%s-%s", String.valueOf(year < 10 ? "0" + year : year), String.valueOf((month + 1) < 10 ? "0" + (month +1) : (month + 1)) , String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth));
                                        timeSet = String.format("%s:%s:00", String.valueOf((hourOfDay < 10 ? "0" + hourOfDay : hourOfDay)), String.valueOf((minute < 10 ? "0" + minute : minute)));

                                        finalTime = dateSet + " " + timeSet;

                                        //finalTime = dateSet + " " + timeSet; //24hrs time 23:23:00
                                        if (dtf.parseDateTime(finalTime).isBefore(checkInJodaTime)) {
                                            Utils.showDialogMessage(act, "Wake up call time invalid, time set before check in time", "Error");
                                        } else if(dtf.parseDateTime(finalTime).isAfter(checkOutJodaTime)) {
                                            Utils.showDialogMessage(act, "Wake up call time invalid, time set after check out time", "Error");
                                        } else {
                                            updateGuestInfo(finalTime);
                                        }
                                    }
                                }, jodatime.hourOfDay().get(), jodatime.minuteOfHour().get(), true);
                                timePickerDialog.show();


                            }
                        }, jodatime.getYear(), jodatime.getMonthOfYear() -1, jodatime.getDayOfMonth());
                datePickerDialog.show();






            }
        });



        roomNumber = findViewById(R.id.roomNumberValue);
        roomNumber.setText(fetchRoomPendingResult.getBooked().get(0).getRoomNo());


        roomType = findViewById(R.id.roomTypeValue);
        roomType.setText(fetchRoomPendingResult.getBooked().get(0).getRoomType());
        rateDescription = findViewById(R.id.rateDescriptionValue);
        rateAmount = findViewById(R.id.rateAmountValue);
        String rd = "";
        String ra = "";

        for (FetchRoomPendingResponse.Post p : fetchRoomPendingResult.getBooked().get(0).getTransaction().getPost()) {
            if (p.getVoid() == 0 && p.getProductId() == 0) {
                rd += p.getRoomRate() + ",";
                ra += p.getTotal() + ",";
            }
        }

        rateDescription.setText(rd);
        rateAmount.setText(ra);

//        rateAmount.setText();
        vehicle = findViewById(R.id.vehicleValue);
        vehicle.setText(fetchVehicleFromId(String.valueOf(fetchRoomPendingResult.getBooked().get(0).getVehicleId())));
        carMake = findViewById(R.id.carMakeValue);
        carMake.setText(fetchRoomPendingResult.getBooked().get(0).getCar().getCarMake());
        plateNumber = findViewById(R.id.plateNumberValue);
        plateNumber.setText(fetchRoomPendingResult.getBooked().get(0).getPlateNo());
        roomBoy = findViewById(R.id.roomBoyValue);
        roomBoy.setText(getUserInfo(String.valueOf(fetchRoomPendingResult.getBooked().get(0).getSteward())));
        remarks = findViewById(R.id.remarksValue);
        remarks.setText("N/A");


        changeCheckInTime = findViewById(R.id.changeCheckInTime);
        changeCheckInTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeCheckInTimeChainDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
        Dialog dialog = this;
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private String fetchVehicleFromId(String vehicleId) {
        TypeToken<List<FetchVehicleResponse.Result>> vehicleToken = new TypeToken<List<FetchVehicleResponse.Result>>() {};
        List<FetchVehicleResponse.Result> vehicleList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.VEHICLE_JSON), vehicleToken.getType());
        String result = "N/A";

        for (FetchVehicleResponse.Result res : vehicleList) {
            if (String.valueOf(res.getId()).equals(vehicleId)) {
                result = res.getVehicle();
                break;
            }
        }

        return result;
    }

    private String getUserInfo(String userId) {

        TypeToken<List<FetchCompanyUserResponse.Result>> companyUser = new TypeToken<List<FetchCompanyUserResponse.Result>>() {};
        List<FetchCompanyUserResponse.Result> vehicleList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.COMPANY_USER), companyUser.getType());
        String result = "N/A";

        for (FetchCompanyUserResponse.Result res : vehicleList) {
            if (String.valueOf(res.getUsername()).equals(userId)) {
                result = res.getName();
                break;
            }
        }

        return result;
    }

    private void updateGuestInfo(String finalTime) {

        final ChangeWakeUpCallPrintModel changeWakeUpCallPrintModel = new ChangeWakeUpCallPrintModel(fetchRoomPendingResult.getBooked().get(0).getRoomNo(), finalTime);

        WakeUpCallUpdateRequest wakeUpCallUpdateRequest = new WakeUpCallUpdateRequest(String.valueOf(fetchRoomPendingResult.getBooked().get(0).getRoomId()), finalTime);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<WakeUpCallUpdateResponse> request = iUsers.updateWakeUpCall(wakeUpCallUpdateRequest.getMapValue());

        request.enqueue(new Callback<WakeUpCallUpdateResponse>() {
            @Override
            public void onResponse(Call<WakeUpCallUpdateResponse> call, Response<WakeUpCallUpdateResponse> response) {
                dismiss();
                refresh(GsonHelper.getGson().toJson(changeWakeUpCallPrintModel));
                Utils.showDialogMessage(act, "Wake up call successfully changed", "Information");
            }

            @Override
            public void onFailure(Call<WakeUpCallUpdateResponse> call, Throwable t) {

            }
        });
    }

    private void updateCheckInTime(String roomId, String checkInTime, String empId) {
        UpdateCheckInTimeRequest updateCheckInTimeRequest = new UpdateCheckInTimeRequest(roomId, checkInTime, empId);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<UpdateCheckInTimeResponse> request = iUsers.updateCheckInTime(updateCheckInTimeRequest.getMapValue());
        request.enqueue(new Callback<UpdateCheckInTimeResponse>() {
            @Override
            public void onResponse(Call<UpdateCheckInTimeResponse> call, Response<UpdateCheckInTimeResponse> response) {
                dismiss();
                refresh();
                Utils.showDialogMessage(act, "Check in time successfully changed", "Information");
            }

            @Override
            public void onFailure(Call<UpdateCheckInTimeResponse> call, Throwable t) {

            }
        });
    }

    public abstract void refresh();
    public abstract void refresh(String jsonString);

    private void showChangeCheckInTimeChainDialog() {
        final DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        final DateTime jodatime = dtf.parseDateTime(String.valueOf(fetchRoomPendingResult.getBooked().get(0).getCheckIn()));
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(act, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        finalCheckInDate = String.format("%s-%s-%s", String.valueOf(year < 10 ? "0" + year : year),
                                String.valueOf((month + 1) < 10 ? "0" + (month +1) : (month + 1)) , String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth));
                        final TimePickerDialog timePickerDialog = new TimePickerDialog(act, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                finalCheckInTime = String.format("%s:%s:00", String.valueOf((hourOfDay < 10 ? "0" + hourOfDay : hourOfDay)), String.valueOf((minute < 10 ? "0" + minute  : minute)));
                                finalCheckInDateTime = finalCheckInDate + " " + finalCheckInTime;
                                PasswordDialog passwordDialog = new PasswordDialog(act, "60") {
                                    @Override
                                    public void passwordSuccess(String employeeId, String employeeName) {
                                        updateCheckInTime(String.valueOf(fetchRoomPendingResult.getBooked().get(0).getRoomId()), finalCheckInDateTime, employeeId);
                                    }

                                    @Override
                                    public void passwordFailed() {

                                    }
                                };
                                passwordDialog.show();

                            }
                        }, jodatime.hourOfDay().get(), jodatime.minuteOfHour().get(), true);
                        timePickerDialog.show();
                    }
                }, jodatime.getYear(), jodatime.getMonthOfYear() -1, jodatime.getDayOfMonth());
        datePickerDialog.show();
    }

//    private void setPaymentsDiscountsAdapter() {
//        fetchRoomPendingResult.getBooked().get(0).getTransaction()
//    }
}
