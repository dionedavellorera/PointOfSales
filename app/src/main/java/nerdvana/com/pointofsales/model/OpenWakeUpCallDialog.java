package nerdvana.com.pointofsales.model;

import java.util.List;

import nerdvana.com.pointofsales.entities.RoomEntity;

public class OpenWakeUpCallDialog {
    private List<RoomEntity> roomEntityList;
    public OpenWakeUpCallDialog(List<RoomEntity> roomEntityList) {
        this.roomEntityList = roomEntityList;
    }

    public List<RoomEntity> getRoomEntityList() {
        return roomEntityList;
    }

}
