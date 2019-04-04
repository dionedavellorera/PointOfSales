package nerdvana.com.pointofsales.model;

public class SwitchRoomPrintModel {
    private String fromRoomNumber;
    private String fromRoomType;
    private String toRoomNumber;
    private String toRoomType;
    private String checkInTime;
    private String userId;

    public SwitchRoomPrintModel(String fromRoomNumber, String fromRoomType,
                                String toRoomNumber, String toRoomType,
                                String checkInTime, String userId) {
        this.fromRoomNumber = fromRoomNumber;
        this.fromRoomType = fromRoomType;
        this.toRoomNumber = toRoomNumber;
        this.toRoomType = toRoomType;
        this.checkInTime = checkInTime;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getFromRoomNumber() {
        return fromRoomNumber;
    }

    public String getFromRoomType() {
        return fromRoomType;
    }

    public String getToRoomNumber() {
        return toRoomNumber;
    }

    public String getToRoomType() {
        return toRoomType;
    }

    public String getCheckInTime() {
        return checkInTime;
    }
}
