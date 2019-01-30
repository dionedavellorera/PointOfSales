package nerdvana.com.pointofsales.entities;

import com.orm.SugarRecord;

public class CurrentTransactionEntity extends SugarRecord<CurrentTransactionEntity> {
    private String roomNumber;

    public CurrentTransactionEntity() {}

    public CurrentTransactionEntity(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}
