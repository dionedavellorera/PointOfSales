package nerdvana.com.pointofsales;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.adapters.RoomFilterAdapter;
import nerdvana.com.pointofsales.api_requests.ChangeRoomStatusRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomRequest;
import nerdvana.com.pointofsales.api_responses.ChangeRoomStatusResponse;
import nerdvana.com.pointofsales.api_responses.CheckSafeKeepingResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
import nerdvana.com.pointofsales.background.RoomsTablesAsync;
import nerdvana.com.pointofsales.custom.CustomGridLayoutManager;
import nerdvana.com.pointofsales.custom.HidingEditText;
import nerdvana.com.pointofsales.dialogs.ChangeRoomStatusDialog;
import nerdvana.com.pointofsales.dialogs.CollectionDialog;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.AsyncRequest;
import nerdvana.com.pointofsales.interfaces.RoomFilterContract;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.FilterOptionModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.postlogin.adapter.RoomsTablesAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomsActivity extends AppCompatActivity implements AsyncContract,
        SelectionContract, RoomFilterContract, AsyncRequest {
    Call<FetchRoomResponse> sendRoomApiRequest;
    Call<ChangeRoomStatusResponse> changeRoomStatusApiRequest;


    private CollectionDialog collectionDialog;
    boolean isLoadingData = false;

    ChangeRoomStatusDialog changeRoomStatusDialog;

    private CoordinatorLayout rootView;

    private RoomsTablesAdapter roomsTablesAdapter;
    private RecyclerView listTableRoomSelection;

    private RecyclerView listFilters;

    private List<RoomTableModel> originalRoomList;
    private List<RoomTableModel> filteredRoomList;

    private SwipeRefreshLayout refreshRoom;
    private HidingEditText searchView;
    private List<String> allowedRoomStatusList;
    private RoomFilterAdapter roomFilterAdapter;

    private List<FilterOptionModel> filterOptionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        searchView = findViewById(R.id.search);
        rootView = findViewById(R.id.rootView);
        allowedRoomStatusList = new ArrayList<>();
        allowedRoomStatusList.add(RoomConstants.CLEAN);
        allowedRoomStatusList.add(RoomConstants.HOTELIFIED);
        allowedRoomStatusList.add(RoomConstants.INSPECTED_CLEAN);
        allowedRoomStatusList.add(RoomConstants.DIRTY);
        allowedRoomStatusList.add(RoomConstants.OCCUPIED);
        allowedRoomStatusList.add(RoomConstants.SOA);

//        allowedRoomStatusList.add(RoomConstants.ONGOING_RC);
//        allowedRoomStatusList.add(RoomConstants.ONGOING_RC_WAITING_GUEST);
//        allowedRoomStatusList.add(RoomConstants.ONGOING_DIRTY_WAITING_GUEST);
        allowedRoomStatusList.add(RoomConstants.WELCOME);
//        allowedRoomStatusList.add(RoomConstants.ON_GOING_NEGO);

        setTitle("ROOMS");

        Toolbar toolbar = findViewById(R.id.toolbar);
        refreshRoom = findViewById(R.id.refreshRoom);
        listFilters = findViewById(R.id.listFilters);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listTableRoomSelection = findViewById(R.id.listTableRoomSelection);

        setRoomsTableAdapter();
        sendRoomListRequest();
        setRoomFilter();

        originalRoomList = new ArrayList<>();
        filteredRoomList = new ArrayList<>();

        refreshRoom.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRoomListRequest();

            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (roomsTablesAdapter != null) {
                    roomsTablesAdapter.getFilter().filter(s);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchView.requestFocus();

        searchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()==0) {
                    if (roomsTablesAdapter != null) {
                        if (roomsTablesAdapter.getRoomsFilteredList().size() == 1) {

                            if (allowedRoomStatusList.contains(roomsTablesAdapter.getRoomsFilteredList().get(0).getStatus())) {
                                Intent intent = new Intent();
                                intent.putExtra("selected", GsonHelper.getGson().toJson(roomsTablesAdapter.getRoomsFilteredList().get(0)));
                                setResult(RESULT_OK, intent);
                                finish();
                            } else {
                                Utils.showDialogMessage(RoomsActivity.this, String.format("%s %s", "You are not allowed to use this room, current status is", roomsTablesAdapter.getRoomsFilteredList().get(0).getStatusDescription()), "Warning");
                            }
                        }
                    }
                }
                return false;
            }


        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
        return true;
    }

    @Override
    public void doneLoading(List list, String isFor) {
        refreshRoom.setRefreshing(false);
        switch (isFor) {
            case "roomstables":
                originalRoomList = list;

                int cleanCount = 0;
                int dirtyCount = 0;
                int occupiedCount = 0;
                int soaCount = 0;
                int welcomeCount = 0;
                for (RoomTableModel rtm : originalRoomList) {
                    if (rtm.getStatus().equalsIgnoreCase(RoomConstants.CLEAN)) {
                        cleanCount+=1;
                    }

                    if (rtm.getStatus().equalsIgnoreCase(RoomConstants.INSPECTED_CLEAN)) {
                        cleanCount+=1;
                    }

                    if (rtm.getStatus().equalsIgnoreCase(RoomConstants.HOTELIFIED)) {
                        cleanCount+=1;
                    }

                    if (rtm.getStatus().equalsIgnoreCase(RoomConstants.FOR_INSPECTION)) {
                        dirtyCount+=1;
                    }

                    if (rtm.getStatus().equalsIgnoreCase(RoomConstants.DIRTY)) {
                        dirtyCount+=1;
                    }

                    if (rtm.getStatus().equalsIgnoreCase(RoomConstants.DIRTY_WITH_LINEN)) {
                        dirtyCount+=1;
                    }

                    if (rtm.getStatus().equalsIgnoreCase(RoomConstants.OCCUPIED)) {
                        occupiedCount+=1;
                    }

                    if (rtm.getStatus().equalsIgnoreCase(RoomConstants.SOA)) {
                        soaCount+=1;
                    }

                    if (rtm.getStatus().equalsIgnoreCase(RoomConstants.WELCOME)) {
                        welcomeCount+=1;
                    }
                }
                for (FilterOptionModel model : filterOptionsList) {
                    if (model.getStatusId() == 0) {
                        model.setStatusCount(cleanCount +dirtyCount + occupiedCount + soaCount + welcomeCount);
                    }

                    if (model.getStatusId() == Integer.valueOf(RoomConstants.CLEAN)) {
                        model.setStatusCount(cleanCount);
                    }
                    if (model.getStatusId() == Integer.valueOf(RoomConstants.DIRTY)) {
                        model.setStatusCount(dirtyCount);
                    }

                    if (model.getStatusId() == Integer.valueOf(RoomConstants.OCCUPIED)) {
                        model.setStatusCount(occupiedCount);
                    }

                    if (model.getStatusId() == Integer.valueOf(RoomConstants.SOA)) {
                        model.setStatusCount(soaCount);
                    }

                    if (model.getStatusId() == Integer.valueOf(RoomConstants.WELCOME)) {
                        model.setStatusCount(welcomeCount);
                    }


                }

                if (roomFilterAdapter != null) {
                    roomFilterAdapter.notifyDataSetChanged();
                }

                roomsTablesAdapter.addItems(list);


                if (!TextUtils.isEmpty(searchView.getText().toString())) {
                    roomsTablesAdapter.getFilter().filter(searchView.getText().toString().toLowerCase());
                }
                break;
        }
    }

    private void setRoomsTableAdapter() {
//        int spanCount = Integer.valueOf(SharedPreferenceManager.getString(RoomsActivity.this, ApplicationConstants.MAX_GRID_COLUMN));
        roomsTablesAdapter = new RoomsTablesAdapter(new ArrayList<RoomTableModel>(), this, RoomsActivity.this, Utils.getSystemType(getApplicationContext()));
        listTableRoomSelection.setLayoutManager(new CustomGridLayoutManager(RoomsActivity.this, calculateNoOfColumns(RoomsActivity.this, 100)));
//        listTableRoomSelection.addItemDecoration(new SpacesItemDecoration( 10));
        listTableRoomSelection.setAdapter(roomsTablesAdapter);
        roomsTablesAdapter.notifyDataSetChanged();
    }

    @Override
    public void listClicked(RoomTableModel selectedItem) {

        if (allowedRoomStatusList.contains(selectedItem.getStatus())) {
            Intent intent = new Intent();
            intent.putExtra("selected", GsonHelper.getGson().toJson(selectedItem));
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Utils.showDialogMessage(RoomsActivity.this, String.format("%s %s", "You are not allowed to use this room, current status is", selectedItem.getStatusDescription()), "Warning");
        }



    }

    @Override
    public void listLongClicked(RoomTableModel selectedItem) {
        TypeToken<List<FetchRoomStatusResponse.Result>> areaToken = new TypeToken<List<FetchRoomStatusResponse.Result>>() {};
        List<FetchRoomStatusResponse.Result> resultList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.ROOM_STATUS_JSON), areaToken.getType());
        if (resultList != null) {
            changeRoomStatusDialog = new ChangeRoomStatusDialog(RoomsActivity.this, resultList, String.valueOf(selectedItem.getRoomId())) {
                @Override
                public void changeStatus(ChangeRoomStatusRequest changeRoomStatusRequest) {
                    IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                    if (changeRoomStatusApiRequest == null) {
                        changeRoomStatusApiRequest = iUsers.changeRoomStatus(changeRoomStatusRequest.getMapValue());
                        changeRoomStatusApiRequest.enqueue(new Callback<ChangeRoomStatusResponse>() {
                            @Override
                            public void onResponse(Call<ChangeRoomStatusResponse> call, Response<ChangeRoomStatusResponse> response) {
                                changeRoomStatusApiRequest = null;

                                if (response.code() == 200) {
                                    if (response.body() != null) {
                                        if (response.body().getStatus() == 1) {
                                            setRoomsTableAdapter();
                                            sendRoomListRequest();
                                            setRoomFilter();
                                            SocketManager.reloadPos(
                                                    selectedItem.getName(),
                                                    String.valueOf(selectedItem.getRoomId()),
                                                    changeRoomStatusRequest.getNewValue(),
                                                    changeRoomStatusRequest.getNewValue(),
                                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME),
                                                    "end");
                                            if (!TextUtils.isEmpty(searchView.getText().toString())) {
                                                roomsTablesAdapter.getFilter().filter(searchView.getText().toString().toLowerCase());
                                            }

                                            changeRoomStatusDialog.dismiss();
                                        } else {
                                            Utils.showDialogMessage(RoomsActivity.this, response.body().getMessage(), "Information");
                                        }
                                    } else {

                                        Toast.makeText(RoomsActivity.this, "Response body of change room status is null", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(RoomsActivity.this, "There is an error change room status", Toast.LENGTH_LONG).show();
                                }


                            }

                            @Override
                            public void onFailure(Call<ChangeRoomStatusResponse> call, Throwable t) {
                                changeRoomStatusApiRequest = null;
                                Toast.makeText(RoomsActivity.this, "CHANGE ROOM STATUS" + t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(RoomsActivity.this, "A change status request is still on processing", Toast.LENGTH_LONG).show();
                    }

                }
            };
            if (selectedItem.getStatus().equalsIgnoreCase("2") || selectedItem.getStatus().equalsIgnoreCase("17")) {
                Utils.showDialogMessage(RoomsActivity.this, "Room is occupied, please checkout properly", "Information");
            } else {
                if (SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.USERNAME).equalsIgnoreCase("10862") ||
                        SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.USERNAME).equalsIgnoreCase("10655")) {
                    changeRoomStatusDialog.show();
                }

            }

        } else {
            Utils.showDialogMessage(RoomsActivity.this, "Room status empty", "Information");
        }
    }

    private void sendRoomListRequest() {
        if (!isLoadingData) {
            refreshRoom.setRefreshing(true);
            isLoadingData = true;
            BusProvider.getInstance().post(new FetchRoomRequest());


            FetchRoomRequest fetchRoomRequest = new FetchRoomRequest();
            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
            if (sendRoomApiRequest == null) {

                sendRoomApiRequest = iUsers.sendRoomListRequest(
                        fetchRoomRequest.getMapValue());

                sendRoomApiRequest.enqueue(new Callback<FetchRoomResponse>() {
                    @Override
                    public void onResponse(Call<FetchRoomResponse> call, Response<FetchRoomResponse> response) {

                        refreshRoom.setRefreshing(false);

                        if (response.code() == 200) {
                            if (response.body() != null) {
                                if (response.body().getResult().size() > 0) {
                                    new RoomsTablesAsync(RoomsActivity.this, response.body().getResult(), RoomsActivity.this).execute();
                                    setRoomFilter();
                                }
                            } else {
                                isLoadingData = false;
                                Toast.makeText(RoomsActivity.this, "Response body of fetch room is null", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            isLoadingData = false;
                            Toast.makeText(RoomsActivity.this, "There is an error in room list", Toast.LENGTH_LONG).show();
                        }


                        sendRoomApiRequest = null;
                    }

                    @Override
                    public void onFailure(Call<FetchRoomResponse> call, Throwable t) {
                        isLoadingData = false;
                        refreshRoom.setRefreshing(false);
                        sendRoomApiRequest = null;
                        Toast.makeText(RoomsActivity.this, "ROOM LIST " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }

        }

    }

//    @Subscribe
//    public void roomlistResponse(FetchRoomResponse fetchRoomResponse) {
//        refreshRoom.setRefreshing(false);
//        if (fetchRoomResponse.getResult().size() > 0) {
////            SharedPreferenceManager
////                    .saveString(
////                            RoomsActivity.this,
////                            GsonHelper.getGson().toJson(fetchRoomResponse.getResult()),
////                            ApplicationConstants.ROOM_JSON);
//            new RoomsTablesAsync(this, fetchRoomResponse.getResult(), this).execute();
//            setRoomFilter();
//
//
//
////            isLoadingData = false;
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
        if (sendRoomApiRequest != null) {
            sendRoomApiRequest.cancel();
            sendRoomApiRequest = null;
        }

        if (changeRoomStatusApiRequest != null) {
            changeRoomStatusApiRequest.cancel();
            changeRoomStatusApiRequest = null;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);

//        if (sendRoomApiRequest != null) {
//            sendRoomApiRequest.request();
//        }
//
//        if (changeRoomStatusApiRequest != null) {
//            changeRoomStatusApiRequest.request();
//        }
    }

    @Subscribe
    public void apiError(ApiError apiError) {
        refreshRoom.setRefreshing(false);
        Toast.makeText(RoomsActivity.this, apiError.message(), Toast.LENGTH_SHORT).show();
    }

    private void setRoomFilter() {
        List<String> allowedPosStatus = new ArrayList<>();
        allowedPosStatus.add("welcome");
        allowedPosStatus.add("occupied");
        allowedPosStatus.add("soa");
        allowedPosStatus.add("clean");
        allowedPosStatus.add("dirty");
//        allowedPosStatus.add("on-going rc with waiting guest");
//        allowedPosStatus.add("dirty with waiting guest");
        filterOptionsList = new ArrayList<>();
//        List<RoomStatusEntity> rse = RoomStatusEntity.listAll(RoomStatusEntity.class);

        TypeToken<List<FetchRoomStatusResponse.Result>> bookedToken = new TypeToken<List<FetchRoomStatusResponse.Result>>() {};
        List<FetchRoomStatusResponse.Result> rse = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(RoomsActivity.this, ApplicationConstants.ROOM_STATUS_JSON), bookedToken.getType());


        filterOptionsList.add(new FilterOptionModel("ALL", true, 0, null));

        for (FetchRoomStatusResponse.Result r : rse) {
            if (allowedPosStatus.contains(r.getRoomStatus().toLowerCase())) {
                filterOptionsList.add(new FilterOptionModel(r.getRoomStatus().toUpperCase(),
                        false, r.getCoreId(),
                        r.getColor()));
            }
        }

        roomFilterAdapter = new RoomFilterAdapter(filterOptionsList, this);
        listFilters.setLayoutManager(new LinearLayoutManager(RoomsActivity.this, LinearLayout.HORIZONTAL, false));

        listFilters.setAdapter(roomFilterAdapter);

        roomFilterAdapter.notifyDataSetChanged();

        if (!TextUtils.isEmpty(searchView.getText().toString())) {
            roomsTablesAdapter.getFilter().filter(searchView.getText().toString().toLowerCase());
        }

    }

    @Override
    public void filterSelected(int statusId) {
        searchView.setText("");
        rootView.requestFocus();

        if (statusId == 0) { //SHOW ALL
            roomsTablesAdapter = new RoomsTablesAdapter(originalRoomList, this, RoomsActivity.this, Utils.getSystemType(getApplicationContext()));
            listTableRoomSelection.setLayoutManager(new CustomGridLayoutManager(RoomsActivity.this, calculateNoOfColumns(RoomsActivity.this, 100)));
//            listTableRoomSelection.addItemDecoration(new SpacesItemDecoration( 10));
            listTableRoomSelection.setAdapter(roomsTablesAdapter);
            roomsTablesAdapter.notifyDataSetChanged();
        } else {
            //SHOW SELECTED STATUS
            filteredRoomList = new ArrayList<>();


            for (RoomTableModel rtm : originalRoomList) {
                if (statusId == 3) {
                    if (rtm.getStatus().equalsIgnoreCase(RoomConstants.DIRTY) ||
                            rtm.getStatus().equalsIgnoreCase(RoomConstants.DIRTY_WITH_LINEN)) {
                        filteredRoomList.add(rtm);
                    }
                } else {
                    if (rtm.getStatus().equalsIgnoreCase(String.valueOf(statusId))) {
                        filteredRoomList.add(rtm);
                    }
                }


            }
            roomsTablesAdapter = new RoomsTablesAdapter(filteredRoomList, this, RoomsActivity.this, Utils.getSystemType(getApplicationContext()));
            listTableRoomSelection.setLayoutManager(new CustomGridLayoutManager(RoomsActivity.this, calculateNoOfColumns(RoomsActivity.this, 100)));
            listTableRoomSelection.setAdapter(roomsTablesAdapter);

            roomsTablesAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void updateRoom(UpdateDataModel updateDataModel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                sendRoomListRequest();
            }
        });
    }

    @Override
    public void finished() {
//        Log.d("FINISHASYNC","TRUE");
        isLoadingData = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.d("FINISHASYNC","TRUE DESTROY");
        isLoadingData = false;
    }

//    @Subscribe
//    public void checkSafeKeeping(CheckSafeKeepingResponse checkSafeKeepingResponse) {
//        Double safeKeepAmount = Double.valueOf(SharedPreferenceManager.getString(RoomsActivity.this, ApplicationConstants.SAFEKEEPING_AMOUNT));
//        if (safeKeepAmount != 0) {
//            if (checkSafeKeepingResponse.getResult().getUnCollected() >= safeKeepAmount) {
//
//
//                if (!ApplicationConstants.IS_ACTIVE.equalsIgnoreCase("T")) {
//                    if (collectionDialog == null) {
//                        ApplicationConstants.IS_ACTIVE = "T";
//                        collectionDialog = new CollectionDialog(RoomsActivity.this, "Safekeeping", false) {
//                            @Override
//                            public void printCashRecoData(String cashNRecoData) {
//
//                            }
//                        };
//
//                        collectionDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                            @Override
//                            public void onCancel(DialogInterface dialog) {
//                                collectionDialog = null;
//                                ApplicationConstants.IS_ACTIVE = "F";
//                            }
//                        });
//
//                        collectionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                            @Override
//                            public void onDismiss(DialogInterface dialog) {
//                                collectionDialog = null;
//                                ApplicationConstants.IS_ACTIVE = "F";
//                            }
//                        });
//
//                        if (!collectionDialog.isShowing()) collectionDialog.show();
//                    }
//                }
//            }
//        }
//    }


    //    ChangeRoomStatusRequest changeRoomStatusRequest = new ChangeRoomStatusRequest(
//            previousPersonCount,
//            value.getText().toString(),
//            roomId,
//            remarks,
//            employeeId);
//
//    IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
//    Call<ChangeRoomStatusResponse> request = iUsers.changeRoomStatus(changeRoomStatusRequest.getMapValue());


    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
        return noOfColumns;
    }


}
