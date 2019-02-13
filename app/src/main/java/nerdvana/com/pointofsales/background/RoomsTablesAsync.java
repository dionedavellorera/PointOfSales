package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.RoomConstants;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.RoomRate;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.RoomTableModel;

public class RoomsTablesAsync extends AsyncTask<RoomTableModel, Void, List<RoomTableModel>>  {
    private AsyncContract asyncContract;
    private List<FetchRoomResponse.Result> roomList;
    public RoomsTablesAsync(AsyncContract asyncContract, List<FetchRoomResponse.Result> roomList) {
        this.asyncContract = asyncContract;
        this.roomList = roomList;
    }

    @Override
    protected List<RoomTableModel> doInBackground(RoomTableModel... productsModels) {
        List<RoomTableModel> productsModelList = new ArrayList<>();
        List<RoomRate> roomRateList = new ArrayList<>();
        for (FetchRoomResponse.Result r : roomList) {
            for (RoomRate rateList : r.getType().getRoomRate()) {
                roomRateList.add(rateList);
            }

            productsModelList.add(
                    new RoomTableModel (
                            r.getId(),
                            r.getRoomTypeId(),
                            r.getType().getRoomType(),
                            0, //r.getType().getParent() == null ? 0 : r.getType().getParent().getId(),
                            "test parent", //r.getType().getParent() == null ? "NONE" : r.getType().getParent().getRoomType(),
                            r.getArea().getId(),
                            r.getArea().getRoomArea(),
                            r.getStatus().get(0).getRoomStatus(),
                            r.getRoomNo(),
                            roomRateList,
                            true,
                            "https://imageog.flaticon.com/icons/png/512/51/51882.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF",
                            String.valueOf(r.getCRoomStat()),
                            r.getStatus().get(0).getColor(),
                            10000
                    )
            );
        }
        return productsModelList;
    }

    @Override
    protected void onPostExecute(List<RoomTableModel> productsModels) {
        this.asyncContract.doneLoading(productsModels, "roomstables");
        super.onPostExecute(productsModels);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
