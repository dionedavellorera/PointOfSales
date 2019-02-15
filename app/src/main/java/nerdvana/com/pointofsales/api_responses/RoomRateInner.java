package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomRateInner {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("room_rate")
    @Expose
    private String roomRate;
    @SerializedName("minHr")
    @Expose
    private Integer minHr;
    @SerializedName("maxHr")
    @Expose
    private Integer maxHr;
    @SerializedName("gracePeriod")
    @Expose
    private Integer gracePeriod;
    @SerializedName("is_promo")
    @Expose
    private Integer isPromo;
    @SerializedName("flag")
    @Expose
    private Integer flag;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
//    @SerializedName("amount")
//    @Expose
//    private Amount amount;
//    @SerializedName("per_hour")
//    @Expose
//    private PerHour perHour;
//    @SerializedName("x_person")
//    @Expose
//    private XPerson xPerson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(String roomRate) {
        this.roomRate = roomRate;
    }

    public Integer getMinHr() {
        return minHr;
    }

    public void setMinHr(Integer minHr) {
        this.minHr = minHr;
    }

    public Integer getMaxHr() {
        return maxHr;
    }

    public void setMaxHr(Integer maxHr) {
        this.maxHr = maxHr;
    }

    public Integer getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Integer gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Integer getIsPromo() {
        return isPromo;
    }

    public void setIsPromo(Integer isPromo) {
        this.isPromo = isPromo;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
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

//    public Amount getAmount() {
//        return amount;
//    }
//
//    public void setAmount(Amount amount) {
//        this.amount = amount;
//    }
//
//    public PerHour getPerHour() {
//        return perHour;
//    }
//
//    public void setPerHour(PerHour perHour) {
//        this.perHour = perHour;
//    }
//
//    public XPerson getXPerson() {
//        return xPerson;
//    }
//
//    public void setXPerson(XPerson xPerson) {
//        this.xPerson = xPerson;
//    }
}
