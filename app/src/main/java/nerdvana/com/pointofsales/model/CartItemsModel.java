package nerdvana.com.pointofsales.model;

import java.util.List;

import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;

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
    private boolean isPosted;
    private Double markUp;
    private int isPriceChanged;
    private Double unitPrice;
    private boolean isSelected;
    private String postId;
    private boolean forVoid;
    private String type;
    private boolean isUpdated;

    private List<AddRateProductModel.AlaCarte> alaCarteList;
    private List<AddRateProductModel.Group> groupList;
    private FetchRoomPendingResponse.TransactionPostFreebies transactionPostFreebies;

    public CartItemsModel(String controlNo, int roomId,
                          int productId, int roomTypeId,
                          int roomRateId, int roomRatePriceId,
                          String name, boolean isProduct,
                          double amount, int coreId,
                          int quantity, boolean isPosted,
                          Double markUp, int isPriceChanged,
                          Double unitPrice, boolean isSelected,
                          String postId, boolean forVoid,
                          String type,
                          List<AddRateProductModel.AlaCarte> alaCarteList,
                          List<AddRateProductModel.Group> groupList,
                          boolean isUpdated,
                          FetchRoomPendingResponse.TransactionPostFreebies transactionPostFreebies) {
        this.transactionPostFreebies = transactionPostFreebies;
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
        this.isPosted = isPosted;
        this.markUp = markUp;
        this.isPriceChanged = isPriceChanged;
        this.unitPrice = unitPrice;
        this.isSelected = isSelected;
        this.postId = postId;
        this.forVoid = forVoid;
        this.type = type;
        this.alaCarteList = alaCarteList;
        this.groupList = groupList;
        this.isUpdated = isUpdated;
    }

    public FetchRoomPendingResponse.TransactionPostFreebies getTransactionPostFreebies() {
        return transactionPostFreebies;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public List<AddRateProductModel.AlaCarte> getAlaCarteList() {
        return alaCarteList;
    }

    public List<AddRateProductModel.Group> getGroupList() {
        return groupList;
    }

    public String getType() {
        return type;
    }

    public boolean isForVoid() {
        return forVoid;
    }

    public void setForVoid(boolean forVoid) {
        this.forVoid = forVoid;
    }

    public String getPostId() {
        return postId;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setIsPriceChanged(int isPriceChanged) {
        this.isPriceChanged = isPriceChanged;
    }

    public int getIsPriceChanged() {
        return isPriceChanged;
    }

    public Double getMarkUp() {
        return markUp;
    }

    public boolean isPosted() {
        return isPosted;
    }

    public void setPosted(boolean posted) {
        isPosted = posted;
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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
