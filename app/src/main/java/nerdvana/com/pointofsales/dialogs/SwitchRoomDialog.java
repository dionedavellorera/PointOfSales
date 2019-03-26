package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.RoomConstants;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.api_requests.FetchRoomRequest;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.background.RoomsTablesAsync;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.model.OwnRoomModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class SwitchRoomDialog extends BaseDialog {

    private AsyncContract asyncContract;
    private Button switchRoom;
    private Spinner spinnerAvailableRooms;
    private Spinner spinnerRoomRates;
    private Spinner spinnerQuantity;

    private String roomId;
    private String roomRatePriceId;
    private String roomRateName;
    private String roomRateAmount;
    private String quantity = "1";

    private List<OwnRoomModel> ownRoomModelList;
    private List<RoomRateMain> selectedRoomRateLists;
    private Context context;

    private String currentRoomNumber;

    public SwitchRoomDialog(@NonNull Context context, String currentRoomNumber) {
        super(context);
        this.context = context;
        this.currentRoomNumber = currentRoomNumber;
    }

    public SwitchRoomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SwitchRoomDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_switch_room);
        setDialogLayout(R.layout.dialog_switch_room, "SWITCH ROOM");
        selectedRoomRateLists = new ArrayList<>();

        ownRoomModelList = new ArrayList<>();
        sendRoomListRequest();

        asyncContract = new AsyncContract() {
            @Override
            public void doneLoading(List list, String isFor) {
                switch (isFor) {
                    case "roomstables":
                        List<String> temp = new ArrayList<>();
                        for (RoomTableModel rtm : new ArrayList<RoomTableModel>(list)) {
                            if (rtm.getStatus().equalsIgnoreCase(RoomConstants.CLEAN) && !currentRoomNumber.equalsIgnoreCase(rtm.getName())) {
                                ownRoomModelList.add(new OwnRoomModel(
                                        String.valueOf(rtm.getRoomId()),
                                        rtm.getStatus(),
                                        rtm.getName(),
                                        String.valueOf(rtm.getRoomTypeId()),
                                        rtm.getPrice()
                                ));

                                temp.add(rtm.getName());
                            }


                        }

                        if (temp.size() < 1) {
                            Toast.makeText(context, "No clean room available", Toast.LENGTH_SHORT).show();
                        }

                        CustomSpinnerAdapter availableRoomAdapter = new CustomSpinnerAdapter(context, R.id.spinnerItem,
                                temp);
                        spinnerAvailableRooms.setAdapter(availableRoomAdapter);
                        break;
                }
            }
        };


        switchRoom = findViewById(R.id.switchRoom);
        spinnerAvailableRooms = findViewById(R.id.spinnerAvailableRooms);
        spinnerRoomRates = findViewById(R.id.spinnerRoomRates);
        spinnerQuantity = findViewById(R.id.spinnerQuantity);
        switchRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(roomRatePriceId) && !TextUtils.isEmpty(roomId)) {
                    switchRoomConfirm(roomRatePriceId, quantity,
                            roomRateAmount, roomRateName,
                            roomId, selectedRoomRateLists);
                    dismiss();
                } else {
                    Toast.makeText(context, "No room / rate available", Toast.LENGTH_SHORT).show();
                }

            }
        });

        setQuantitySpinner();

        spinnerQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quantity = spinnerQuantity.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        spinnerAvailableRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                roomId = ownRoomModelList.get(position).getRoomId();
                selectedRoomRateLists = ownRoomModelList.get(position).getPriceList();

                List<String> temp = new ArrayList<>();

                for (RoomRateMain rrm : ownRoomModelList.get(position).getPriceList()) {
                    temp.add(rrm.getRatePrice().getRoomRate().getRoomRate() + " - " + rrm.getRatePrice().getAmount());
                }


                CustomSpinnerAdapter availableRoomAdapter = new CustomSpinnerAdapter(context, R.id.spinnerItem,
                        temp);
                spinnerRoomRates.setAdapter(availableRoomAdapter);

                spinnerRoomRates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        roomRatePriceId = String.valueOf(ownRoomModelList.get(position).getPriceList().get(pos).getRoomRatePriceId());
                        roomRateName = ownRoomModelList.get(position).getPriceList().get(pos).getRatePrice().getRoomRate().getRoomRate();
                        roomRateAmount = String.valueOf(ownRoomModelList.get(position).getPriceList().get(pos).getRatePrice().getAmount());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public abstract void switchRoomConfirm(
            String roomRatePriceId, String qty,
            String price, String rateName,
            String roomId, List<RoomRateMain> roomRateMainList);

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
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
        spinnerQuantity.setAdapter(arrayAdapter);
    }



    private void sendRoomListRequest() {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchRoomResponse> roomlistRequest = iUsers.sendRoomListRequest(
                new FetchRoomRequest().getMapValue());
        roomlistRequest.enqueue(new Callback<FetchRoomResponse>() {
            @Override
            public void onResponse(Call<FetchRoomResponse> call, Response<FetchRoomResponse> response) {
                if (response.body().getResult().size() > 0) {
                    new RoomsTablesAsync(asyncContract, response.body().getResult()).execute();
                }
            }

            @Override
            public void onFailure(Call<FetchRoomResponse> call, Throwable t) {

            }
        });
    }




}
