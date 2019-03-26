package nerdvana.com.pointofsales.model;

public class ProgressBarModel {
    private boolean willStart;
    private String message;
    public ProgressBarModel(boolean willStart, String message) {
        this.willStart = willStart;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isWillStart() {
        return willStart;
    }
}
