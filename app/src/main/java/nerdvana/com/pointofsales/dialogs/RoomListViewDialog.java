package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.AvailableGcAdapter;
import nerdvana.com.pointofsales.adapters.RoomListViewAdapter;
import nerdvana.com.pointofsales.api_requests.FetchRoomRequest;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomListViewDialog extends BaseDialog {

    private RecyclerView listRoomData;

    public RoomListViewDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_room_list_view, "ROOM LIST VIEW");
        listRoomData = findViewById(R.id.listRoomData);


        FetchRoomRequest fetchRoomRequest = new FetchRoomRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchRoomResponse> roomlistRequest = iUsers.sendRoomListRequest(
                fetchRoomRequest.getMapValue());

        roomlistRequest.enqueue(new Callback<FetchRoomResponse>() {
            @Override
            public void onResponse(Call<FetchRoomResponse> call, Response<FetchRoomResponse> response) {
                RoomListViewAdapter roomListViewAdapter = new RoomListViewAdapter(response.body().getResult());
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                listRoomData.setLayoutManager(llm);
                listRoomData.setAdapter(roomListViewAdapter);
                roomListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FetchRoomResponse> call, Throwable t) {

            }
        });



    }

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
