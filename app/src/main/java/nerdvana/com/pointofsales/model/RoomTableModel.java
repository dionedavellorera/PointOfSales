package nerdvana.com.pointofsales.model;

import java.util.List;

import nerdvana.com.pointofsales.api_responses.RoomRateMain;

public class RoomTableModel implements Comparable<RoomTableModel>{

    private int roomId;
    private int roomTypeId;
    private String roomType;
    private int roomTypeParentId;
    private String roomTypeParent;

    private int areaId;
    private String areaName;

    private String statusDescription;

    private String name;
    private List<RoomRateMain> price;
    private boolean isAvailable;
    private String imageUrl;
    private String status; //clean, occupied, dirty, etc, etc => CRoom_Stat
    private String hexColor;
    private double amountSelected;
    private boolean isTakeOut;
    private String controlNo;
    private int unpostedOrdersCount = 0;
    private boolean isBlink = false;
    private boolean isTimer = false;
    private String expectedCheckout;
    private String otHours;

    private String checkInTime;
    private int listPosition;

    public RoomTableModel(String controlNumber, boolean isTakeOut) {
        this.controlNo = controlNumber;
        this.isTakeOut = isTakeOut;
    }

    public RoomTableModel(int roomId, int roomTypeId,
                          String roomType, int roomTypeParentId,
                          String roomTypeParent, int areaId,
                          String areaName, String statusDescription,
                          String name, List<RoomRateMain> roomRateMainList,
                          boolean isAvailable, String imageUrl,
                          String status, String hexColor,
                          double amountSelected, boolean isTakeOut,
                          String controlNo, int unpostedOrdersCount,
                          boolean isBlink, boolean isTimer,
                          String expectedCheckout, String otHours,
                          String checkInTime, int listPosition) {
        this.roomId = roomId;
        this.roomTypeId = roomTypeId;
        this.roomType = roomType;
        this.roomTypeParentId = roomTypeParentId;
        this.roomTypeParent = roomTypeParent;
        this.areaId = areaId;
        this.areaName = areaName;
        this.statusDescription = statusDescription;
        this.name = name;
        this.price = roomRateMainList;
        this.isAvailable = isAvailable;
        this.imageUrl = imageUrl;
        this.status = status;
        this.hexColor = hexColor;
        this.amountSelected = amountSelected;
        this.isTakeOut = isTakeOut;
        this.controlNo = controlNo;
        this.unpostedOrdersCount = unpostedOrdersCount;
        this.isBlink = isBlink;
        this.isTimer = isTimer;
        this.expectedCheckout = expectedCheckout;
        this.otHours = otHours;
        this.checkInTime = checkInTime;
        this.listPosition = listPosition;

    }

    public int getListPosition() {
        return listPosition;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public String getOtHours() {
        return otHours;
    }

    public String getExpectedCheckout() {
        return expectedCheckout;
    }

    public boolean isBlink() {
        return isBlink;
    }

    public boolean isTimer() {
        return isTimer;
    }

    public int getUnpostedOrdersCount() {
        return unpostedOrdersCount;
    }

    public void setUnpostedOrdersCount(int unpostedOrdersCount) {
        this.unpostedOrdersCount = unpostedOrdersCount;
    }

    public String getControlNo() {
        return controlNo;
    }

    public boolean isTakeOut() {
        return isTakeOut;
    }

    public double getAmountSelected() {
        return amountSelected;
    }

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

    public RoomTableModel(List<RoomRateMain> price) {
        this.price = price;
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

    public List<RoomRateMain> getPrice() {
        return price;
    }

    public void setPrice(List<RoomRateMain> price) {
        this.price = price;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
    }

    @Override
    public int compareTo(RoomTableModel o) {
        if (listPosition > o.getListPosition()) {
            return 1;
        } else if (listPosition < o.getListPosition()) {
            return -1;
        }
        return 0;
    }
}
