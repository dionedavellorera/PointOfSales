package nerdvana.com.pointofsales.model;

public class ItemScannedModel {
    private String productBarcode;

    public ItemScannedModel(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public String getProductBarcode() {
        return productBarcode;
    }
}
