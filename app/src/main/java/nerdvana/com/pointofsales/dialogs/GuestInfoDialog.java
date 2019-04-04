package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.FetchCompanyUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;

public class GuestInfoDialog extends BaseDialog {
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

    private FetchRoomPendingResponse.Result fetchRoomPendingResult;
    public GuestInfoDialog(@NonNull Context context, FetchRoomPendingResponse.Result fetchRoomPendingResult) {
        super(context);
        this.fetchRoomPendingResult = fetchRoomPendingResult;
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

        updateGuestInfo = findViewById(R.id.updateGuestInfo);

        updateGuestInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}
