package nerdvana.com.pointofsales.model;

public class GuestInfoPrintModel {
    private String name;
    private String address;
    private String tin;

    public GuestInfoPrintModel(String name, String address, String tin) {
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
