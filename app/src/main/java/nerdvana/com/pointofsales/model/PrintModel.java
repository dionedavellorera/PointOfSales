package nerdvana.com.pointofsales.model;

public class PrintModel {
    private String message;
    private String roomNumber;
    private String type;
    private String data;
    private String roomType;

    private String dutyManager;
    private String remarks;

    private String kitchenPath;
    private String printerPath;

    private int newQty;
    public PrintModel(String message, String roomNumber, String type, String data, int newQty) {
        this.message = message;
        this.roomNumber = roomNumber;
        this.type = type;
        this.data = data;
        this.newQty = newQty;
    }

    public PrintModel(String message, String roomNumber, String type, String data) {
        this.message = message;
        this.roomNumber = roomNumber;
        this.type = type;
        this.data = data;
    }

    public PrintModel(String message, String roomNumber,
                      String type, String data,
                      String kitchenPath, String printerPath) {
        this.message = message;
        this.roomNumber = roomNumber;
        this.type = type;
        this.data = data;
        this.kitchenPath = kitchenPath;
        this.printerPath = printerPath;
    }

    public PrintModel(String message, String roomNumber,
                      String type, String data,
                      String roomType,String kitchenPath, String printerPath ) {
        this.message = message;
        this.roomNumber = roomNumber;
        this.type = type;
        this.data = data;
        this.roomType = roomType;
        this.kitchenPath = kitchenPath;
        this.printerPath = printerPath;
    }

    public PrintModel(String message, String roomNumber,
                      String type, String data,
                      String roomType, String dutyManager,
                      String remarks,
                      String kitchenPath, String printerPath) {
        this.message = message;
        this.roomNumber = roomNumber;
        this.type = type;
        this.data = data;
        this.roomType = roomType;
        this.dutyManager = dutyManager;
        this.remarks = remarks;
        this.kitchenPath = kitchenPath;
        this.printerPath = printerPath;
    }

    public int getNewQty() {
        return newQty;
    }

    public String getKitchenPath() {
        return kitchenPath;
    }

    public String getPrinterPath() {
        return printerPath;
    }

    public String getDutyManager() {
        return dutyManager;
    }

    public String getRemarks() {
        return remarks;
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
