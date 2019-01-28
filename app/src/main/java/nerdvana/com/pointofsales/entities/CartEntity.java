package nerdvana.com.pointofsales.entities;


import com.orm.SugarRecord;

public class CartEntity extends SugarRecord<CartEntity> {

    private Long productId;
    private String name;
    private String shortName;
    private double price;
    private double vat;
    private boolean isAvailable;
    private String imageUrls;
    private boolean isVattable;
    private boolean isSerialNumberRequired;
    private int lowStackCount;
    private int productStatus;
    private String _transId;
    private int quantity;


    public CartEntity() {}

    public CartEntity(String name, String shortName,
                      double price, double vat,
                      boolean isAvailable, String imageUrls,
                      boolean isVattable, boolean isSerialNumberRequired,
                      int lowStackCount, int productStatus,
                      String _transId, int quantity,
                      Long productId) {
        this.name = name;
        this.shortName = shortName;
        this.price = price;
        this.vat = vat;
        this.isAvailable = isAvailable;
        this.imageUrls = imageUrls;
        this.isVattable = isVattable;
        this.isSerialNumberRequired = isSerialNumberRequired;
        this.lowStackCount = lowStackCount;
        this.productStatus = productStatus;
        this._transId = _transId;
        this.quantity = quantity;
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String get_transId() {
        return _transId;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public double getPrice() {
        return price;
    }

    public double getVat() {
        return vat;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public boolean isVattable() {
        return isVattable;
    }

    public boolean isSerialNumberRequired() {
        return isSerialNumberRequired;
    }

    public int getLowStackCount() {
        return lowStackCount;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }
}
