package nerdvana.com.pointofsales.entities;

import com.orm.SugarRecord;

public class SkipPaymentEntity extends SugarRecord<SkipPaymentEntity> {

    private String receiptNumber;
    private String transactionNumber;
    private String paymentMethod; //cash , credit card
    private String paymentType; //multiple payment, single payment
    private Double balance;
    private Double tenderedAmount;

    public SkipPaymentEntity() {}

    public SkipPaymentEntity(String receiptNumber, String transactionNumber,
                             String paymentMethod, String paymentType,
                             Double balance, Double tenderedAmount) {
        this.receiptNumber = receiptNumber;
        this.transactionNumber = transactionNumber;
        this.paymentMethod = paymentMethod;
        this.paymentType = paymentType;
        this.balance = balance;
        this.tenderedAmount = tenderedAmount;
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

    public String getPaymentType() {
        return paymentType;
    }

    public Double getBalance() {
        return balance;
    }

    public Double getTenderedAmount() {
        return tenderedAmount;
    }
}
