package nerdvana.com.pointofsales.entities;


import com.orm.SugarRecord;

public class SavedTransactionEntity extends SugarRecord<SavedTransactionEntity> {

    String roomOrTableNumber;
    String jsonItemsSelected;

    public SavedTransactionEntity() {}

    public SavedTransactionEntity(String roomOrTableNumber, String jsonItemsSelected) {
        this.roomOrTableNumber = roomOrTableNumber;
        this.jsonItemsSelected = jsonItemsSelected;
    }

    public String getRoomOrTableNumber() {
        return roomOrTableNumber;
    }

    public String getJsonItemsSelected() {
        return jsonItemsSelected;
    }
}
