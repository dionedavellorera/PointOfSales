package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;

import java.util.List;

import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
import nerdvana.com.pointofsales.entities.RoomStatusEntity;

public class RoomStatusAsync extends AsyncTask<Void, Void, Void> {
    List<FetchRoomStatusResponse.Result> list;
    public RoomStatusAsync(List<FetchRoomStatusResponse.Result> list) {
        this.list = list;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        for (FetchRoomStatusResponse.Result r : list) {
            RoomStatusEntity roomStatusRecord = new RoomStatusEntity(
                r.getCoreId(), r.getRoomStatus(),
                r.getColor(), r.getIsBlink(),
                r.getIsTimer(), r.getIsName(),
                r.getIsBuddy(), r.getIsCancel()
            );
            roomStatusRecord.save();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
