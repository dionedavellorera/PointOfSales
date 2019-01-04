package nerdvana.com.pointofsales.model;

public class RoomTableModel {
    private String name;
    private String price;
    private boolean isAvailable;
    private String imageUrl;

    public RoomTableModel(String name, String price, boolean isAvailable, String imageUrl) {
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
