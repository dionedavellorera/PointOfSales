package nerdvana.com.pointofsales.model;

public class FragmentNotifierModel {
    private RoomTableModel selectedRoom;

    public FragmentNotifierModel(RoomTableModel selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    public RoomTableModel getSelectedRoom() {
        return selectedRoom;
    }
}
