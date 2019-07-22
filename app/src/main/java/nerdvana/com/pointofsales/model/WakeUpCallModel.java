package nerdvana.com.pointofsales.model;

public class WakeUpCallModel {
    private String roomNumber;
    private String wakeUpCallTime;
    private String wakeUpCallTimeInSeconds;

    public WakeUpCallModel(String roomNumber, String wakeUpCallTime,
                           String wakeUpCallTimeInSeconds) {
        this.roomNumber = roomNumber;
        this.wakeUpCallTime = wakeUpCallTime;
        this.wakeUpCallTimeInSeconds = wakeUpCallTimeInSeconds;
    }

    public String getWakeUpCallTimeInSeconds() {
        return wakeUpCallTimeInSeconds;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getWakeUpCallTime() {
        return wakeUpCallTime;
    }
}
