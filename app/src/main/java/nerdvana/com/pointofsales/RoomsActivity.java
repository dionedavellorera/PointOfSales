package nerdvana.com.pointofsales;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.adapters.RoomFilterAdapter;
import nerdvana.com.pointofsales.api_requests.ChangeRoomStatusRequest;
import nerdvana.com.pointofsales.api_requests.EditGuestCountRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomRequest;
import nerdvana.com.pointofsales.api_responses.ChangeRoomStatusResponse;
import nerdvana.com.pointofsales.api_responses.EditGuestCountResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomAreaResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.background.RoomsTablesAsync;
import nerdvana.com.pointofsales.custom.HidingEditText;
import nerdvana.com.pointofsales.custom.SpacesItemDecoration;
import nerdvana.com.pointofsales.dialogs.ChangeRoomStatusDialog;
import nerdvana.com.pointofsales.entities.RoomStatusEntity;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.AsyncRequest;
import nerdvana.com.pointofsales.interfaces.RoomFilterContract;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.FilterOptionModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.postlogin.adapter.RoomsTablesAdapter;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomsActivity extends AppCompatActivity implements AsyncContract,
        SelectionContract, RoomFilterContract, AsyncRequest {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        searchView = findViewById(R.id.search);
        rootView = findViewById(R.id.rootView);
        allowedRoomStatusList = new ArrayList<>();


        allowedRoomStatusList.add(RoomConstants.CLEAN);
        allowedRoomStatusList.add(RoomConstants.DIRTY);
        allowedRoomStatusList.add(RoomConstants.OCCUPIED);
        allowedRoomStatusList.add(RoomConstants.SOA);
        allowedRoomStatusList.add(RoomConstants.ONGOING_RC);
        allowedRoomStatusList.add(RoomConstants.ONGOING_RC_WAITING_GUEST);
        allowedRoomStatusList.add(RoomConstants.ONGOING_DIRTY_WAITING_GUEST);
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
                roomsTablesAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                roomsTablesAdapter.addItems(list);
                break;
        }
    }

    private void setRoomsTableAdapter() {
        roomsTablesAdapter = new RoomsTablesAdapter(new ArrayList<RoomTableModel>(), this, RoomsActivity.this, Utils.getSystemType(getApplicationContext()));
        listTableRoomSelection.setLayoutManager(new GridLayoutManager(RoomsActivity.this, 5));
        listTableRoomSelection.addItemDecoration(new SpacesItemDecoration( 10));
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
                    Call<ChangeRoomStatusResponse> request = iUsers.changeRoomStatus(changeRoomStatusRequest.getMapValue());
                    request.enqueue(new Callback<ChangeRoomStatusResponse>() {
                        @Override
                        public void onResponse(Call<ChangeRoomStatusResponse> call, Response<ChangeRoomStatusResponse> response) {
                            if (response.body().getStatus() == 1) {
                                setRoomsTableAdapter();
                                sendRoomListRequest();
                                setRoomFilter();
                                changeRoomStatusDialog.dismiss();
                            } else {
                                Utils.showDialogMessage(RoomsActivity.this, response.body().getMessage(), "Information");
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangeRoomStatusResponse> call, Throwable t) {

                        }
                    });
                }
            };
            changeRoomStatusDialog.show();
        } else {
            Utils.showDialogMessage(RoomsActivity.this, "Room status empty", "Information");
        }
    }

    private void sendRoomListRequest() {
        if (!isLoadingData) {
            refreshRoom.setRefreshing(true);
            isLoadingData = true;
            BusProvider.getInstance().post(new FetchRoomRequest());
        }

    }

    @Subscribe
    public void roomlistResponse(FetchRoomResponse fetchRoomResponse) {
        refreshRoom.setRefreshing(false);
        if (fetchRoomResponse.getResult().size() > 0) {
            SharedPreferenceManager
                    .saveString(
                            RoomsActivity.this,
                            GsonHelper.getGson().toJson(fetchRoomResponse.getResult()),
                            ApplicationConstants.ROOM_JSON);
            new RoomsTablesAsync(this, fetchRoomResponse.getResult(), this).execute();
            setRoomFilter();
//            isLoadingData = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
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
        allowedPosStatus.add("on-going rc with waiting guest");
        allowedPosStatus.add("dirty with waiting guest");
        List<FilterOptionModel> filterOptionsList = new ArrayList<>();
//        List<RoomStatusEntity> rse = RoomStatusEntity.listAll(RoomStatusEntity.class);

        TypeToken<List<FetchRoomStatusResponse.Result>> bookedToken = new TypeToken<List<FetchRoomStatusResponse.Result>>() {};
        List<FetchRoomStatusResponse.Result> rse = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(RoomsActivity.this, ApplicationConstants.ROOM_STATUS_JSON), bookedToken.getType());


        filterOptionsList.add(new FilterOptionModel("ALL", true, 0));
        for (FetchRoomStatusResponse.Result r : rse) {
            if (allowedPosStatus.contains(r.getRoomStatus().toLowerCase())) {
                filterOptionsList.add(new FilterOptionModel(r.getRoomStatus().toUpperCase(), false, r.getCoreId()));
            }
        }

        RoomFilterAdapter roomFilterAdapter = new RoomFilterAdapter(filterOptionsList, this);
        listFilters.setLayoutManager(new LinearLayoutManager(RoomsActivity.this, LinearLayout.HORIZONTAL, false));

        listFilters.setAdapter(roomFilterAdapter);

        roomFilterAdapter.notifyDataSetChanged();

        roomsTablesAdapter.getFilter().filter(searchView.getText().toString().toLowerCase());
    }

    @Override
    public void filterSelected(int statusId) {
        searchView.setText("");
        rootView.requestFocus();

        if (statusId == 0) { //SHOW ALL
            roomsTablesAdapter = new RoomsTablesAdapter(originalRoomList, this, RoomsActivity.this, Utils.getSystemType(getApplicationContext()));
            listTableRoomSelection.setLayoutManager(new GridLayoutManager(RoomsActivity.this, 5));
//            listTableRoomSelection.addItemDecoration(new SpacesItemDecoration( 10));
            listTableRoomSelection.setAdapter(roomsTablesAdapter);
            roomsTablesAdapter.notifyDataSetChanged();
        } else {
            //SHOW SELECTED STATUS
            filteredRoomList = new ArrayList<>();
            for (RoomTableModel rtm : originalRoomList) {
                if (rtm.getStatus().equalsIgnoreCase(String.valueOf(statusId))) {
                    filteredRoomList.add(rtm);
                }
            }
            roomsTablesAdapter = new RoomsTablesAdapter(filteredRoomList, this, RoomsActivity.this, Utils.getSystemType(getApplicationContext()));
            listTableRoomSelection.setLayoutManager(new GridLayoutManager(RoomsActivity.this, 5));
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
        Log.d("FINISHASYNC","TRUE");
        isLoadingData = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("FINISHASYNC","TRUE DESTROY");
        isLoadingData = false;
    }

    //    ChangeRoomStatusRequest changeRoomStatusRequest = new ChangeRoomStatusRequest(
//            previousPersonCount,
//            value.getText().toString(),
//            roomId,
//            remarks,
//            employeeId);
//
//    IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
//    Call<ChangeRoomStatusResponse> request = iUsers.changeRoomStatus(changeRoomStatusRequest.getMapValue());
}
