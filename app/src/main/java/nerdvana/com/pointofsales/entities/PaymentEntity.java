package nerdvana.com.pointofsales.entities;

import com.orm.SugarRecord;

public class PaymentEntity extends SugarRecord<PaymentEntity> {

    private String receiptNumber;
    private String transactionNumber;
    private String paymentMethod; //cash , credit card
    private String paymentType; //multiple payment, single payment
    private Double balance;
    private Double tenderedAmount;

    public PaymentEntity() {}

    public PaymentEntity(String receiptNumber, String transactionNumber,
                         String paymentMethod, Double balance,
                         Double tenderedAmount, String paymentType) {
        this.receiptNumber = receiptNumber;
        this.transactionNumber = transactionNumber;
        this.paymentMethod = paymentMethod;
        this.balance = balance;
        this.tenderedAmount = tenderedAmount;
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Double getBalance() {
        return balance;
    }

    public Double getTenderedAmount() {
        return tenderedAmount;
    }
}
