package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchRoomStatusResponse {
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public static class Result {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("core_id")
        @Expose
        private Integer coreId;
        @SerializedName("room_status")
        @Expose
        private String roomStatus;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("is_blink")
        @Expose
        private Integer isBlink;
        @SerializedName("is_timer")
        @Expose
        private Integer isTimer;
        @SerializedName("is_name")
        @Expose
        private Integer isName;
        @SerializedName("is_buddy")
        @Expose
        private Integer isBuddy;
        @SerializedName("is_cancel")
        @Expose
        private Integer isCancel;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCoreId() {
            return coreId;
        }

        public void setCoreId(Integer coreId) {
            this.coreId = coreId;
        }

        public String getRoomStatus() {
            return roomStatus;
        }

        public void setRoomStatus(String roomStatus) {
            this.roomStatus = roomStatus;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Integer getIsBlink() {
            return isBlink;
        }

        public void setIsBlink(Integer isBlink) {
            this.isBlink = isBlink;
        }

        public Integer getIsTimer() {
            return isTimer;
        }

        public void setIsTimer(Integer isTimer) {
            this.isTimer = isTimer;
        }

        public Integer getIsName() {
            return isName;
        }

        public void setIsName(Integer isName) {
            this.isName = isName;
        }

        public Integer getIsBuddy() {
            return isBuddy;
        }

        public void setIsBuddy(Integer isBuddy) {
            this.isBuddy = isBuddy;
        }

        public Integer getIsCancel() {
            return isCancel;
        }

        public void setIsCancel(Integer isCancel) {
            this.isCancel = isCancel;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

    }
}
