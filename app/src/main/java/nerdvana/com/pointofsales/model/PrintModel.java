package nerdvana.com.pointofsales.model;

public class PrintModel {
    private String message;
    private String roomNumber;
    private String type;
    private String data;
    private String roomType;

    public PrintModel(String message, String roomNumber, String type, String data) {
        this.message = message;
        this.roomNumber = roomNumber;
        this.type = type;
        this.data = data;
    }

    public PrintModel(String message, String roomNumber, String type, String data, String roomType) {
        this.message = message;
        this.roomNumber = roomNumber;
        this.type = type;
        this.data = data;
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }
}
