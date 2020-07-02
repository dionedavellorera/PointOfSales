package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_requests.FetchDiscountRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomRequest;
import nerdvana.com.pointofsales.api_responses.FetchDiscountResponse;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.entities.RoomEntity;
import nerdvana.com.pointofsales.model.OpenWakeUpCallDialog;
import nerdvana.com.pointofsales.model.RoomWelcomeNotifier;
import nerdvana.com.pointofsales.model.WakeUpCallModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WakeUpCallReminderAsync extends AsyncTask<Void, Void, List<WakeUpCallModel>> {

    private List<WakeUpCallModel> wakeUpCallModels = new ArrayList<>();
    private long secsOfDate = 0;
    private int occupiedCount = 0;
    public WakeUpCallReminderAsync(long secsOfDate) {
//        SharedPreferenceManager.getString(null, ApplicationConstants.ROOM_JSON);
        this.secsOfDate = secsOfDate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<WakeUpCallModel> doInBackground(Void... voids) {

        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        FetchRoomRequest fetchRoomRequest = new FetchRoomRequest();
        Call<FetchRoomResponse> request = iUsers.sendRoomListRequest(fetchRoomRequest.getMapValue());
        request.enqueue(new Callback<FetchRoomResponse>() {
            @Override
            public void onResponse(Call<FetchRoomResponse> call, Response<FetchRoomResponse> response) {
                if (response.body().getResult().size() > 0) {
                    boolean hasWelcome = false;
                    for (FetchRoomResponse.Result r : response.body().getResult()) {
                        if (r.getStatus() != null) {
                            List<RoomEntity> insertedRoom = RoomEntity
                                    .findWithQuery(RoomEntity.class,
                                            "SELECT * FROM Room_Entity WHERE roomnumber = ?", String.valueOf(r.getRoomName()));

                            if (insertedRoom.size() == 0) {
                                RoomEntity roomEntity = new RoomEntity(r.getRoomName(), r.getType().getRoomType(),
                                        String.valueOf(r.getStatus().getCoreId()), r.getStatus().getRoomStatus(),
                                        r.getTransaction() != null ? r.getTransaction().getWakeUpCall() : "", 0,
                                        r.getTransaction() != null ? r.getTransaction().getTransaction().getControlNo() : "");
                                roomEntity.save();
                            } else {
                                RoomEntity room = insertedRoom.get(0);
                                boolean hasChanged = false;
                                if (r.getStatus().getCoreId() == 17 || r.getStatus().getCoreId() == 2) {
                                    room.setWake_up_call(r.getTransaction() != null ? r.getTransaction().getWakeUpCall() : "");
                                    room.setControl_number(r.getTransaction() != null ? r.getTransaction().getTransaction().getControlNo() : "");
                                    hasChanged = true;
                                }

                                if (!String.valueOf(r.getStatus().getCoreId()).equalsIgnoreCase(room.getRoom_status())) {
                                    room.setRoom_status(String.valueOf(r.getStatus().getCoreId()));
                                    room.setRoom_status_description(r.getStatus().getRoomStatus());
                                    room.setWake_up_call("");
                                    room.setIs_done(0);
                                    hasChanged = true;
                                }

                                if (r.getStatus().getCoreId() == 59) {
                                    hasWelcome = true;
                                }

                                if (hasChanged) {
                                    room.save();
                                }
                            }
                        }

                    }
                    BusProvider.getInstance().post(new RoomWelcomeNotifier(hasWelcome));
                }
            }

            @Override
            public void onFailure(Call<FetchRoomResponse> call, Throwable t) {

            }
        });



        return wakeUpCallModels;
    }

    @Override
    protected void onPostExecute(List<WakeUpCallModel> wakeUpCallModels) {
        super.onPostExecute(wakeUpCallModels);

        DateTimeFormatter finlDateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTime timeNow = finlDateTime.parseDateTime(formatter.format(new Date(secsOfDate * 1000L)));
        Log.d("MYWAKEUPCALL", timeNow.toString("yyyy-MM-dd HH:mm:ss"));
        List<RoomEntity> existingRoomsNeedToCall = RoomEntity
                .findWithQuery(RoomEntity.class,
                        "SELECT * FROM Room_Entity WHERE DATETIME(?) >= DATETIME(WakeUpCall) AND IsDone = 0 AND (RoomStatus = 17 OR RoomStatus = 2)", timeNow.toString("yyyy-MM-dd HH:mm:ss"));

        if (existingRoomsNeedToCall.size() > 0) {
            BusProvider.getInstance().post(new OpenWakeUpCallDialog(existingRoomsNeedToCall));
        }

    }
}
