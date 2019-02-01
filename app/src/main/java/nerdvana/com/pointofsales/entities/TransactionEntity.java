package nerdvana.com.pointofsales.entities;

import com.orm.SugarRecord;

public class TransactionEntity extends SugarRecord<TransactionEntity> {
    private String transactionId;
    private String orNumber;
    private String roomTableNumber;
    private int transactionStatus;

    public TransactionEntity() {}


    public TransactionEntity(String transactionId, String orNumber,
                             String roomTableNumber, int transactionStatus) {
        this.transactionId = transactionId;
        this.orNumber = orNumber;
        this.roomTableNumber = roomTableNumber;
        this.transactionStatus = transactionStatus;
    }


    public void setTransactionStatus(int transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public int getTransactionStatus() {
        return transactionStatus;
    }

    public String getRoomTableNumber() {
        return roomTableNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOrNumber() {
        return orNumber;
    }
}
