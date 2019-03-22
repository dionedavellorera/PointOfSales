package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatePrice {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("room_rate_id")
    @Expose
    private Integer roomRateId;
    @SerializedName("currency_id")
    @Expose
    private Integer currencyId;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("xPerson")
    @Expose
    private Integer xPerson;
    @SerializedName("perHour")
    @Expose
    private Integer perHour;
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
    @SerializedName("branch_room_rate")
    @Expose
    private RoomRateInner roomRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomRateId() {
        return roomRateId;
    }

    public void setRoomRateId(Integer roomRateId) {
        this.roomRateId = roomRateId;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getXPerson() {
        return xPerson;
    }

    public void setXPerson(Integer xPerson) {
        this.xPerson = xPerson;
    }

    public Integer getPerHour() {
        return perHour;
    }

    public void setPerHour(Integer perHour) {
        this.perHour = perHour;
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

    public RoomRateInner getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(RoomRateInner roomRate) {
        this.roomRate = roomRate;
    }

    public RatePrice(Integer id, Integer roomRateId, Integer currencyId, Double amount, Integer xPerson, Integer perHour, Integer flag, Integer createdBy, String createdAt, String updatedAt, Object deletedAt, RoomRateInner roomRate) {
        this.id = id;
        this.roomRateId = roomRateId;
        this.currencyId = currencyId;
        this.amount = amount;
        this.xPerson = xPerson;
        this.perHour = perHour;
        this.flag = flag;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.roomRate = roomRate;
    }
}