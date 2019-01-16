package nerdvana.com.pointofsales.model;

public class ButtonsModel {
    private String name;
    private String imageUrl;
    private int position;
    public ButtonsModel(String name, String imageUrl, int position) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
