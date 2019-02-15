package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomRateSub {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("room_rate_price_id")
    @Expose
    private Integer roomRatePriceId;
    @SerializedName("room_id")
    @Expose
    private Integer roomId;
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
    @SerializedName("rate_price")
    @Expose
    private RatePrice ratePrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomRatePriceId() {
        return roomRatePriceId;
    }

    public void setRoomRatePriceId(Integer roomRatePriceId) {
        this.roomRatePriceId = roomRatePriceId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
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

    public RatePrice getRatePrice() {
        return ratePrice;
    }

    public void setRatePrice(RatePrice ratePrice) {
        this.ratePrice = ratePrice;
    }
}
