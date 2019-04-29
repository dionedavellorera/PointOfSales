package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.api_responses.FetchOrderPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.api_responses.RoomRateSub;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.model.RoomTableModel;

public class FetchOrderPendingAsync extends AsyncTask<RoomTableModel, Void, List<RoomTableModel>> {
    private AsyncContract asyncContract;
    private List<FetchOrderPendingResponse.Result> roomList;
    public FetchOrderPendingAsync(AsyncContract asyncContract, List<FetchOrderPendingResponse.Result> roomList) {
        this.asyncContract = asyncContract;
        this.roomList = roomList;
    }

    @Override
    protected List<RoomTableModel> doInBackground(RoomTableModel... productsModels) {
        List<RoomTableModel> productsModelList = new ArrayList<>();

        for (FetchOrderPendingResponse.Result r : roomList) {

            productsModelList.add(
                    new RoomTableModel (
                            r.getId(),
                            0,
                            "",
                            0, //r.getType().getParent() == null ? 0 : r.getType().getParent().getId(),
                            "", //r.getType().getParent() == null ? "NONE" : r.getType().getParent().getRoomType(),
                            r.getRoomAreaId() != null ? ((Number)r.getRoomAreaId()).intValue() : 0,
                            "",
                            "",
                            String.valueOf(r.getCustomerId()),
                            new ArrayList<RoomRateMain>(),
                            true,
                            "https://imageog.flaticon.com/icons/png/512/51/51882.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF",
                            "",
                            "",
                            r.getTotal(),
                            true,
                            r.getControlNo(),
                            0,
                            r.getIsSoa() == 1 ? true : false,
                            false,
                            "",
                            "",
                            ""

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

    private List<RoomTableModel> priceList(List<RoomTableModel> list) {
        List<RoomTableModel> rtm = new ArrayList<>(list);
        for (RoomTableModel r : rtm) {
            List<RoomRateMain> newList = new ArrayList<>();
            for (RoomRateMain rrm : r.getPrice()) {
                if (!newList.contains(rrm)) {
                    newList.add(rrm);
                }
            }
            r.setPrice(newList);
        }
        return rtm;
    }
}