package nerdvana.com.pointofsales.model;

public class RoomWelcomeNotifier {
    private  boolean hasWelcome;

    public RoomWelcomeNotifier(boolean hasWelcome) {
        this.hasWelcome = hasWelcome;
    }

    public boolean isHasWelcome() {
        return hasWelcome;
    }
}
