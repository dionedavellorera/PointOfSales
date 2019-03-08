package nerdvana.com.pointofsales.model;

import org.json.JSONObject;

public class PostedPaymentsModel {
    private String payment_method;
    private String payment_description;
    private String amount;
    private boolean is_posted;
    private String currency_id;
    private String currency_value;
    private JSONObject data;

    public PostedPaymentsModel(String paymentType, String paymentAmount,
                               String paymentDescription, boolean isPosted,
                               String currencyId, String currencyValue,
                               JSONObject data) {
        this.payment_method = paymentType;
        this.amount = paymentAmount;
        this.payment_description = paymentDescription;
        this.is_posted = isPosted;
        this.currency_id = currencyId;
        this.currency_value = currencyValue;
        this.data = data;
    }

    public JSONObject getData() {
        return data;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public String getCurrency_value() {
        return currency_value;
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

    @Override
    public String toString() {
        return "PostedPaymentsModel{" +
                "payment_method='" + payment_method + '\'' +
                ", payment_description='" + payment_description + '\'' +
                ", amount='" + amount + '\'' +
                ", is_posted=" + is_posted +
                ", currency_id='" + currency_id + '\'' +
                ", currency_value='" + currency_value + '\'' +
                ", data=" + data +
                '}';
    }
}
