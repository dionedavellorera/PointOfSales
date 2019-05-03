package nerdvana.com.pointofsales.model;

public class GuestReceiptInfoModel {
    private String name;
    private String address;
    private String tin;

    public GuestReceiptInfoModel(String name, String address, String tin) {
        this.name = name;
        this.address = address;
        this.tin = tin;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTin() {
        return tin;
    }
}
