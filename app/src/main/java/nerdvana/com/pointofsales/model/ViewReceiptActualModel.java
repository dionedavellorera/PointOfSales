package nerdvana.com.pointofsales.model;

public class ViewReceiptActualModel {
    private String company;
    private String address;
    private String telNumber;
    private String serialNumber;
    private String regTin;
    private String permitNumber;
    private String minNumber;
    private String roomNumber;

    private String cashier;
    private String roomBoy;
    private String checkInTime;
    private String expectedCheckOut;
    private String receiptNumber;
    private String machineNumber;

    public ViewReceiptActualModel(String company, String address,
                                  String telNumber, String serialNumber,
                                  String regTin, String permitNumber,
                                  String minNumber, String roomNumber,
                                  String cashier, String roomBoy,
                                  String checkInTime, String expectedCheckOut,
                                  String receiptNumber, String machineNumber) {
        this.company = company;
        this.address = address;
        this.telNumber = telNumber;
        this.serialNumber = serialNumber;
        this.regTin = regTin;
        this.permitNumber = permitNumber;
        this.minNumber = minNumber;
        this.roomNumber = roomNumber;
        this.cashier = cashier;
        this.roomBoy = roomBoy;
        this.checkInTime = checkInTime;
        this.expectedCheckOut = expectedCheckOut;
        this.receiptNumber = receiptNumber;
        this.machineNumber = machineNumber;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getRegTin() {
        return regTin;
    }

    public String getPermitNumber() {
        return permitNumber;
    }

    public String getMinNumber() {
        return minNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getCashier() {
        return cashier;
    }

    public String getRoomBoy() {
        return roomBoy;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public String getExpectedCheckOut() {
        return expectedCheckOut;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public String getMachineNumber() {
        return machineNumber;
    }
}
