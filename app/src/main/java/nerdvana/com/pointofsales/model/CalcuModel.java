package nerdvana.com.pointofsales.model;

public class CalcuModel {
    private String value;
    private String display;

    public CalcuModel(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public String getValue() {
        return value;
    }

    public String getDisplay() {
        return display;
    }
}
