package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.RoomConstants;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
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
//        for (int i = 0; i < 15; i++) {
//            productsModelList.add(
//                    new RoomTableModel(
//                            i,
//                            i,
//                            "SUPER STANDARD",
//                            i,
//                            "STANDARD",
//                            i,
//                            "CANCEY",
//                            "CLEAN",
//                            "RM" + i,
//                            "0.00",
//                            true,
//                            "https://imageog.flaticon.com/icons/png/512/51/51882.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF",
//                            "1",
//                            "#000000"
//                    )
//            );
//        }
        for (FetchRoomResponse.Result r : roomList) {
            productsModelList.add(
                    new RoomTableModel(
                            r.getId(),
                            r.getRoomTypeId(),
                            r.getType().getRoomType(),
                            r.getType().getParent().getId(),
                            r.getType().getParent().getRoomType(),
                            r.getArea().getId(),
                            r.getArea().getRoomArea(),
                            r.getStatus().get(0).getRoomStatus(),
                            r.getRoomNo(),
                            "0.00",
                            true,
                            "https://imageog.flaticon.com/icons/png/512/51/51882.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF",
                            String.valueOf(r.getCRoomStat()),
                            r.getStatus().get(0).getColor()
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
