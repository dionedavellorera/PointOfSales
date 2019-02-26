package nerdvana.com.pointofsales.model;

public class PostedPaymentsModel {
    private String payment_method;
    private String payment_description;
    private String amount;
    private boolean is_posted;

    public PostedPaymentsModel(String paymentType, String paymentAmount,
                               String paymentDescription, boolean isPosted) {
        this.payment_method = paymentType;
        this.amount = paymentAmount;
        this.payment_description = paymentDescription;
        this.is_posted = isPosted;
    }

    public boolean isIs_posted() {
        return is_posted;
    }

    public String getPayment_description() {
        return payment_description;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public String getAmount() {
        return amount;
    }
}
