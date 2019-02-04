package nerdvana.com.pointofsales.model;

public class RoomTableModel {
    private String name;
    private String price;
    private boolean isAvailable;
    private String imageUrl;
    private String status; //clean, occupied, dirty, etc, etc
    private String hexColor;
    public RoomTableModel(String name, String price,
                          boolean isAvailable, String imageUrl,
                          String status, String hexColor) {
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
        this.imageUrl = imageUrl;
        this.status = status;
        this.hexColor = hexColor;
    }

    public String getHexColor() {
        return hexColor;
    }

    public String getStatus() {
        return status;
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
