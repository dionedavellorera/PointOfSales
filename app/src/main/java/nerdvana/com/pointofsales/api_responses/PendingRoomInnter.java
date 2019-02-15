package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingRoomInnter {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("room_no")
    @Expose
    private String roomNo;
    @SerializedName("room_name")
    @Expose
    private String roomName;
    @SerializedName("CRoom_Stat")
    @Expose
    private Integer cRoomStat;
    @SerializedName("room_type_id")
    @Expose
    private Integer roomTypeId;
    @SerializedName("room_area_id")
    @Expose
    private Integer roomAreaId;
    @SerializedName("last_checkout")
    @Expose
    private Object lastCheckout;
    @SerializedName("flag")
    @Expose
    private Integer flag;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getCRoomStat() {
        return cRoomStat;
    }

    public void setCRoomStat(Integer cRoomStat) {
        this.cRoomStat = cRoomStat;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Integer getRoomAreaId() {
        return roomAreaId;
    }

    public void setRoomAreaId(Integer roomAreaId) {
        this.roomAreaId = roomAreaId;
    }

    public Object getLastCheckout() {
        return lastCheckout;
    }

    public void setLastCheckout(Object lastCheckout) {
        this.lastCheckout = lastCheckout;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

}
