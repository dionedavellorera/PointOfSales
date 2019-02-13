package nerdvana.com.pointofsales.entities;

import com.orm.SugarRecord;

public class CurrentTransactionEntity extends SugarRecord<CurrentTransactionEntity> {
    private String roomNumber;
    private double amount;

    public CurrentTransactionEntity() {}

    public CurrentTransactionEntity(String roomNumber, double amount) {
        this.roomNumber = roomNumber;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
