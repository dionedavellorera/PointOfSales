package nerdvana.com.pointofsales.model;

public class TimerModel {
    private String time;
    private String shiftNumber;

    public TimerModel(String time) {
        this.time = time;
    }


    public TimerModel(String time, String shiftNumber) {
        this.time = time;
        this.shiftNumber = shiftNumber;
    }

    public String getShiftNumber() {
        return shiftNumber;
    }

    public String getTime() {
        return time;
    }
}
