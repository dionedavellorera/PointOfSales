package nerdvana.com.pointofsales.entities;


import com.orm.SugarRecord;

public class CartEntity extends SugarRecord<CartEntity> {
    private String controlNo;
    private int roomId;
    private int productId;
    private int roomTypeId;
    private int roomRateId;
    private int roomRatePriceId;
    private String name;
    private boolean isProduct;
    private double amount;


//    private Long productId;
//    private String name;
//    private String shortName;
//    private double price;
//    private double vat;
//    private boolean isAvailable;
//    private String imageUrls;
//    private boolean isVattable;
//    private boolean isSerialNumberRequired;
//    private int lowStackCount;
//    private int productStatus;
//    private String _transId;
//    private int quantity;


    public CartEntity() {}

    public CartEntity(String controlNo, int roomId,
                      int productId, int roomTypeId,
                      int roomRateId, int roomRatePriceId,
                      String name, boolean isProduct,
                      double amount) {
        this.controlNo = controlNo;
        this.roomId = roomId;
        this.productId = productId;
        this.roomTypeId = roomTypeId;
        this.roomRateId = roomRateId;
        this.roomRatePriceId = roomRatePriceId;
        this.name = name;
        this.isProduct = isProduct;
        this.amount = amount;
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

    //    public CartEntity(String name, String shortName,
//                      double price, double vat,
//                      boolean isAvailable, String imageUrls,
//                      boolean isVattable, boolean isSerialNumberRequired,
//                      int lowStackCount, int productStatus,
//                      String _transId, int quantity,
//                      Long productId) {
//        this.name = name;
//        this.shortName = shortName;
//        this.price = price;
//        this.vat = vat;
//        this.isAvailable = isAvailable;
//        this.imageUrls = imageUrls;
//        this.isVattable = isVattable;
//        this.isSerialNumberRequired = isSerialNumberRequired;
//        this.lowStackCount = lowStackCount;
//        this.productStatus = productStatus;
//        this._transId = _transId;
//        this.quantity = quantity;
//        this.productId = productId;
//    }

//    public Long getProductId() {
//        return productId;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public String get_transId() {
//        return _transId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getShortName() {
//        return shortName;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public double getVat() {
//        return vat;
//    }
//
//    public boolean isAvailable() {
//        return isAvailable;
//    }
//
//    public String getImageUrls() {
//        return imageUrls;
//    }
//
//    public boolean isVattable() {
//        return isVattable;
//    }
//
//    public boolean isSerialNumberRequired() {
//        return isSerialNumberRequired;
//    }
//
//    public int getLowStackCount() {
//        return lowStackCount;
//    }
//
//    public int getProductStatus() {
//        return productStatus;
//    }
//
//    public void setProductStatus(int productStatus) {
//        this.productStatus = productStatus;
//    }
}
