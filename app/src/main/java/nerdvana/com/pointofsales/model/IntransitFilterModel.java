package nerdvana.com.pointofsales.model;

public class IntransitFilterModel {
    private int id;
    private String text;
    private boolean isActive;

    public IntransitFilterModel(int id, String text,
                                boolean isActive) {
        this.id = id;
        this.text = text;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isActive() {
        return isActive;
    }
}
