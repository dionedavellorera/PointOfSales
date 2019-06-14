package nerdvana.com.pointofsales;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.api_requests.FetchOrderPendingRequest;
import nerdvana.com.pointofsales.api_requests.GetOrderRequest;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomAreaResponse;
import nerdvana.com.pointofsales.api_responses.GetOrderResponse;
import nerdvana.com.pointofsales.background.FetchOrderPendingAsync;
import nerdvana.com.pointofsales.background.RoomsTablesAsync;
import nerdvana.com.pointofsales.custom.SpacesItemDecoration;
import nerdvana.com.pointofsales.dialogs.TakeOutCreateCustomerDialog;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.postlogin.adapter.RoomsTablesAdapter;

public class TakeOutActivity extends AppCompatActivity implements AsyncContract,
        SelectionContract {

    private RoomsTablesAdapter roomsTablesAdapter;
    private RecyclerView listTableRoomSelection;
    private SwipeRefreshLayout refreshRoom;

    private RecyclerView listFilters;

    private FloatingActionButton fab;

    private List<FetchRoomAreaResponse.Result> roomAreaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_out);

        setTitle("TAKE OUT");
        Toolbar toolbar = findViewById(R.id.toolbar);
        refreshRoom = findViewById(R.id.refreshRoom);
        listFilters = findViewById(R.id.listFilters);
        roomAreaList = new ArrayList<>();

        TypeToken<List<FetchRoomAreaResponse.Result>> areaToken = new TypeToken<List<FetchRoomAreaResponse.Result>>() {};
        roomAreaList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.ROOM_AREA_JSON), areaToken.getType());

        listTableRoomSelection = findViewById(R.id.listTableRoomSelection);
        fab = findViewById(R.id.fab);
        setRoomsTableAdapter();


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listTableRoomSelection = findViewById(R.id.listTableRoomSelection);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fetchOrderPendingRequest();

        refreshRoom.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchOrderPendingRequest();

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakeOutCreateCustomerDialog takeout = new TakeOutCreateCustomerDialog(TakeOutActivity.this, roomAreaList) {
                    @Override
                    public void createSuccess(String customerName, int areaId, String userId) {
                        getOrderRequest(customerName, String.valueOf(areaId), userId);
                        dismiss();
                    }
                };

                if (!takeout.isShowing()) takeout.show();
            }
        });

        if (Utils.getSystemType(getApplicationContext()).equalsIgnoreCase("franchise")) {
            fab.hide();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    private void fetchOrderPendingRequest() {
        refreshRoom.setRefreshing(true);
        BusProvider.getInstance().post(new FetchOrderPendingRequest());
    }

    @Subscribe
    public void fetchOrderPendingResponse(FetchOrderPendingResponse fetchOrderPendingResponse) {
        refreshRoom.setRefreshing(false);
        if (fetchOrderPendingResponse.getResult().size() > 0) {
            new FetchOrderPendingAsync(this, fetchOrderPendingResponse.getResult(), getApplicationContext()).execute();
        }
    }

    @Override
    public void doneLoading(List list, String isFor) {
        refreshRoom.setRefreshing(false);
        switch (isFor) {
            case "roomstables":
                roomsTablesAdapter.addItems(list);
                break;
        }
    }

    @Override
    public void listClicked(RoomTableModel selectedItem) {
        Intent intent = new Intent();
        intent.putExtra("selected", GsonHelper.getGson().toJson(selectedItem));
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setRoomsTableAdapter() {
        roomsTablesAdapter = new RoomsTablesAdapter(
                new ArrayList<RoomTableModel>(),
                this,
                TakeOutActivity.this,
                Utils.getSystemType(getApplicationContext()));
        listTableRoomSelection.setLayoutManager(new GridLayoutManager(TakeOutActivity.this, 5));
        listTableRoomSelection.addItemDecoration(new SpacesItemDecoration( 10));
        listTableRoomSelection.setAdapter(roomsTablesAdapter);
        roomsTablesAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void apiError(ApiError apiError) {
        refreshRoom.setRefreshing(false);
        Toast.makeText(TakeOutActivity.this, apiError.message(), Toast.LENGTH_SHORT).show();
    }

    private void getOrderRequest(String customerName, String areaId, String userId) {
        refreshRoom.setRefreshing(true);
        BusProvider.getInstance().post(new GetOrderRequest(customerName, areaId, userId));
    }

    @Subscribe
    public void getOrderResponse(GetOrderResponse getOrderResponse) {
        refreshRoom.setRefreshing(false);
        fetchOrderPendingRequest();
    }

}
