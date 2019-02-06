package nerdvana.com.pointofsales.model;

public class RoomTableModel {

    private int roomId;
    private int roomTypeId;
    private String roomType;
    private int roomTypeParentId;
    private String roomTypeParent;

    private int areaId;
    private String areaName;

    private String statusDescription;

    private String name;
    private String price;
    private boolean isAvailable;
    private String imageUrl;
    private String status; //clean, occupied, dirty, etc, etc => CRoom_Stat
    private String hexColor;

    public RoomTableModel(int roomId, int roomTypeId,
                          String roomType, int roomTypeParentId,
                          String roomTypeParent, int areaId,
                          String areaName, String statusDescription,
                          String name, String price,
                          boolean isAvailable, String imageUrl,
                          String status, String hexColor) {
        this.roomId = roomId;
        this.roomTypeId = roomTypeId;
        this.roomType = roomType;
        this.roomTypeParentId = roomTypeParentId;
        this.roomTypeParent = roomTypeParent;
        this.areaId = areaId;
        this.areaName = areaName;
        this.statusDescription = statusDescription;
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
        this.imageUrl = imageUrl;
        this.status = status;
        this.hexColor = hexColor;
    }

//    public RoomTableModel(String name, String price,
//                          boolean isAvailable, String imageUrl,
//                          String status, String hexColor) {
//        this.name = name;
//        this.price = price;
//        this.isAvailable = isAvailable;
//        this.imageUrl = imageUrl;
//        this.status = status;
//        this.hexColor = hexColor;
//    }


    public int getRoomId() {
        return roomId;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getRoomTypeParentId() {
        return roomTypeParentId;
    }

    public String getRoomTypeParent() {
        return roomTypeParent;
    }

    public int getAreaId() {
        return areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getStatusDescription() {
        return statusDescription;
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

    public String getStatus() {
        return status;
    }

    public String getHexColor() {
        return hexColor;
    }
}
