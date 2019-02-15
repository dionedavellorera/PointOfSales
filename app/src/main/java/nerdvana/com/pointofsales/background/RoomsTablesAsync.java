package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.RatePrice;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.api_responses.RoomRateSub;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
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

        for (FetchRoomResponse.Result r : roomList) {
            List<RoomRateMain> roomRateMainList = new ArrayList<>();
            List<Integer> tempList = new ArrayList<>();
            if ((r.getRoomRate().size() == 0 &&
                    r.getType().getRoomRate().size() == 0 &&
                    r.getType().getParent().getRoomRate().size() == 0)
                    || r.getType().getFlag() == 0) {
                continue;
            }

            if (r.getType().getParent() != null) {
                for (RoomRateMain p : r.getType().getParent().getRoomRate()) {
                    if (!tempList.contains(p.getRoomRatePriceId())) {
                        roomRateMainList.add(p);
                        tempList.add(p.getRoomRatePriceId());
                    }


                }
            }


            for (RoomRateSub rateSub : r.getRoomRate()) {
                if (!tempList.contains(rateSub.getRoomRatePriceId())) {
                    roomRateMainList.add(
                            new RoomRateMain(rateSub.getId(), rateSub.getRoomRatePriceId(),
                                    r.getRoomTypeId(),rateSub.getCreatedBy(),
                                    rateSub.getCreatedAt(), rateSub.getUpdatedAt(),
                                    rateSub.getDeletedAt(), rateSub.getRatePrice())
                    );tempList.add(rateSub.getRoomRatePriceId());
                }


            }
            for (RoomRateMain rateList : r.getType().getRoomRate()) {
                if (!tempList.contains(rateList.getRoomRatePriceId())) {
                    roomRateMainList.add(rateList);
                    tempList.add(rateList.getRoomRatePriceId());
                }
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
                            r.getStatus().getRoomStatus(),
                            r.getRoomNo(),
                            roomRateMainList,
                            true,
                            "https://imageog.flaticon.com/icons/png/512/51/51882.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF",
                            String.valueOf(r.getCRoomStat()),
                            r.getStatus().getColor(),
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
