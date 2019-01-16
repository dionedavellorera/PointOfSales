package nerdvana.com.pointofsales.model;

import java.util.List;

public class ProductsModel {
    private String name;
    private String shortName;
    private double price;
    private double vat;
    private boolean isAvailable;
    private String[] imageUrls;
    private boolean isVattable;
    private List<ProductsModel> productsList;

    public ProductsModel(String name, double price,
                         double vat, boolean isAvailable,
                         String[] imageUrls, boolean isVattable,
                         String shortName, List<ProductsModel> productsList) {
        this.name = name;
        this.price = price;
        this.vat = vat;
        this.isAvailable = isAvailable;
        this.imageUrls = imageUrls;
        this.isVattable = isVattable;
        this.shortName = shortName;
        this.productsList = productsList;
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
}
