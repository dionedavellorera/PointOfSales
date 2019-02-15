package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchRoomResponse {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("mesage")
    @Expose
    private String mesage;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMesage() {
        return mesage;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }




    public class Result {

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
        @SerializedName("creator")
        @Expose
        private Creator creator;
        @SerializedName("type")
        @Expose
        private Type type;
        @SerializedName("room_rate")
        @Expose
        private List<RoomRateSub> roomRate = null;
        @SerializedName("status")
        @Expose
        private Status status;
        @SerializedName("area")
        @Expose
        private Area area;


        public Integer getcRoomStat() {
            return cRoomStat;
        }

        public void setcRoomStat(Integer cRoomStat) {
            this.cRoomStat = cRoomStat;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Area getArea() {
            return area;
        }

        public void setArea(Area area) {
            this.area = area;
        }

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

        public Creator getCreator() {
            return creator;
        }

        public void setCreator(Creator creator) {
            this.creator = creator;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public List<RoomRateSub> getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(List<RoomRateSub> roomRate) {
            this.roomRate = roomRate;
        }

    }


    public class Type {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("room_type")
        @Expose
        private String roomType;
        @SerializedName("parent_id")
        @Expose
        private Object parentId;
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
        @SerializedName("room_rate")
        @Expose
        private List<RoomRateMain> roomRate = null;


        @SerializedName("parent")
        @Expose
        private Parent parent;

        public Parent getParent() {
            return parent;
        }

        public void setParent(Parent parent) {
            this.parent = parent;
        }


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public Object getParentId() {
            return parentId;
        }

        public void setParentId(Object parentId) {
            this.parentId = parentId;
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

        public List<RoomRateMain> getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(List<RoomRateMain> roomRate) {
            this.roomRate = roomRate;
        }



    }

    public class Status {

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


    public class Area {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("room_area")
        @Expose
        private String roomArea;
        @SerializedName("printer_path")
        @Expose
        private Object printerPath;
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

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getRoomArea() {
            return roomArea;
        }

        public void setRoomArea(String roomArea) {
            this.roomArea = roomArea;
        }

        public Object getPrinterPath() {
            return printerPath;
        }

        public void setPrinterPath(Object printerPath) {
            this.printerPath = printerPath;
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

    }

    public class Parent {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("room_type")
        @Expose
        private String roomType;
        @SerializedName("parent_id")
        @Expose
        private Object parentId;
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
        @SerializedName("room_rate")
        @Expose
        private List<RoomRateMain> roomRate = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public Object getParentId() {
            return parentId;
        }

        public void setParentId(Object parentId) {
            this.parentId = parentId;
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

        public List<RoomRateMain> getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(List<RoomRateMain> roomRate) {
            this.roomRate = roomRate;
        }

    }

}
