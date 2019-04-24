package nerdvana.com.pointofsales.model;

public class PaymentPrintModel {
    private String paymentType;
    private String amount;

    public PaymentPrintModel(String paymentType, String amount) {
        this.paymentType = paymentType;
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getAmount() {
        return amount;
    }
}
