package nerdvana.com.pointofsales.model;

public class ForVoidDiscountModel {
    private String discountId;
    private String discountName;
    private String discountAmount;

    public ForVoidDiscountModel(String discountId, String discountName, String discountAmount) {
        this.discountId = discountId;
        this.discountName = discountName;
        this.discountAmount = discountAmount;
    }

    public String getDiscountId() {
        return discountId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }
}
