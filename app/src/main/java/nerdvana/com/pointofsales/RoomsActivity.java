package nerdvana.com.pointofsales;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.orm.query.Select;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.api_requests.FetchRoomRequest;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.background.RoomsTablesAsync;
import nerdvana.com.pointofsales.entities.RoomStatusEntity;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.postlogin.RightFrameFragment;
import nerdvana.com.pointofsales.postlogin.adapter.RoomsTablesAdapter;

public class RoomsActivity extends AppCompatActivity implements AsyncContract, SelectionContract {

    private RoomsTablesAdapter roomsTablesAdapter;
    private RecyclerView listTableRoomSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listTableRoomSelection = findViewById(R.id.listTableRoomSelection);


        setRoomsTableAdapter();

//        new RoomsTablesAsync(this, new ArrayList<FetchRoomResponse.Result>()).execute();
        sendRoomListRequest();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
        return true;
//        return super.onSupportNavigateUp();
    }

    @Override
    public void doneLoading(List list, String isFor) {
        switch (isFor) {
            case "roomstables":
                roomsTablesAdapter.addItems(list);
                break;
        }
    }

    private void setRoomsTableAdapter() {
        roomsTablesAdapter = new RoomsTablesAdapter(new ArrayList<RoomTableModel>(), this);
        listTableRoomSelection.setLayoutManager(new GridLayoutManager(RoomsActivity.this, 5));
        listTableRoomSelection.setAdapter(roomsTablesAdapter);
    }

    @Override
    public void listClicked(RoomTableModel selectedItem) {
        Intent intent = new Intent();
        intent.putExtra("selected", GsonHelper.getGson().toJson(selectedItem));
        setResult(RESULT_OK, intent);
        finish();
    }

    private void sendRoomListRequest() {
        BusProvider.getInstance().post(new FetchRoomRequest());
    }

    @Subscribe
    public void roomlistResponse(FetchRoomResponse fetchRoomResponse) {
        if (fetchRoomResponse.getResult().size() > 0) {
            new RoomsTablesAsync(this, fetchRoomResponse.getResult()).execute();
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
}
