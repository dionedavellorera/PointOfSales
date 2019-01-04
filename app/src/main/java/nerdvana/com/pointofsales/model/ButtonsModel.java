package nerdvana.com.pointofsales.model;

public class ButtonsModel {
    private String name;
    private String imageUrl;

    public ButtonsModel(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
