package nerdvana.com.pointofsales.model;

public class ProductsModel {
    private String name;
    private double price;
    private double vat;
    private boolean isAvailable;
    private String[] imageUrls;
    public ProductsModel(String name, double price, double vat, boolean isAvailable, String[] imageUrls) {
        this.name = name;
        this.price = price;
        this.vat = vat;
        this.isAvailable = isAvailable;
        this.imageUrls = imageUrls;
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
