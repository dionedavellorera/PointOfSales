package nerdvana.com.pointofsales.model;

public class ProgressBarModel {
    private boolean willStart;

    public ProgressBarModel(boolean willStart) {
        this.willStart = willStart;
    }

    public boolean isWillStart() {
        return willStart;
    }
}
