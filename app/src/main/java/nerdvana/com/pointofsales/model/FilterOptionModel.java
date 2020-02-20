package nerdvana.com.pointofsales.model;

public class FilterOptionModel {
    private String name;
    private int statusCount = 0;
    private String hexColor = "#ffffff";
    private boolean isSelected;
    private int statusId;

//    , int statusCount, String hexColor
    public FilterOptionModel(String name, boolean isSelected, int statusId, String hexColor) {
        this.name = name;
        this.isSelected = isSelected;
        this.statusId = statusId;
//        this.statusCount = statusCount;
        this.hexColor = hexColor;
    }

    public int getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(int statusCount) {
        this.statusCount = statusCount;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
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

