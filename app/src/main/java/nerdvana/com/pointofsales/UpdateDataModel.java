package nerdvana.com.pointofsales;

public class UpdateDataModel {
    private String roomno;
    private String status;
    public UpdateDataModel(String roomno, String status) {
        this.roomno = roomno;
        this.status = status;
    }

    public String getRoomno() {
        return roomno;
    }

    public String getStatus() {
        return status;
    }
}
