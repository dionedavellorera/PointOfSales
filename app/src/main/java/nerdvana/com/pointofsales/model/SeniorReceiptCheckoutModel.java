package nerdvana.com.pointofsales.model;

public class SeniorReceiptCheckoutModel {
    private String name;
    private String scPwdId;
    private String address;
    private String tin;
    private String businessStyle;

    public SeniorReceiptCheckoutModel(String name, String scPwdId, String address, String tin, String businessStyle) {
        this.name = name;
        this.scPwdId = scPwdId;
        this.address = address;
        this.tin = tin;
        this.businessStyle = businessStyle;
    }

    public String getName() {
        return name;
    }

    public String getScPwdId() {
        return scPwdId;
    }

    public String getAddress() {
        return address;
    }

    public String getTin() {
        return tin;
    }

    public String getBusinessStyle() {
        return businessStyle;
    }
}
