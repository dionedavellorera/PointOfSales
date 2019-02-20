package nerdvana.com.pointofsales.model;

public class CartItemsModel {
    private String controlNo;
    private int roomId;
    private int productId;
    private int roomTypeId;
    private int roomRateId;
    private int roomRatePriceId;
    private String name;
    private boolean isProduct;
    private double amount;
    private int coreId;
    private int quantity;

    public CartItemsModel(String controlNo, int roomId,
                          int productId, int roomTypeId,
                          int roomRateId, int roomRatePriceId,
                          String name, boolean isProduct,
                          double amount, int coreId,
                          int quantity) {
        this.controlNo = controlNo;
        this.roomId = roomId;
        this.productId = productId;
        this.roomTypeId = roomTypeId;
        this.roomRateId = roomRateId;
        this.roomRatePriceId = roomRatePriceId;
        this.name = name;
        this.isProduct = isProduct;
        this.amount = amount;
        this.coreId = coreId;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCoreId() {
        return coreId;
    }

    public String getControlNo() {
        return controlNo;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getProductId() {
        return productId;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public int getRoomRateId() {
        return roomRateId;
    }

    public int getRoomRatePriceId() {
        return roomRatePriceId;
    }

    public String getName() {
        return name;
    }

    public boolean isProduct() {
        return isProduct;
    }

    public double getAmount() {
        return amount;
    }
}
