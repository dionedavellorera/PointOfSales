package nerdvana.com.pointofsales.entities;

import com.orm.SugarRecord;

public class TransactionEntity extends SugarRecord<TransactionEntity> {
    private String transactionId;
    private String orNumber;
    private String roomTableNumber;

    public TransactionEntity() {}


    public TransactionEntity(String transactionId, String orNumber, String roomTableNumber) {
        this.transactionId = transactionId;
        this.orNumber = orNumber;
        this.roomTableNumber = roomTableNumber;
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
