package nerdvana.com.pointofsales.model;

import java.util.List;

import nerdvana.com.pointofsales.api_responses.RoomRateMain;

public class OwnRoomModel {
    private String roomId;
    private String roomTypeId;
    private String status;
    private String roomName;
    private List<RoomRateMain> priceList;

    public OwnRoomModel(String roomId, String status, String roomName, String roomTypeId, List<RoomRateMain> priceList) {
        this.roomId = roomId;
        this.status = status;
        this.roomName = roomName;
        this.roomTypeId = roomTypeId;
        this.priceList = priceList;
    }

    public List<RoomRateMain> getPriceList() {
        return priceList;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getStatus() {
        return status;
    }

    public String getRoomName() {
        return roomName;
    }
}
