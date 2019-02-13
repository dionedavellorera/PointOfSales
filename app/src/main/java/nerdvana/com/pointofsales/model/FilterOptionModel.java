package nerdvana.com.pointofsales.model;

public class FilterOptionModel {
    private String name;
    private boolean isSelected;
    private int statusId;

    public FilterOptionModel(String name, boolean isSelected, int statusId) {
        this.name = name;
        this.isSelected = isSelected;
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

