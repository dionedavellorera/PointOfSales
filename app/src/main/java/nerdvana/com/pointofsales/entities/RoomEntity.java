package nerdvana.com.pointofsales.entities;

import com.orm.SugarRecord;

public class RoomEntity extends SugarRecord<RoomEntity> {

    private String room_number;
    private String room_type;
    private String room_status;
    private String room_status_description;
    private String wake_up_call;
    private int is_done;

    public RoomEntity() {}

    public RoomEntity(String room_number, String room_type,
                      String room_status, String room_status_description,
                      String wake_up_call, int is_done) {
        this.is_done = is_done;
        this.room_number = room_number;
        this.room_type = room_type;
        this.room_status = room_status;
        this.room_status_description = room_status_description;
        this.wake_up_call = wake_up_call;
    }

    public int getIs_done() {
        return is_done;
    }

    public void setIs_done(int is_done) {
        this.is_done = is_done;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getRoom_status() {
        return room_status;
    }

    public void setRoom_status(String room_status) {
        this.room_status = room_status;
    }

    public String getRoom_status_description() {
        return room_status_description;
    }

    public void setRoom_status_description(String room_status_description) {
        this.room_status_description = room_status_description;
    }

    public String getWake_up_call() {
        return wake_up_call;
    }

    public void setWake_up_call(String wake_up_call) {
        this.wake_up_call = wake_up_call;
    }
}
