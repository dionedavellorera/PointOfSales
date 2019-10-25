package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.model.OpenWakeUpCallDialog;
import nerdvana.com.pointofsales.model.WakeUpCallModel;

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

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");


        TypeToken<List<FetchRoomResponse.Result>> fetchRoomToken = new TypeToken<List<FetchRoomResponse.Result>>() {
        };
        List<FetchRoomResponse.Result> roomList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(null, ApplicationConstants.ROOM_JSON), fetchRoomToken.getType());

        if (roomList != null) {
            if (roomList.size() > 0) {
                for (FetchRoomResponse.Result r : roomList) {
                    if (r.getStatus().getCoreId() == 2 || r.getStatus().getCoreId() == 17) {
                        if (r.getTransaction() != null) {

                            if (!TextUtils.isEmpty(r.getTransaction().getWakeUpCall())) {
                                DateTime dateTime = dateTimeFormatter.parseDateTime(r.getTransaction().getWakeUpCall());
                                if (secsOfDate >= (dateTime.getMillis() / 1000)) {
                                    occupiedCount += 1;
                                    wakeUpCallModels.add(new WakeUpCallModel(
                                            r.getRoomNo(),
                                            r.getTransaction().getWakeUpCall(),
                                            String.valueOf(dateTime.getMillis())
                                    ));

                                }
                            }
                        }
                    }
                }

            }
        }


        return wakeUpCallModels;
    }

    @Override
    protected void onPostExecute(List<WakeUpCallModel> wakeUpCallModels) {
        super.onPostExecute(wakeUpCallModels);

        TypeToken<List<String>> roomToken = new TypeToken<List<String>>() {};
        List<String> wul =
                GsonHelper
                        .getGson()
                        .fromJson(
                                SharedPreferenceManager.getString(null, "room_no_list"),
                                roomToken.getType());

        if (occupiedCount > 0) {
            if (wakeUpCallModels.size() > 0) {
                if (wul != null) {



                    if (wul.size() != occupiedCount) {
//                        BusProvider.getInstance().post(new OpenWakeUpCallDialog(wakeUpCallModels));
                    }
                } else {
//                    BusProvider.getInstance().post(new OpenWakeUpCallDialog(wakeUpCallModels));
                }
            }
        }

    }
}
