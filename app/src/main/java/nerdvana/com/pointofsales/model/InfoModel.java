package nerdvana.com.pointofsales.model;

public class InfoModel {
    private String information;
    private String shiftNumber;
    public InfoModel(String information, String shiftNumber) {
        this.information = information;
        this.shiftNumber = shiftNumber;
    }

    public String getShiftNumber() {
        return shiftNumber;
    }

    public String getInformation() {
        return information;
    }
}
