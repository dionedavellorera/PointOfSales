package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.RoomListViewAdapter;
import nerdvana.com.pointofsales.api_requests.FetchRoomRequest;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.model.IntransitFilterModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RoomListViewDialog extends BaseDialog implements View.OnClickListener{

    private RecyclerView listRoomData;
    private FloatingActionButton fab;
    private String globalServerTime;

    private Button btnAction;

    private List<IntransitFilterModel> intransitFilterList = new ArrayList<>();

    public RoomListViewDialog(@NonNull Context context, String globalServerTime) {
        super(context);
        this.globalServerTime = globalServerTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_room_list_view, "Room List View");
        setIntransitListData();


        listRoomData = findViewById(R.id.listRoomData);
        btnAction = findViewById(R.id.btnAction);
        btnAction.setVisibility(View.VISIBLE);
        btnAction.setOnClickListener(this);
        fab = findViewById(R.id.fab);
        reloadIntransitData(1);


    }

    private void reloadIntransitData(int id) {
        FetchRoomRequest fetchRoomRequest = new FetchRoomRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchRoomResponse> roomlistRequest = iUsers.sendRoomListRequest(
                fetchRoomRequest.getMapValue());

        roomlistRequest.enqueue(new Callback<FetchRoomResponse>() {
            @Override
            public void onResponse(Call<FetchRoomResponse> call, final Response<FetchRoomResponse> response) {

                List<FetchRoomResponse.Result> tmpRoomList = new ArrayList<>();
                for (FetchRoomResponse.Result r : response.body().getResult()) {
                    if (r.getStatus().getCoreId() == 2 || r.getStatus().getCoreId() == 17) {
                        tmpRoomList.add(r);
                    }
                }


                Collections.sort(tmpRoomList, new Comparator<FetchRoomResponse.Result>() {
                    @Override
                    public int compare(FetchRoomResponse.Result o1, FetchRoomResponse.Result o2) {

                        switch (id) {
                            case 1: //room number
                                if (o1.getTransaction() != null && o2.getTransaction() != null) {
                                    if (Integer.valueOf(o1.getRoomNo()) < Integer.valueOf(o2.getRoomNo())) {
                                        return -1;
                                    } else {
                                        return 1;
                                    }
                                }
                                break;
                            case 2: //date in
                                if (o1.getTransaction() != null && o2.getTransaction() != null) {
                                    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                                    if (formatter.parseDateTime(o1.getTransaction().getCheckIn())
                                            .isBefore(formatter.parseDateTime(o2.getTransaction().getCheckIn()))) {
                                        return -1;
                                    } else  {
                                        return 1;
                                    }
                                }
                                break;
                            case 3: //wake up call
                                if (o1.getTransaction() != null && o2.getTransaction() != null) {
                                    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                                    if (formatter.parseDateTime(o1.getTransaction().getWakeUpCall())
                                            .isBefore(formatter.parseDateTime(o2.getTransaction().getWakeUpCall()))) {
                                        return -1;
                                    } else  {
                                        return 1;
                                    }
                                }
                                break;
                            case 4: //fnb
                                if (o1.getTransaction() != null && o2.getTransaction() != null) {
                                    if (o1.getTransaction().getTransaction().getFnb() < o2.getTransaction().getTransaction().getFnb()) {
                                        return -1;
                                    } else {
                                        return 1;
                                    }
                                }
                                break;
                            case 5: //deposit
                                if (o1.getTransaction() != null && o2.getTransaction() != null) {
                                    if (o1.getTransaction().getTransaction().getAdvance() < o2.getTransaction().getTransaction().getAdvance()) {
                                        return -1;
                                    } else {
                                        return 1;
                                    }
                                }
                                break;
                        }

                        return 0;

                    }
                });

                RoomListViewAdapter roomListViewAdapter = new RoomListViewAdapter(tmpRoomList, globalServerTime);
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                listRoomData.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                listRoomData.setLayoutManager(llm);
                listRoomData.setAdapter(roomListViewAdapter);
                roomListViewAdapter.notifyDataSetChanged();

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        instransitClicked(response.body().getResult());
                    }
                });

            }
            @Override
            public void onFailure(Call<FetchRoomResponse> call, Throwable t) {

            }
        });

    }

    private void setIntransitListData() {
        intransitFilterList.add(new IntransitFilterModel(1,"ROOM NUMBER", true));
        intransitFilterList.add(new IntransitFilterModel(2,"DATE IN", false));
        intransitFilterList.add(new IntransitFilterModel(3,"WAKE UP CALL", false));
        intransitFilterList.add(new IntransitFilterModel(4,"FNB", false));
        intransitFilterList.add(new IntransitFilterModel(5,"DEPOSIT", false));
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


    public abstract void instransitClicked(List<FetchRoomResponse.Result> data);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAction:
                IntransitFilterDialog intransitFilterDialog = new IntransitFilterDialog(getContext(), intransitFilterList) {
                    @Override
                    public void filterSelected(int id) {
                        reloadIntransitData(id);
                    }
                };
                intransitFilterDialog.show();
                break;
        }
    }
}

