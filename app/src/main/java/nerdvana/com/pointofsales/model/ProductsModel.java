package nerdvana.com.pointofsales.model;

import com.orm.SugarRecord;

import java.util.List;

import nerdvana.com.pointofsales.api_responses.FetchProductsResponse;

public class ProductsModel extends SugarRecord<ProductsModel> {
    private String name;
    private String shortName;
    private double price;
    private double vat;
    private boolean isAvailable;
    private String[] imageUrls;
    private boolean isVattable;
    private boolean isSerialNumberRequired;
    private int lowStackCount;
    private int productStatus;
    private List<ProductsModel> productsList;
    private boolean isSelected;
    private int productId;
    private boolean isRoom = false;
    private int qty = 1;
    private Double markUp;
    private int isPriceChanged;
    private String department;
    private double unitPrice;

    private List<FetchProductsResponse.BranchAlaCart> branchAlaCartList;
    private List<FetchProductsResponse.BranchGroup> branchGroupList;


    public ProductsModel() {}

    public ProductsModel(String name, double price,
                         double vat, boolean isAvailable,
                         String[] imageUrls, boolean isVattable,
                         String shortName, List<ProductsModel> productsList,
                         boolean isSelected, boolean isSerialNumberRequired,
                         int lowStackCount, int productStatus,
                         int productId, Double markUp,
                         int isPriceChanged, String department,
                         double unitPrice,
                         List<FetchProductsResponse.BranchAlaCart> branchAlaCartList,
                         List<FetchProductsResponse.BranchGroup> branchGroupList) {
        this.branchAlaCartList = branchAlaCartList;
        this.branchGroupList = branchGroupList;
        this.name = name;
        this.price = price;
        this.vat = vat;
        this.isAvailable = isAvailable;
        this.imageUrls = imageUrls;
        this.isVattable = isVattable;
        this.shortName = shortName;
        this.productsList = productsList;
        this.isSelected = isSelected;
        this.isSerialNumberRequired = isSerialNumberRequired;
        this.lowStackCount = lowStackCount;
        this.productStatus = productStatus;
        this.productId = productId;
        this.markUp = markUp;
        this.isPriceChanged = isPriceChanged;
        this.department = department;
        this.unitPrice = unitPrice;
    }

    public boolean isRoom() {
        return isRoom;
    }

    public List<FetchProductsResponse.BranchAlaCart> getBranchAlaCartList() {
        return branchAlaCartList;
    }

    public List<FetchProductsResponse.BranchGroup> getBranchGroupList() {
        return branchGroupList;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getDepartment() {
        return department;
    }

    public int getIsPriceChanged() {
        return isPriceChanged;
    }

    public void setIsPriceChanged(int isPriceChanged) {
        this.isPriceChanged = isPriceChanged;
    }

    public Double getMarkUp() {
        return markUp;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getProductId() {
        return productId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public List<ProductsModel> getProductsList() {
        return productsList;
    }

    public String getShortName() {
        return shortName;
    }

    public boolean isVattable() {
        return isVattable;
    }

    public String getName() {
        return name;
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

    public String[] getImageUrls() {
        return imageUrls;
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
