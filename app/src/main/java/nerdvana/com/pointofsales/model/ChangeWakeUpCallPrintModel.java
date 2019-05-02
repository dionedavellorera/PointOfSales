package nerdvana.com.pointofsales.model;

public class ChangeWakeUpCallPrintModel {
    private String roomNumber;
    private String newWakeUpCall;

    public ChangeWakeUpCallPrintModel(String roomNumber, String newWakeUpCall) {
        this.roomNumber = roomNumber;
        this.newWakeUpCall = newWakeUpCall;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getNewWakeUpCall() {
        return newWakeUpCall;
    }
}
