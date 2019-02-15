package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomRateMain {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("room_rate_price_id")
    @Expose
    private Integer roomRatePriceId;
    @SerializedName("room_type_id")
    @Expose
    private Integer roomTypeId;
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

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
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

    public RoomRateMain() {}

    public RoomRateMain(Integer id, Integer roomRatePriceId,
                        Integer roomTypeId, Integer createdBy,
                        String createdAt, String updatedAt,
                        Object deletedAt, RatePrice ratePrice) {
        this.id = id;
        this.roomRatePriceId = roomRatePriceId;
        this.roomTypeId = roomTypeId;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.ratePrice = ratePrice;
    }
}