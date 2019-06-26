package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nerdvana.com.pointofsales.RoomConstants;
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



            for (RoomRateSub rateSub : r.getRoomRate()) {
                if (!tempList.contains(rateSub.getRoomRatePriceId())) {
                    if (rateSub.getRatePrice() != null) {
                        roomRateMainList.add(
                                new RoomRateMain(rateSub.getId(), rateSub.getRoomRatePriceId(),
                                        r.getRoomTypeId(),rateSub.getCreatedBy(),
                                        rateSub.getCreatedAt(), rateSub.getUpdatedAt(),
                                        rateSub.getDeletedAt(), rateSub.getRatePrice())
                        );
                        tempList.add(rateSub.getRoomRatePriceId());
                    }

                }
            }

            if (r.getType() != null) {
                if (r.getType().getParent() != null) {
                    for (RoomRateMain p : r.getType().getParent().getRoomRate()) {
                        if (!tempList.contains(p.getRoomRatePriceId())) {
                            roomRateMainList.add(p);
                            tempList.add(p.getRoomRatePriceId());
                        }
                    }
                }

                if (r.getType().getRoomRate().size() > 0) {
                    for (RoomRateMain rateList : r.getType().getRoomRate()) {
                        if (!tempList.contains(rateList.getRoomRatePriceId())) {
                            roomRateMainList.add(rateList);
                            tempList.add(rateList.getRoomRatePriceId());
                        }
                    }
                }



            }
            int unpostedOrderCount = 0;

            Double amountSelected = 0.00;
            String checkoutExpected = "";
            if (String.valueOf(r.getStatus().getCoreId()).equalsIgnoreCase(RoomConstants.OCCUPIED) ||
                    String.valueOf(r.getStatus().getCoreId()).equalsIgnoreCase(RoomConstants.SOA)) {

                if (r.getTransaction() != null) {
                    if (r.getTransaction().getExpectedCheckOut() != null) {
                        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                        DateTime jodatime = dtf.parseDateTime(r.getTransaction().getExpectedCheckOut());
                        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MMM d h:m a");

                        amountSelected = r.getTransaction().getTransaction().getTotal();
                        checkoutExpected = dtfOut.print(jodatime);
                    } else {
                        checkoutExpected = "";
                    }
                } else {
                    checkoutExpected = "";
                }



            } else {
                amountSelected = 0.00;
                checkoutExpected = "--";
            }

            String otHours = "";
            if (r.getTransaction() != null) {
                if (r.getTransaction().getTransaction() != null) {
                    if (r.getTransaction().getTransaction().getOtHours() != null) {
                        otHours = String.valueOf(r.getTransaction().getTransaction().getOtHours());
                    }
                }
            }

            String controlNumber = "";
            if (r.getTransaction() != null) {
                if (r.getTransaction().getTransaction() != null) {
                    if (r.getTransaction().getTransaction().getControlNo() != null) {
                        controlNumber = r.getTransaction().getTransaction().getControlNo();
                    }
                }
            }

            String checkInTime = "NA";
            if (r.getTransaction() != null) {
                if (r.getTransaction().getCheckIn() != null) {
                    checkInTime = r.getTransaction().getCheckIn();
                }
            }

            int listPosition = 1;
            switch (r.getStatus().getCoreId()) {
                case 59:
                    listPosition = 1;
                    break;
                case 17:
                    listPosition = 2;
                    break;
                case 2:
                    listPosition = 3;
                    break;
                case 1:
                    listPosition = 4;
                    break;
                default:
                    listPosition = 5;
                    break;
            }

            productsModelList.add(
                    new RoomTableModel (
                            r.getId(),
                            r.getRoomTypeId(),
                            r.getType().getRoomType(),
                            0, //r.getType().getParent() == null ? 0 : r.getType().getParent().getId(),
                            "test parent", //r.getType().getParent() == null ? "NONE" : r.getType().getParent().getRoomType(),
                            r.getRoomAreaId(),
                            r.getArea().getRoomArea(),
                            r.getStatus().getRoomStatus(),
                            r.getRoomNo(),
                            roomRateMainList,
                            true,
                            "https://imageog.flaticon.com/icons/png/512/51/51882.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF",
                            String.valueOf(r.getCRoomStat()),
                            r.getStatus().getColor(),
                            amountSelected,
                            false,
                            controlNumber,
                            unpostedOrderCount,
                            r.getStatus().getIsBlink() == 1 ? true : false,
                            r.getStatus().getIsTimer() == 1 ? true : false,
                            checkoutExpected,
                            otHours,
                            checkInTime,
                            listPosition
                    )
            );

            Collections.sort(productsModelList);

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
